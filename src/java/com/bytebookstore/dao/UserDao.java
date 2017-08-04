/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.dao;

import com.bytebookstore.models.Author;
import com.bytebookstore.models.User;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public interface UserDao {
    public void setDataSource(DataSource ds);
    public boolean create(User model);
    public User getERModel(Integer id);
    public List<User> getAllERModels(Integer id);
    public boolean delete(User model);
    public boolean update(User model);
}
