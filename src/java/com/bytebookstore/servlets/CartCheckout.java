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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
@WebServlet(name = "CartCheckout", urlPatterns = {"/CartCheckout"})
public class CartCheckout extends HttpServlet {

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
        
        try {
            HttpSession session = request.getSession();
            
            Cart cart = (Cart)request.getSession().getAttribute("cart");
            User user = (User) session.getAttribute("user");
            int token = user.getLogkey_id();

            String query;
            CallableStatement cStmt;
            ResultSet st = null;
            
            Connection conn = DBUtility.ds.getConnection();
            
            for(int i=0; i < cart.getItemCount(); i++) {
                query = "SELECT INVENTORY.count as icount, USER_ORDER.count, USER_ORDER.ISBN FROM ORDER_LOG "
                        + "LEFT JOIN USER_ORDER ON ORDER_LOG.tid=USER_ORDER.tid "
                        + "LEFT JOIN INVENTORY ON INVENTORY.ISBN=USER_ORDER.ISBN "
                        + "WHERE status=\"ACTIVE\" AND ORDER_LOG.logkey_id="+ token +" AND "
                        + "USER_ORDER.ISBN="+ cart.getCartItem(i).getISBN() +";";

                st = conn.createStatement().executeQuery(query);

                if(st.next()) {
                    
                    if(cart.getCartItem(i).getQuantity() <= st.getInt("icount")) {
                        int newvalue = st.getInt("icount") - cart.getCartItem(i).getQuantity();

                        query = "UPDATE INVENTORY SET count=" + newvalue + " WHERE ISBN = " 
                                + cart.getCartItem(i).getISBN() + ";";

                        conn.createStatement().executeUpdate(query);

                        System.out.println("purchased: " + cart.getCartItem(i).getQuantity() + " of " + cart.getCartItem(i).getISBN());

                        cart.getCartItem(i).setPurchased(true);
                    } else {
                        System.out.println("failed to purchase: " + cart.getCartItem(i).getISBN() + "inventory: " + st.getInt("icount"));
                        
                        cStmt = conn.prepareCall("{call spRemoveBookFromOrder(?,?)}");
                        cStmt.setInt(1,token);
                        cStmt.setString(2, cart.getCartItem(i).getISBN());
                        
                        cStmt.executeQuery();
                    }
                }
            }
            
            cStmt = conn.prepareCall("{ call spOrderStatusUpdate(?)}");
            cStmt.setInt(1, token);
            cStmt.executeQuery();
            
            String subject = "Order Receipt - ByteBookstore";
            
            String body = "This is the confirmation e-mail for your recently submitted order from " +
                    "The ByteBookstre.\n\n";

            for(int i=0; i<cart.getItemCount(); i++) {
                if(cart.getCartItem(i).isPurchased())
                    body = body + "\n  +  Succesful purchase: " + cart.getCartItem(i).getQuantity() + " copies of: " + cart.getCartItem(i).getTitle() + " at $" + cart.getCartItem(i).getPrice();
                else
                    body = body + "\n  -  Unsuccesful purchase: " + cart.getCartItem(i).getQuantity() + "copies of: " + cart.getCartItem(i).getTitle() +" at $" + cart.getCartItem(i).getPrice();
            }
            
            body = body + "\n\n Final Charge: " + cart.getFinalCost();
            
            body = body + "\n\n Customer Name: " + request.getParameter("buyerName");
            body = body + "\n\n Customer Address " + request.getParameter("buyerStreet");
            body = body + ", " + request.getParameter("buyerCity") + ", " + request.getParameter("buyerState");
            body = body + ", " + request.getParameter("buyerZIP");
           
            final String username = "EN605.782";
            final String password = "security alert";
            
            Properties props = new Properties();
            
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", 587);
            
            Session pSession = Session.getInstance(props,new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            
            try {
                Message message = new MimeMessage(pSession);
                
                try {
                    message.setFrom(new InternetAddress("EN605.782@gmail.com"));
                } catch (MessagingException ex) {
                    Logger.getLogger(CartCheckout.class.getName()).log(Level.SEVERE, null, ex);
                }

                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("EN605.782@gmail.com"));
                
                message.setSubject(subject);
                message.setText(body);
                
                Transport.send(message);

                conn.close();
                
                cart.clear();
                
                response.sendRedirect("/ByteBookstore/account.jsp");
            } catch (SendFailedException e) {
                System.out.println(e);
            } catch (MessagingException e) {
                System.out.println(e.toString());
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CartCheckout.class.getName()).log(Level.SEVERE, null,ex);
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
