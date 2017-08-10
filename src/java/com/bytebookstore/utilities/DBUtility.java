/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public class DBUtility {

    public static DataSource ds = null;

    public static void initializeDataSource() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DBUtility.ds = (DataSource) envContext.lookup("jdbc/BookStoreDB");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("DBUTILITY CLASS - ERROR GETTING CONNECTION");
        }
    }
}
