/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.daoimpl;

import com.bytebookstore.dao.InventoryDao;
import com.bytebookstore.models.Author;
import com.bytebookstore.models.Book;
import com.bytebookstore.models.Inventory;
import com.bytebookstore.models.User;
import com.bytebookstore.utilities.DBUtility;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author wjlax
 */
public class InventoryDaoImpl implements InventoryDao{

    @Override
    public void setDataSource(DataSource ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Inventory model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }
    
    public boolean create(Inventory inventory, Book book, Author auth, User user){
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            CallableStatement cStmt = conn.prepareCall("{call spBookInventoryInsert(?,?,?,?,?,?,?,?)}");
            cStmt.setString(1,book.getISBN());
            cStmt.setString(2, book.getTitle());
            byte[] istr = Base64.decodeBase64(book.getImage());
            InputStream inputStream = new ByteArrayInputStream(istr);
            cStmt.setBinaryStream(3, inputStream);
            cStmt.setString(4, auth.getFirstName());
            cStmt.setString(5, auth.getLastName());
            cStmt.setDouble(6, book.getPrice());
            cStmt.setInt(7,inventory.getInv());
            cStmt.setInt(8, user.getLogkey_id());
            valid = cStmt.execute();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public Inventory getInventoryModel(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Inventory> getAllInventoryModels(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Inventory model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean update(Inventory model) {
        boolean valid = true;
        try(Connection conn = DBUtility.ds.getConnection()){
            
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }
    
}
