/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LAM
 */
public class CDFCategory {

    private String id;
    private String name;
    private List<CDFThread> threads = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CDFThread> getThreads() {
        return threads;
    }

    public void setThreads(List<CDFThread> threads) {
        this.threads = threads;
    }
}
