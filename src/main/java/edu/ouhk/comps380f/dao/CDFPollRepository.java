/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.CDFPoll;
import edu.ouhk.comps380f.model.CDFPollAnswer;
import java.util.List;

/**
 *
 * @author LAM
 */
public interface CDFPollRepository {

    public void create(CDFPoll poll);

    public void answer(CDFPollAnswer pollAnswer);

    public List<CDFPoll> findAll(boolean hasPollAnswers);

    public CDFPoll findLast(boolean hasPollAnswers);

    public CDFPoll findByPollId(int pollId, boolean hasPollAnswers);

    public CDFPollAnswer findByUsername(String username, int pollId);

    public void deleteByPollId(int pollId);
}
