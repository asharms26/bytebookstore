/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.servlets;

import com.bytebookstore.daoimpl.RegDataDaoImpl;
import com.bytebookstore.daoimpl.UserDaoImpl;
import com.bytebookstore.models.RegData;
import com.bytebookstore.models.User;
import com.bytebookstore.utilities.DBUtility;
import com.bytebookstore.utilities.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wjlax
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = {"/RegistrationServlet"})
public class RegistrationServlet extends HttpServlet {

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
        try (Connection conn = DBUtility.ds.getConnection()) {
            
            String firstName = request.getParameter("first-name");
            String lastName = request.getParameter("last-name");
            String password = request.getParameter("first-pass");
            String email = request.getParameter("registration-email");
            
            boolean privilege = true;
            
            if(request.getParameter("privilege").contains("false")){
                privilege = false;
            }
            
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPrivilege(privilege);
            
            String hashedPass = Utility.hashPassword(password);
            RegData regData = new RegData();
            regData.setPw(hashedPass);
            
            RegDataDaoImpl regDao = new RegDataDaoImpl();
            regDao.register(regData,user);
            
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"status\":\"success\"}");
            out.flush();
            
        } catch (Exception ex) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"status\":\"failure\"}");
            out.flush();
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
