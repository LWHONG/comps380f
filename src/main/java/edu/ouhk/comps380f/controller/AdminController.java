/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.CDFUserRepository;
import edu.ouhk.comps380f.model.CDFUser;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CDFUserRepository userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String backend(ModelMap model, Principal principal) {
        if (principal != null) {
            CDFUser user = userRepo.findByUsername(principal.getName());
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        List<CDFUser> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "backend";
    }

    /*@RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String backendSlash(ModelMap model) {
        List<CDFUser> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "redirect:/admin";
    }*/
    /*
    @RequestMapping(value = {"register"}, method = RequestMethod.GET)
    public String admin() {
        return "admin";
    }
    */
    public static class Form {
        private String username;
        private String password;
        private String email;
        private String[] roles;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }
    
    @RequestMapping(value = {"register"}, method = RequestMethod.POST)
    public String register(Form form, RedirectAttributes attributes) {
        if (userRepo.findByUsername(form.getUsername()) != null) {
            attributes.addFlashAttribute("register", "exist");
        } else {
            CDFUser user = new CDFUser();
            user.setUsername(form.getUsername());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            user.setEmail(form.getEmail());
            for (String role : form.getRoles()) {
                user.addRole(role);
            }
            userRepo.create(user);
            //logger.info("Admin " + form.getUsername() + " created.");
            attributes.addFlashAttribute("register", "success");
        }
        return "redirect:/admin";
    }
    
    @RequestMapping(value = "/delete/{username}", method = RequestMethod.GET)
    public String delete(@PathVariable String username) {
        userRepo.deleteByUsername(username);
        return "redirect:/admin";
    }
}
