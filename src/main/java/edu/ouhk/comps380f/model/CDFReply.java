/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author LAM
 */
public class CDFReply {
    private int id;
    private String username;
    private String content;
    private int threadId;
    private List<CDFAttachment> attachments = new ArrayList<>();

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public List<CDFAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CDFAttachment> attachments) {
        this.attachments = attachments;
    }
    
    public CDFAttachment getAttachment(int attachmentId) {
        CDFAttachment attachment = null;
        for (CDFAttachment item : this.attachments) {
            if (item.getId() == attachmentId) {
                attachment = item;
                break;
            }
        }
        return attachment;
    }
    
    public void addAttachment(CDFAttachment attachment) {
        this.attachments.add(attachment);
    }
}
