/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.controller;

import java.security.Principal;
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
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String index(ModelMap model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "index";
    }
}
