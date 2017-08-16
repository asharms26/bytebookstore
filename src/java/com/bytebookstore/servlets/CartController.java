/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.servlets;

import com.bytebookstore.models.Cart;
import com.bytebookstore.utilities.DBUtility;
import java.io.IOException;
import java.io.PrintWriter;
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
            
            String action = request.getParameter("action");
            String add = request.getParameter("add");
            String remove = request.getParameter("remove");
            
            String query;
            Cart cart;
            
            if (session.getAttribute("cart") == null) {
                cart = new Cart();
            } else {
                cart = (Cart) session.getAttribute("cart");
            }
               
            
            try {
                if(remove != null && !remove.isEmpty()){                    
                    if(cart.getCartItem(remove).getQuantity() > 1) {
                        cart.getCartItem(remove).setQuantity(cart.getCartItem(remove).getQuantity()-1);
                    } else {
                        cart.deleteCartItem(remove);
                    }
                } else {
                    Connection conn = DBUtility.ds.getConnection();

                    query = "SELECT BOOK.*, INVENTORY.price, INVENTORY.count, AUTHOR.lastname, AUTHOR.firstname FROM BOOK LEFT JOIN INVENTORY ON BOOK.ISBN=INVENTORY.ISBN LEFT JOIN BOOK_AUT ON BOOK.ISBN=BOOK_AUT.ISBN INNER JOIN AUTHOR ON AUTHOR.authorid=BOOK_AUT.aid WHERE BOOK.ISBN ='" + action + "';";

                    ResultSet st = conn.createStatement().executeQuery(query);

                    if(st.next()) {
                        if(cart.getCartItem(st.getString("ISBN"))!= null) {
                            if(st.getInt("count") >= cart.getCartItem(st.getString("ISBN")).getQuantity()+1){
                                cart.addCartItem(st.getString("ISBN"), st.getString("title"), st.getString("firstname"), st.getString("lastname"), st.getDouble("price"));
                            } else {
                                System.out.println("error .. inventory too low to add another");                            
                            }
                        } else if(st.getInt("count")>0) {
                            cart.addCartItem(st.getString("ISBN"), st.getString("title"), st.getString("firstname"), st.getString("lastname"), st.getDouble("price"));
                        } else {
                            System.out.println("no inventory");
                        }
                    }

                    conn.close();

                }} catch (SQLException ex) {
                Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            session.setAttribute("cart", cart);
            
            response.sendRedirect("https://localhost:8443/ByteBookstore/cart.jsp");
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
