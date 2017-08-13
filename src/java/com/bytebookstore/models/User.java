/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.models;

/**
 *
 * @author wjlax
 */
public class User {
    private int logkey_id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean privilege;

    public int getLogkey_id() {
        return logkey_id;
    }

    public void setLogkey_id(int logkey_id) {
        this.logkey_id = logkey_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getPrivilege() {
        return privilege;
    }

    public void setPrivilege(boolean privilege) {
        this.privilege = privilege;
    }
}
