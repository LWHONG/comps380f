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
        private String email;

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
    }

    @RequestMapping(value = {""}, method = RequestMethod.POST)
    public String register(Form form, RedirectAttributes attributes) {
        if (userRepo.findByUsername(form.getUsername()) != null) {
            attributes.addFlashAttribute("register", "exist");
        } else {
            CDFUser user = new CDFUser();
            user.setUsername(form.getUsername());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            user.setEmail(form.getEmail());
            user.addRole("ROLE_USER");
            userRepo.create(user);
            attributes.addFlashAttribute("register", "success");
        }
        return "redirect:/login";
    }
}
