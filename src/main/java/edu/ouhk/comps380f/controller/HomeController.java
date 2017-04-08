/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.CDFPollRepository;
import edu.ouhk.comps380f.dao.CDFUserRepository;
import edu.ouhk.comps380f.model.CDFPoll;
import edu.ouhk.comps380f.model.CDFPollAnswer;
import edu.ouhk.comps380f.model.CDFUser;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author LAM
 */
@Controller
@RequestMapping("/index")
public class HomeController {

    @Autowired
    private CDFUserRepository userRepo;

    @Autowired
    private CDFPollRepository pollRepo;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String index(ModelMap model, Principal principal) {
        if (principal != null) {
            CDFUser user = userRepo.findByUsername(principal.getName());
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        CDFPoll poll = pollRepo.findLast(true);
        if (poll != null) {
            model.addAttribute("poll", poll);
            if (principal != null) {
                CDFPollAnswer pollAnswer = pollRepo.findByUsername(principal.getName(), poll.getId());
                if (pollAnswer != null) {
                    model.addAttribute("pollAnswer", pollAnswer);
                    System.out.println("answered...");
                }
            }
        }
        return "index";
    }
}
