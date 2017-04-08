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
import java.util.List;
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
@RequestMapping("/poll")
public class PollController {

    @Autowired
    private CDFUserRepository userRepo;

    @Autowired
    private CDFPollRepository pollRepo;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String poll(ModelMap model, Principal principal) {
        if (principal != null) {
            CDFUser user = userRepo.findByUsername(principal.getName());
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        List<CDFPoll> polls = pollRepo.findAll(true);
        model.addAttribute("polls", polls);
        return "poll";
    }

    public static class CreatePollForm {

        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String createPoll(CreatePollForm form, Principal principal) {
        CDFPoll poll = new CDFPoll();
        poll.setQuestion(form.getQuestion());
        poll.setOptionA(form.getOptionA());
        poll.setOptionB(form.getOptionB());
        poll.setOptionC(form.getOptionC());
        poll.setOptionD(form.getOptionD());
        poll.setEnable(true);
        pollRepo.create(poll);
        return "redirect:/poll";
    }

    public static class PollAnswerForm {

        private String answer;
        private int pollId;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public int getPollId() {
            return pollId;
        }

        public void setPollId(int pollId) {
            this.pollId = pollId;
        }
    }

    @RequestMapping(value = {""}, method = RequestMethod.POST)
    public String pollAnswer(PollAnswerForm form, Principal principal) {
        if (principal != null) {
            CDFUser user = userRepo.findByUsername(principal.getName());
            if (user != null) {
                CDFPollAnswer pollAnswer = new CDFPollAnswer();
                pollAnswer.setUsername(principal.getName());
                pollAnswer.setAnswer(form.getAnswer());
                pollAnswer.setPollId(form.getPollId());
                pollRepo.answer(pollAnswer);
            }
        }
        return "redirect:/index";
    }
}
