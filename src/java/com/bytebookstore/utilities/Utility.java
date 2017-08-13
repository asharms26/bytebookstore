/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.utilities;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author wjlax
 */
public class Utility {

    public static String hashPassword(String pw) {
        
        if (pw.length() == 0) {
            return null;
        }

        String hashedPw = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(pw.getBytes());
            for (int i = 0; i < bytes.length; i++) {
                hashedPw += bytes[i];
            }
        } catch (Exception ex) {
            hashedPw = null;
        }

        return hashedPw;
        
    }

    public static boolean checkLogin(String email, String pw) {
        boolean match = false;
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(pw.getBytes());
            String hashedPw = "";
            for (int i = 0; i < bytes.length; i++) {
                hashedPw += bytes[i];
            }

            try (Connection conn = DBUtility.ds.getConnection()) {
                String query = "SELECT * FROM USER INNER JOIN REGDATA ON USER.logkey_id = REGDATA.logkey_id "
                        + "WHERE USER.email = ?";
                PreparedStatement stm = conn.prepareStatement(query);
                stm.setString(1, email);
                ResultSet rs = stm.executeQuery();
                rs.last();
                int count = rs.getRow();
                rs.beforeFirst();

                if (count > 0) {
                    String pass = rs.getString("pw");
                    if (pass.equalsIgnoreCase((pw))) {
                        match = true;
                    }
                }

            } catch (Exception ex) {
                System.out.println("Exception in check login method");
            }

        } catch (Exception ex) {
            System.out.println("Exception in check login method");
        }

        return match;
    }
}
