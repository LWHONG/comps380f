/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.CDFThreadRepository;
import edu.ouhk.comps380f.dao.CDFUserRepository;
import edu.ouhk.comps380f.model.CDFCategory;
import edu.ouhk.comps380f.model.CDFReply;
import edu.ouhk.comps380f.model.CDFThread;
import edu.ouhk.comps380f.model.CDFUser;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author LAM
 */
@Controller
public class ThreadController {
    @Autowired
    CDFUserRepository userRepo;
    
    @Autowired
    CDFThreadRepository threadRepo;
    
    @RequestMapping(value = {"/{category:lecture|lab|other}"}, method = RequestMethod.GET)
    public String list(@PathVariable String category, ModelMap model, Principal principal) {
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
        List<CDFThread> threads = threadRepo.findAllByCategory(category);
        model.addAttribute("threads", threads);
        return "threads";
    }
    
    @RequestMapping(value = {"/{category:lecture|lab|other}/post"}, method = RequestMethod.GET)
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
    }
    
    @RequestMapping(value = {"/{category:lecture|lab|other}/post"}, method = RequestMethod.POST)
    public View postPOST(@PathVariable String category, PostForm form, Principal principal) {
        CDFThread thread = new CDFThread();
        thread.setUsername(principal.getName());
        thread.setTitle(form.getTitle());
        thread.setContent(form.getContent());
        thread.setCategory(category);
        threadRepo.create(thread);
         return new RedirectView("/"+category, true);
    }
    
    @RequestMapping(value = {"/{category:lecture|lab|other}/thread/{id:\\d+}"}, method = RequestMethod.GET)
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
        CDFThread thread = threadRepo.findByThreadId(id);
        model.addAttribute("thread", thread);
        return "thread";
    }
    
    public static class ReplyForm {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
    
    @RequestMapping(value = {"/{category:lecture|lab|other}/thread/{id:\\d+}/reply"}, method = RequestMethod.POST)
    public View reply(@PathVariable String category, @PathVariable int id, ReplyForm form, Principal principal) {
        CDFReply reply = new CDFReply();
        reply.setUsername(principal.getName());
        reply.setContent(form.getContent());
        reply.setThreadId(id);
        threadRepo.reply(reply);
        return new RedirectView("/"+category+"/thread/"+id, true);
    }
}
