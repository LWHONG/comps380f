/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.CDFUser;
import java.util.List;

/**
 *
 * @author LAM
 */
public interface CDFUserRepository {
    public void create(CDFUser user);
    public List<CDFUser> findAll();
    public CDFUser findByUsername(String username);
    public void update(CDFUser user);
    public void deleteByUsername(String username);
}
