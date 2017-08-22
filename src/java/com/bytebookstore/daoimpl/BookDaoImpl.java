/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.daoimpl;

import com.bytebookstore.dao.BookDao;
import com.bytebookstore.models.Author;
import com.bytebookstore.models.Book;
import com.bytebookstore.models.Inventory;
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
public class BookDaoImpl implements BookDao {

    @Override
    public void setDataSource(DataSource ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Book model) {
        boolean valid = true;
        try (Connection conn = DBUtility.ds.getConnection()) {
            CallableStatement cStmt = conn.prepareCall("{call spRegdataInsert(?,?,?,?,?)}");

            valid = cStmt.execute();
        } catch (Exception ex) {
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
        try (Connection conn = DBUtility.ds.getConnection()) {
            String query = "";
            if(id == 0){
                query = "SELECT * FROM bytebooks.BOOK INNER JOIN bytebooks.BOOK_AUT ON bytebooks.BOOK.ISBN = bytebooks.BOOK_AUT.ISBN\n"
                    + "INNER JOIN bytebooks.INVENTORY ON bytebooks.BOOK.ISBN = bytebooks.INVENTORY.ISBN\n"
                    + "INNER JOIN bytebooks.AUTHOR ON bytebooks.BOOK_AUT.aid = bytebooks.AUTHOR.authorid";
            } else {
                query = "SELECT * FROM bytebooks.BOOK INNER JOIN bytebooks.BOOK_AUT ON bytebooks.BOOK.ISBN = bytebooks.BOOK_AUT.ISBN\n"
                    + "INNER JOIN bytebooks.INVENTORY ON bytebooks.BOOK.ISBN = bytebooks.INVENTORY.ISBN\n"
                    + "INNER JOIN bytebooks.AUTHOR ON bytebooks.BOOK_AUT.aid = bytebooks.AUTHOR.authorid WHERE bytebooks.BOOK.logkey_id = " + id;
            }

            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                
                String title = rs.getString("title");
                boolean duplicate = false;
                for(int i = 0; i < books.size(); i++){
                    if(title.equalsIgnoreCase(books.get(i).getTitle())){
                        duplicate = true;
                        break;
                    }
                }
                
                //Don't add duplicate's
                if(duplicate){
                    continue;
                }
                
                Book book = new Book();
                book.setISBN(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                Blob blob = rs.getBlob("image");
                byte[] bytes = blob.getBytes(1, (int) blob.length());
                String imgString = Base64.getEncoder().encodeToString(bytes);
                book.setImage(imgString);
                
                Author author = new Author();
                author.setFirstName((String)rs.getString("firstname"));
                author.setLastName((String)rs.getString("lastname"));
                author.setAuthorId((int)rs.getInt("aid"));
                
                Inventory inventory = new Inventory();
                inventory.setInv((int)rs.getInt("count"));
                
                book.setAuthor(author);
                book.setInventory(inventory);
                book.setPrice((double)rs.getDouble("price"));
                
                books.add(book);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            valid = false;
        }
        return books;
    }

    @Override
    public boolean delete(Book model) {
        boolean valid = true;
        try (Connection conn = DBUtility.ds.getConnection()) {

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean update(Book model) {
        boolean valid = true;
        try (Connection conn = DBUtility.ds.getConnection()) {

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            valid = false;
        }
        return valid;
    }

}
