/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.CDFUserRepository;
import edu.ouhk.comps380f.model.CDFUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author LAM
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private CDFUserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static class Form {
        private String username;
        private String password;

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
    }
    
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String registerGET(Form form, ModelMap model) {
        return "redirect:/login";
    }
    
    @RequestMapping(value = {""}, method = RequestMethod.POST)
    public String registerPOST(Form form, RedirectAttributes attributes) {
        if (userRepo.findByUsername(form.getUsername()) != null) {
            attributes.addFlashAttribute("register", "exist");
        } else {
            CDFUser user = new CDFUser();
            user.setUsername(form.getUsername());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            user.addRole("ROLE_USER");
            userRepo.create(user);
            //logger.info("User " + form.getUsername() + " created.");
            attributes.addFlashAttribute("register", "success");
        }
        return "redirect:/login";
    }
}
