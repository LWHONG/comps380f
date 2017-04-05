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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
    
    @RequestMapping(value = {"register"}, method = RequestMethod.GET)
    public String admin() {
        return "admin";
    }
    
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
    
    @RequestMapping(value = {"register"}, method = RequestMethod.POST)
    public String register(RegisterController.Form form, RedirectAttributes attributes) {
        if (userRepo.findByUsername(form.getUsername()) != null) {
            attributes.addFlashAttribute("register", "exist");
        } else {
            CDFUser user = new CDFUser();
            user.setUsername(form.getUsername());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            user.addRole("ROLE_ADMIN");
            userRepo.create(user);
            //logger.info("Admin " + form.getUsername() + " created.");
            attributes.addFlashAttribute("register", "success");
        }
        return "redirect:/admin/register";
    }
}
