/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.servlets;

import com.bytebookstore.models.Cart;
import com.bytebookstore.models.User;
import com.bytebookstore.utilities.DBUtility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mbenso14
 */
@WebServlet(name = "Cart", urlPatterns = {"/Cart"})
public class CartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            User user = (User) session.getAttribute("user");
            
            String query;
            Cart cart;
            CallableStatement cStmt;            
            ResultSet st = null;
            
            String add = request.getParameter("add");
            String remove = request.getParameter("remove");

            Connection conn, iconn;
                        
            try {
                
                conn = DBUtility.ds.getConnection();

                if (session.getAttribute("cart") == null) {
                    cart = new Cart();
                } else {
                    cart = (Cart) session.getAttribute("cart");
                }
                
                if(user != null) {
                    if(!cart.isInital_synch()){                       
                        iconn = DBUtility.ds.getConnection();

                        query = "select logkey_id from USER where email=\"" + user.getEmail() + "\";";
                        
                        st = iconn.createStatement().executeQuery(query);

                        if(st.next()) {
                            user.setLogkey_id(st.getInt("logkey_id"));
                        }
                        
                        query = "SELECT USER_ORDER.*,BOOK.title, AUTHOR.firstname, AUTHOR.lastname, INVENTORY.price, INVENTORY.count AS icount FROM USER_ORDER "
                                + "LEFT JOIN BOOK ON USER_ORDER.ISBN=BOOK.ISBN "
                                + "LEFT JOIN BOOK_AUT ON BOOK_AUT.ISBN=USER_ORDER.ISBN "
                                + "LEFT JOIN AUTHOR ON AUTHOR.authorid=BOOK_AUT.aid "
                                + "LEFT JOIN INVENTORY ON USER_ORDER.ISBN=INVENTORY.ISBN "
                                + "WHERE USER_ORDER.tid = ("
                                + "select ORDER_LOG.tid from ORDER_LOG WHERE (ORDER_LOG.logkey_id=" + user.getLogkey_id() + " AND ORDER_LOG.status=\"ACTIVE\")"
                                + ");";

                        st = iconn.createStatement().executeQuery(query);
                        
                        while(st.next()){
                            
                            cart.addCartItem(st.getString("ISBN"), st.getString("title"), st.getString("firstname"), st.getString("lastname"), st.getDouble("price"), st.getInt("count"));
                            
                            if(cart.getCartItem(st.getString("ISBN")).getQuantity() > st.getInt("icount")) {
                                cart.getCartItem(st.getString("ISBN")).setQuantity(st.getInt("icount"));
                                System.out.println("error: can only add max available to cart");
                            }
                        }
                                                
                        for(int i=0; i<cart.getItemCount(); i++) {
                            cStmt = iconn.prepareCall("{ call spAddBookToOrder(?,?) }");
                            cStmt.setInt(1, user.getLogkey_id());
                            cStmt.setString(2, cart.getCartItem(i).getISBN());
                                                    
                            for(int j=0; j<cart.getCartItem(i).getQuantity();j++) 
                                cStmt.executeQuery();
                        }
                        
                        iconn.close();
                        cart.setInital_synch(true);
                    }
                    
                    if(remove != null && !remove.isEmpty()){
                        query = "select count from USER_ORDER where (ISBN=\"" + remove + 
                               "\" AND tid = (select tid from ORDER_LOG where logkey_id=" 
                                + user.getLogkey_id() + " and status=\"ACTIVE\"));";

                        st = conn.createStatement().executeQuery(query);
                        
                        if(st.next()) {
                        
                            if(st.getInt("count") - 1 > 0) {
                                cart.getCartItem(remove).setQuantity(st.getInt("count") - 1);

                                cStmt = conn.prepareCall("{call spUpdateBookOrder(?,?,?)}");
                                cStmt.setInt(1, user.getLogkey_id());
                                cStmt.setString(2, remove);
                                cStmt.setInt(3, st.getInt("count") - 1);
                                
                                cStmt.executeQuery();
                            } else {
                                cart.deleteCartItem(remove);

                                cStmt = conn.prepareCall("{ call spRemoveBookFromOrder(?,?) }");
                                cStmt.setInt(1, user.getLogkey_id());
                                cStmt.setString(2, remove);

                                cStmt.executeQuery();
                            }
                        }
                    }
                    
                    if(add != null && !add.isEmpty()) {
                                                
                        query = "SELECT INVENTORY.count as icount, USER_ORDER.count, USER_ORDER.ISBN FROM ORDER_LOG "
                            + "LEFT JOIN USER_ORDER ON ORDER_LOG.tid=USER_ORDER.tid "
                            + "LEFT JOIN INVENTORY ON INVENTORY.ISBN=USER_ORDER.ISBN "
                            + "WHERE status=\"ACTIVE\" AND ORDER_LOG.logkey_id="+ user.getLogkey_id() +" AND "
                            + "USER_ORDER.ISBN="+ add +";";

                        st = conn.createStatement().executeQuery(query);
                        
                        if(st.next()) {
                            if(st.getInt("count") < st.getInt("icount")) {
                                cart.getCartItem(add).setQuantity(st.getInt("count") + 1);

                                cStmt = conn.prepareCall("{call spUpdateBookOrder(?,?,?)}");
                                cStmt.setInt(1, user.getLogkey_id());
                                cStmt.setString(2, add);
                                cStmt.setInt(3, st.getInt("count") + 1);

                                cStmt.executeQuery();                           
                            } else {
                                System.out.println("cannot add to cart, not enough inventory");
                            }
                        } else {      
                            
                            query = "select count as icount from INVENTORY where ISBN=\"" + add + "\";";

                            st = conn.createStatement().executeQuery(query);

                            if(st.next() && st.getInt("icount") > 0) {
                                                                
                                query = "SELECT BOOK.title, AUTHOR.firstname, AUTHOR.lastname, INVENTORY.price, INVENTORY.count FROM BOOK "
                                        + "LEFT JOIN INVENTORY ON BOOK.ISBN = INVENTORY.ISBN "
                                        + "LEFT JOIN BOOK_AUT ON BOOK.ISBN = BOOK_AUT.ISBN "
                                        + "LEFT JOIN AUTHOR ON BOOK_AUT.aid=AUTHOR.authorid "
                                        + "WHERE BOOK.ISBN=\"" + add + "\";";

                                st = conn.createStatement().executeQuery(query);
                                
                                if(st.next()) {

                                    cart.addCartItem(add, st.getString("title"), st.getString("firstname"), st.getString("lastname"), st.getDouble("price"), 1);
                                    
                                    cStmt = conn.prepareCall("{call spAddBookToOrder(?,?)}");
                                    cStmt.setInt(1, user.getLogkey_id());
                                    cStmt.setString(2, add);

                                    cStmt.executeQuery();
                                }
                            }
                        }
                    }
                    
                } else { // no user logged in starts here
                    if(remove != null && !remove.isEmpty()){
                        if(cart.getCartItem(remove).getQuantity() > 1) {
                            cart.getCartItem(remove).setQuantity(cart.getCartItem(remove).getQuantity()-1);
                        } else {
                            cart.deleteCartItem(remove);
                        }
                    } 
                    
                    if(add != null && !add.isEmpty()) {
                        query = "select count as icount from INVENTORY where ISBN=\""+ add +"\";";

                        st = conn.createStatement().executeQuery(query);

                        if(st.next()) {                            
                            if(cart.getCartItem(add) != null) {
                                if(cart.getCartItem(add).getQuantity() < st.getInt("icount")){
                                    cart.getCartItem(add).setQuantity(cart.getCartItem(add).getQuantity()+1);
                                } else {
                                    System.out.println("current inventory: " + st.getInt("count"));                            
                                }
                            } else {
                                if(st.getInt("icount") > 0) {
                                    
                                    query = "SELECT BOOK.title, AUTHOR.firstname, AUTHOR.lastname, INVENTORY.price, INVENTORY.count FROM BOOK "
                                            + "LEFT JOIN INVENTORY ON BOOK.ISBN = INVENTORY.ISBN "
                                            + "LEFT JOIN BOOK_AUT ON BOOK.ISBN = BOOK_AUT.ISBN "
                                            + "LEFT JOIN AUTHOR ON BOOK_AUT.aid=AUTHOR.authorid "
                                            + "WHERE BOOK.ISBN=\"" + add + "\";";

                                    st = conn.createStatement().executeQuery(query);

                                    if(st.next()) {                                        
                                        cart.addCartItem(add, st.getString("title"), st.getString("firstname"), st.getString("lastname"), st.getDouble("price"));
                                    }
                                } else {
                                    System.out.println("out of stock");
                                }
                            }
                        }   
                    }
                }
                
                conn.close();

                session.setAttribute("cart", cart);

                response.sendRedirect("https://localhost:8443/ByteBookstore/cart.jsp");            
            } catch (SQLException ex) {
                Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
