/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.models;

import java.util.Date;

/**
 *
 * @author wjlax
 */
public class Transaction {
    private int tid;
    private Date dateEntered;
    private int logkey_id;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public int getLogkey_id() {
        return logkey_id;
    }

    public void setLogkey_id(int logkey_id) {
        this.logkey_id = logkey_id;
    }
    
    
}
