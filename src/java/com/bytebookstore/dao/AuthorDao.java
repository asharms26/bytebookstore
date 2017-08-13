/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.dao;

import com.bytebookstore.models.Author;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public interface AuthorDao {
    public void setDataSource(DataSource ds);
    public boolean create(Author model);
    public Author getAuthorModel(Integer id);
    public List<Author> getAllAuthorModels(Integer id);
    public boolean delete(Author model);
    public boolean update(Author model);
}
