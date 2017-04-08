/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LAM
 */
public class CDFPoll {

    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Timestamp createTimestamp;
    private boolean enable;
    List<CDFPollAnswer> pollAnswers = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<CDFPollAnswer> getPollAnswers() {
        return pollAnswers;
    }

    public void setPollAnswers(List<CDFPollAnswer> pollAnswers) {
        this.pollAnswers = pollAnswers;
    }

    public int getNumberOfOptionA() {
        int number = 0;
        for (CDFPollAnswer pollAnswer : pollAnswers) {
            if (pollAnswer.getAnswer().equals("option_a")) {
                number++;
            }
        }
        return number;
    }

    public int getNumberOfOptionB() {
        int number = 0;
        for (CDFPollAnswer pollAnswer : pollAnswers) {
            if (pollAnswer.getAnswer().equals("option_b")) {
                number++;
            }
        }
        return number;
    }

    public int getNumberOfOptionC() {
        int number = 0;
        for (CDFPollAnswer pollAnswer : pollAnswers) {
            if (pollAnswer.getAnswer().equals("option_c")) {
                number++;
            }
        }
        return number;
    }

    public int getNumberOfOptionD() {
        int number = 0;
        for (CDFPollAnswer pollAnswer : pollAnswers) {
            if (pollAnswer.getAnswer().equals("option_d")) {
                number++;
            }
        }
        return number;
    }

    public int getNumberOfPollAnswer() {
        return pollAnswers.size();
    }
}
