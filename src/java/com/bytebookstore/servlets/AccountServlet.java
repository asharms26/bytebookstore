/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.servlets;

import com.bytebookstore.daoimpl.AuthorDaoImpl;
import com.bytebookstore.daoimpl.BookAutDaoImpl;
import com.bytebookstore.daoimpl.BookDaoImpl;
import com.bytebookstore.daoimpl.InventoryDaoImpl;
import com.bytebookstore.daoimpl.UserDaoImpl;
import com.bytebookstore.models.Author;
import com.bytebookstore.models.Book;
import com.bytebookstore.models.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wjlax
 */
public class AccountServlet extends HttpServlet {

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

        User user = (User) request.getSession().getAttribute("user");
        int logkey_id = user.getLogkey_id();

        if (request.getParameter("tag") != null) {
            switch ((String) request.getParameter("tag")) {
                case "UPDATE_INFO": {
                    User sessionUser = (User) request.getSession().getAttribute("user");
                    User newUser = new User();
                    newUser.setEmail((String) request.getParameter("email"));
                    newUser.setFirstName((String) request.getParameter("first-name"));
                    newUser.setLastName((String) request.getParameter("last-name"));
                    newUser.setPrivilege(sessionUser.getPrivilege());
                    newUser.setLogkey_id(sessionUser.getLogkey_id());

                    UserDaoImpl userDao = new UserDaoImpl();
                    boolean success = userDao.update(newUser);

                    if (success) {
                        request.getSession().setAttribute("user", newUser);
                    }
                }
                break;
                case "UPDATE_PASSWORD": {

                }
                break;
                case "ADD_BOOK": {
                    Author author = new Author();
                    author.setFirstName((String) request.getParameter("book-author-first-name"));
                    author.setLastName((String) request.getParameter("book-author-last-name"));

                    Book book = new Book();
                    book.setTitle((String) request.getParameter("book-title"));

                    String sanitizedPrice = ((String) request.getParameter("book-price")).replace("$", "");
                    book.setPrice(Double.valueOf(sanitizedPrice));

                    String isbn = "";
                    for (int i = 0; i < 12; i++) {
                        int val = (int) Math.ceil(Math.random() * 10);
                        isbn += Integer.toString(val);
                    }
                    book.setISBN(isbn);
                    book.setImage(((String) request.getParameter("img")).split(",")[1]);

                    com.bytebookstore.models.Inventory inventory = new com.bytebookstore.models.Inventory();
                    inventory.setInv(Integer.valueOf((String) request.getParameter("book-inventory")));

                    BookAutDaoImpl bookDao = new BookAutDaoImpl();
                    InventoryDaoImpl inventoryDao = new InventoryDaoImpl();

                    inventoryDao.create(inventory, book, author);
                    bookDao.create(author, book);
                }
                break;
            }
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"status\":\"success\"}");
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
