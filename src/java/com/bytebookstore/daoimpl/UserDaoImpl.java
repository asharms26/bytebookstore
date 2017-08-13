/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.daoimpl;

import com.bytebookstore.dao.UserDao;
import com.bytebookstore.models.User;
import com.bytebookstore.utilities.DBUtility;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public class UserDaoImpl implements UserDao{

    @Override
    public void setDataSource(DataSource ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(User model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            CallableStatement cStmt = conn.prepareCall("{call spUserInsert(?, ?, ?, ?)}");
            cStmt.setString(1, model.getFirstName());
            cStmt.setString(2, model.getLastName());
            cStmt.setString(3, model.getEmail());
            cStmt.setBoolean(4, model.getPrivilege());
            valid = cStmt.execute();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public User getUserModel(Integer id) {
        User user = new User();
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return user;
    }

    @Override
    public List<User> getAllUserModels(Integer id) {
        List<User> users = new ArrayList<>();
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public boolean delete(User model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean update(User model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }
    
}
