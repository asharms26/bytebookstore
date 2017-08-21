/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.daoimpl;

import com.bytebookstore.dao.BookDao;
import com.bytebookstore.models.Book;
import com.bytebookstore.utilities.DBUtility;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.Base64;


/**
 *
 * @author wjlax
 */
public class BookDaoImpl implements BookDao{

    @Override
    public void setDataSource(DataSource ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Book model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            CallableStatement cStmt = conn.prepareCall("{call spRegdataInsert(?,?,?,?,?)}");

            valid = cStmt.execute();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public Book getBookModel(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> getAllBookModels(Integer id) {
        List<Book> books = new ArrayList<>();
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM BOOK");
            while(rs.next()){
                Book book = new Book();
                book.setISBN(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                Blob blob = rs.getBlob("image");
                byte[] bytes = blob.getBytes(1, (int)blob.length());
                String imgString = Base64.getEncoder().encodeToString(bytes);
                book.setImage(imgString);
                books.add(book);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return books;
    }

    @Override
    public boolean delete(Book model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean update(Book model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }
    
}
