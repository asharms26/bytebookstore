/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.dao;

import com.bytebookstore.models.Author;
import com.bytebookstore.models.Book;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public interface BookDao {
    public void setDataSource(DataSource ds);
    public boolean create(Book model);
    public Book getERModel(Integer id);
    public List<Book> getAllERModels(Integer id);
    public boolean delete(Book model);
    public boolean update(Book model);
}
