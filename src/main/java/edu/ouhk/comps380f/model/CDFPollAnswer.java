/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.model;

import java.sql.Timestamp;

/**
 *
 * @author LAM
 */
public class CDFPollAnswer {

    private int id;
    private String username;
    private String answer;
    private Timestamp createTimestamp;
    private int pollId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }
}
