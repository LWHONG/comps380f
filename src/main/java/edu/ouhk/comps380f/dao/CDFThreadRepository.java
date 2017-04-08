/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.CDFReply;
import edu.ouhk.comps380f.model.CDFThread;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author LAM
 */
public interface CDFThreadRepository {

    public void create(CDFThread thread) throws IOException;

    public void reply(CDFReply reply) throws IOException;

    public List<CDFThread> findAllByCategory(String category, boolean hasAttachments);

    public CDFThread findByThreadId(int threadId, boolean hasAttachments);

    public CDFReply findByReplyId(int replyId, boolean hasAttachments);

    public void deleteByThreadId(int threadId) throws IOException;

    public void deleteByReplyId(int replyId) throws IOException;
}
