/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.daoimpl;

import com.bytebookstore.dao.BookAutDao;
import com.bytebookstore.models.Author;
import com.bytebookstore.models.Book;
import com.bytebookstore.utilities.DBUtility;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author wjlax
 */
public class BookAutDaoImpl implements BookAutDao{

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
    
    public boolean create(Author author, Book book) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){ // << ALL WE HAVE TO DO TO CREATE A POOLED CONNECTION!
            CallableStatement cStmt = conn.prepareCall("{call spBookAuthorInsert(?,?,?)}");
            cStmt.setString(1, author.getFirstName());
            cStmt.setString(2, author.getLastName());
            cStmt.setString(3, book.getISBN());
            valid = cStmt.execute();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid; 
    }

    @Override
    public Author getBookAutModel(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Author> getAllBookAutModels(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Author model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Author model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
