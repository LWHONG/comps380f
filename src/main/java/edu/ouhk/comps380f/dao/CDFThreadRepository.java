/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.CDFReply;
import edu.ouhk.comps380f.model.CDFThread;
import java.util.List;

/**
 *
 * @author LAM
 */
public interface CDFThreadRepository {
    public void create(CDFThread thread);
    public void reply(CDFReply reply);
    public List<CDFThread> findAllByCategory(String category);
    public CDFThread findByThreadId(int threadId);
    public void deleteByThreadId(int threadId);
}
