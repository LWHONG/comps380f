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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author LAM
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private CDFUserRepository userRepo;
    
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String profile(ModelMap model, Principal principal) {
        if (principal != null) {
            CDFUser user = userRepo.findByUsername(principal.getName());
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        return "profile";
    }
    
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String profileSlash() {
        return "redirect:/profile";
    }
    
    @RequestMapping(value = {"/{username}"}, method = RequestMethod.GET)
    public String edit(@PathVariable String username, ModelMap model) {
        CDFUser user = userRepo.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "profile";
    }
    
    @RequestMapping(value = {"/{username}/"}, method = RequestMethod.GET)
    public String editSlash(@PathVariable String username) {
        return "redirect:/profile/"+username;
    }
}
