/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.daoimpl;

import com.bytebookstore.dao.AuthorDao;
import com.bytebookstore.models.Author;
import com.bytebookstore.utilities.DBUtility;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public class AuthorDaoImpl implements AuthorDao {

    DataSource ds;
    
    @Override
    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public boolean create(Author model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public Author getAuthorModel(Integer id) {
        Author author = new Author();
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return author;
    }

    @Override
    public List<Author> getAllAuthorModels(Integer id) {
        List<Author> author = new ArrayList<>();
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return author;
    }

    @Override
    public boolean delete(Author model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean update(Author model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }
    
}
