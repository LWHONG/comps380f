/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.CDFUserRepository;
import edu.ouhk.comps380f.model.CDFUser;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author LAM
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private CDFUserRepository userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String userProfile(ModelMap model, Principal principal) {
        if (principal != null) {
            CDFUser user = userRepo.findByUsername(principal.getName());
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        return "profile";
    }
    
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String userProfileSlash() {
        return "redirect:/profile";
    }
    
    /*@RequestMapping(value = {"/{username}"}, method = RequestMethod.GET)
    public String editGET(@PathVariable String username, ModelMap model) {
        CDFUser user = userRepo.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "profile";
    }
    
   @RequestMapping(value = {"/{username}/"}, method = RequestMethod.GET)
    public String editGETSlash(@PathVariable String username) {
        return "redirect:/profile/"+username;
    }*/
    
    public static class EditInfoForm {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
    
    @RequestMapping(value = {"/edit/info"}, method = RequestMethod.POST)
    public String userEditInfo(EditInfoForm form, RedirectAttributes attributes, Principal principal) {
        CDFUser user = userRepo.findByUsername(principal.getName());
        if (user != null) {
            user.setEmail(form.getEmail());
            userRepo.update(user);
            attributes.addFlashAttribute("edit_info", "success");
        }else {
            attributes.addFlashAttribute("edit_info", "fail");
        }
        return "redirect:/profile";
    }
    
    public static class EditPasswordForm {
        private String currentPassword;
        private String password;

        public String getCurrentPassword() {
            return currentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    
    @RequestMapping(value = {"/edit/password"}, method = RequestMethod.POST)
    public String userEditPassword(EditPasswordForm form, RedirectAttributes attributes, Principal principal) {
        CDFUser user = userRepo.findByUsername(principal.getName());
        if (user != null) {
            if (passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(form.getPassword()));
                userRepo.update(user);
                attributes.addFlashAttribute("edit_password", "success");
            }else {
                attributes.addFlashAttribute("edit_password", "fail");
            }
        }else {
            attributes.addFlashAttribute("edit_password", "fail");
        }
        return "redirect:/profile";
    }
    
    @RequestMapping(value = {"/{username}"}, method = RequestMethod.GET)
    public String adminProfile(@PathVariable String username, ModelMap model) {
        CDFUser user = userRepo.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "profile";
    }
    
    @RequestMapping(value = {"/{username}/"}, method = RequestMethod.GET)
    public String adminProfileSlash(@PathVariable String username) {
        return "redirect:/profile/"+username;
    }
    
    
    @RequestMapping(value = {"/{username}/edit/info"}, method = RequestMethod.POST)
    public String adminEditInfo(@PathVariable String username, EditInfoForm form, RedirectAttributes attributes) {
        CDFUser user = userRepo.findByUsername(username);
        if (user != null) {
            user.setEmail(form.getEmail());
            userRepo.update(user);
            attributes.addFlashAttribute("edit_info", "success");
        }else {
            attributes.addFlashAttribute("edit_info", "fail");
        }
        return "redirect:/profile/"+username;
    }
    
    @RequestMapping(value = {"/{username}/edit/password"}, method = RequestMethod.POST)
    public String adminEditPassword(@PathVariable String username, EditPasswordForm form, RedirectAttributes attributes) {
        CDFUser user = userRepo.findByUsername(username);
        if (user != null) {
            if (passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(form.getPassword()));
                userRepo.update(user);
                attributes.addFlashAttribute("edit_password", "success");
            }else {
                attributes.addFlashAttribute("edit_password", "fail");
            }
        }else {
            attributes.addFlashAttribute("edit_password", "fail");
        }
        return "redirect:/profile/"+username;
    }
}
