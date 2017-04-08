/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.CDFThreadRepository;
import edu.ouhk.comps380f.dao.CDFUserRepository;
import edu.ouhk.comps380f.model.CDFAttachment;
import edu.ouhk.comps380f.model.CDFCategory;
import edu.ouhk.comps380f.model.CDFReply;
import edu.ouhk.comps380f.model.CDFThread;
import edu.ouhk.comps380f.model.CDFUser;
import edu.ouhk.comps380f.view.CDFDownloadingView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author LAM
 */
@Controller
@RequestMapping("/{category:lecture|lab|other}")
public class ThreadController {

    @Autowired
    private CDFUserRepository userRepo;

    @Autowired
    private CDFThreadRepository threadRepo;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String threads(@PathVariable String category, ModelMap model, Principal principal) {
        if (principal != null) {
            CDFUser user = userRepo.findByUsername(principal.getName());
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        CDFCategory cate = new CDFCategory();
        cate.setId(category);
        cate.setName(category.substring(0, 1).toUpperCase() + category.substring(1));
        model.addAttribute("category", cate);
        List<CDFThread> threads = threadRepo.findAllByCategory(category, false);
        model.addAttribute("threads", threads);
        model.addAttribute("threads_size", threads.size());
        return "threads";
    }

    @RequestMapping(value = {"/post"}, method = RequestMethod.GET)
    public String postGET(@PathVariable String category, ModelMap model, Principal principal) {
        if (principal != null) {
            CDFUser user = userRepo.findByUsername(principal.getName());
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        CDFCategory cate = new CDFCategory();
        cate.setId(category);
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        cate.setName(category);
        model.addAttribute("category", cate);
        return "post";
    }

    public static class PostForm {

        private String title;
        private String content;
        private List<MultipartFile> attachments;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @RequestMapping(value = {"/post"}, method = RequestMethod.POST)
    public String postPOST(@PathVariable String category, PostForm form, Principal principal) throws IllegalStateException, IOException {
        CDFThread thread = new CDFThread();
        thread.setUsername(principal.getName());
        thread.setTitle(form.getTitle());
        thread.setContent(form.getContent());
        thread.setCategory(category);
        for (MultipartFile filePart : form.getAttachments()) {
            CDFAttachment attachment = new CDFAttachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setFile(filePart);
            if (attachment.getName() != null && attachment.getName().length() > 0) {
                thread.addAttachment(attachment);
            }
        }
        threadRepo.create(thread);
        return "redirect:/" + category;
    }

    @RequestMapping(value = {"/{id:\\d+}"}, method = RequestMethod.GET)
    public String thread(@PathVariable String category, @PathVariable int id, ModelMap model, Principal principal) {
        if (principal != null) {
            CDFUser user = userRepo.findByUsername(principal.getName());
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        CDFCategory cate = new CDFCategory();
        cate.setId(category);
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        cate.setName(category);
        model.addAttribute("category", cate);
        CDFThread thread = threadRepo.findByThreadId(id, true);
        model.addAttribute("thread", thread);
        return "thread";
    }

    public static class ReplyForm {

        private String content;
        private List<MultipartFile> attachments;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @RequestMapping(value = {"/{id:\\d+}/reply"}, method = RequestMethod.POST)
    public String reply(@PathVariable String category, @PathVariable int id, ReplyForm form, Principal principal) throws IllegalStateException, IOException {
        CDFReply reply = new CDFReply();
        reply.setUsername(principal.getName());
        reply.setContent(form.getContent());
        reply.setThreadId(id);
        for (MultipartFile filePart : form.getAttachments()) {
            CDFAttachment attachment = new CDFAttachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setFile(filePart);
            if (attachment.getName() != null && attachment.getName().length() > 0) {
                reply.addAttachment(attachment);
            }
        }
        threadRepo.reply(reply);
        return "redirect:/" + category + "/" + id;
    }

    @RequestMapping(value = "/{id:\\d+}/attachment/{type:thread|reply}/{attachmentId:\\d+}", method = RequestMethod.GET)
    public View download(@PathVariable String category, @PathVariable int id, @PathVariable String type, @PathVariable int attachmentId) throws IOException {
        CDFAttachment attachment = null;

        CDFThread thread = threadRepo.findByThreadId(id, true);
        if (thread != null) {
            if (type.equals("thread")) {
                attachment = thread.getAttachment(attachmentId);
            } else if (type.equals("reply")) {
                for (CDFReply reply : thread.getReplies()) {
                    if ((attachment = reply.getAttachment(attachmentId)) != null) {
                        break;
                    }
                }
            }
        }

        if (attachment != null) {
            File file = new File(attachment.getPath());
            FileInputStream fis = new FileInputStream(file);
            byte[] contents = new byte[(int) file.length()];
            fis.read(contents);
            return new CDFDownloadingView(attachment.getName(), attachment.getMimeContentType(), contents);
        }
        return new RedirectView("redirect:/" + category + "/" + id, true);
    }

    @RequestMapping(value = "/{id:\\d+}/delete", method = RequestMethod.GET)
    public String deleteThread(@PathVariable String category, @PathVariable int id) throws IOException {
        threadRepo.deleteByThreadId(id);
        return "redirect:/" + category;
    }

    @RequestMapping(value = "/{id:\\d+}/delete/{replyId:\\d+}", method = RequestMethod.GET)
    public String deleteReply(@PathVariable String category, @PathVariable int id, @PathVariable int replyId) throws IOException {
        threadRepo.deleteByReplyId(replyId);
        return "redirect:/" + category + "/" + id;
    }
}
