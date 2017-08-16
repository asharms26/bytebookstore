/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bytebookstore.servlets;

import com.bytebookstore.utilities.DBUtility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mbenso14
 */
public class SearchServlet extends HttpServlet {

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
            
            String queryString = request.getParameter("queryString");
            String querySel = request.getParameter("querySel");
            String query = "";

            String action = request.getParameter("action");

            try {
                Connection conn = DBUtility.ds.getConnection();
                
                if (!queryString.equals(""))
                    queryString = queryString.replaceAll("[^a-zA-Z0-9 ]", "");
                
                if (querySel.equals("Search by Title")) {                    
                    if (!queryString.equals("")) {
                        query = "SELECT BOOK.*, INVENTORY.price, INVENTORY.count, AUTHOR.lastname, AUTHOR.firstname FROM BOOK LEFT JOIN INVENTORY ON BOOK.ISBN=INVENTORY.ISBN LEFT JOIN BOOK_AUT ON BOOK.ISBN=BOOK_AUT.ISBN INNER JOIN AUTHOR ON AUTHOR.authorid=BOOK_AUT.aid where title LIKE \"%" + queryString + "%\";";
                    } else {
                        query = "SELECT BOOK.*, INVENTORY.price, INVENTORY.count, AUTHOR.lastname, AUTHOR.firstname FROM BOOK LEFT JOIN INVENTORY ON BOOK.ISBN=INVENTORY.ISBN LEFT JOIN BOOK_AUT ON BOOK.ISBN=BOOK_AUT.ISBN INNER JOIN AUTHOR ON AUTHOR.authorid=BOOK_AUT.aid;";
                    }
                }else if (querySel.equals("Search by Author")) {

                    if (!queryString.equals("")) {
                        String words[] = queryString.split("\\s+");
                        String wordlist;
                        
                        wordlist = "(\'" + words[0];

                        for(int i=1; i<words.length; i++) { 
                            wordlist = wordlist + "\',\'" + words[i];
                        }
                        
                        wordlist = wordlist + "\')";
                                                
                        query = "SELECT BOOK.*, INVENTORY.price, INVENTORY.count, AUTHOR.lastname, AUTHOR.firstname FROM BOOK LEFT JOIN INVENTORY ON BOOK.ISBN=INVENTORY.ISBN LEFT JOIN BOOK_AUT ON BOOK.ISBN=BOOK_AUT.ISBN INNER JOIN AUTHOR ON AUTHOR.authorid=BOOK_AUT.aid where (AUTHOR.lastname IN " + wordlist + " OR AUTHOR.firstname IN " + wordlist + ");";
                    } else {
                        query = "SELECT BOOK.*, INVENTORY.price, INVENTORY.count, AUTHOR.lastname, AUTHOR.firstname FROM BOOK LEFT JOIN INVENTORY ON BOOK.ISBN=INVENTORY.ISBN LEFT JOIN BOOK_AUT ON BOOK.ISBN=BOOK_AUT.ISBN INNER JOIN AUTHOR ON AUTHOR.authorid=BOOK_AUT.aid;";
                    }
                }else if (querySel.equals("Search by ISBN")){

                    if (!queryString.equals("")) {
                        query = "SELECT BOOK.*, INVENTORY.price, INVENTORY.count, AUTHOR.lastname, AUTHOR.firstname FROM BOOK LEFT JOIN INVENTORY ON BOOK.ISBN=INVENTORY.ISBN LEFT JOIN BOOK_AUT ON BOOK.ISBN=BOOK_AUT.ISBN INNER JOIN AUTHOR ON AUTHOR.authorid=BOOK_AUT.aid where BOOK.ISBN LIKE \"%" + queryString + "%\";";
                    } else {
                        query = "SELECT BOOK.*, INVENTORY.price, INVENTORY.count, AUTHOR.lastname, AUTHOR.firstname FROM BOOK LEFT JOIN INVENTORY ON BOOK.ISBN=INVENTORY.ISBN LEFT JOIN BOOK_AUT ON BOOK.ISBN=BOOK_AUT.ISBN INNER JOIN AUTHOR ON AUTHOR.authorid=BOOK_AUT.aid;";
                    }
                }
                
                ResultSet st = conn.createStatement().executeQuery(query);
                
                out.println("<!DOCTYPE html>");
                out.println("<html lang=\"en\">");
                out.println("<head>");
                out.println("<title>ByteBookstore</title>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
                out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">");
                out.println("<link href=\"style/style.css\" rel=\"stylesheet\" type=\"text/css\">");
                out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>");
                out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
                out.println("<style>");
                out.println(".navbar {");
                out.println("margin-bottom: 50px;");
                out.println("border-radius: 0;");
                out.println("}");
                out.println(".jumbotron {");
                out.println("margin-bottom: 0;");
                out.println("}");
                out.println("footer {");
                out.println("background-color: #f2f2f2;");
                out.println("padding: 25px;");
                out.println("}");
                out.println(".navbar .navbar-search .dropdown-menu { min-width: 25px; }");
                out.println(".dropdown-menu .label-icon { margin-left: 5px; }");
                out.println(".btn-outline {");
                out.println("background-color: transparent;");
                out.println("color: inherit;");
                out.println("transition: all .5s;");
                out.println("}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class=\"jumbotron\">");
                out.println("<div class=\"container text-center\">");
                out.println("<img src=\"img/ByteBookstore.jpg\">");
                out.println("</div>");
                out.println("</div>");
                out.println("<nav class=\"navbar navbar-inverse\">");
                out.println("<div class=\"container-fluid\">");
                out.println("<div class=\"navbar-header\">");
                out.println("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#myNavbar\">");
                out.println("<span class=\"icon-bar\"></span>");
                out.println("<span class=\"icon-bar\"></span>");
                out.println("<span class=\"icon-bar\"></span>");                     
                out.println("</button>");
                out.println("</div>");
                out.println("<div class=\"collapse navbar-collapse\" id=\"myNavbar\">");
                out.println("<ul class=\"nav navbar-nav\">");
                out.println("<li class=\"active\"><a href=\"index.html\">Home</a></li>");
                out.println("<li><a href=\"search.html\">Search Products</a></li>");
                out.println("<li><a href=\"stores.html\">Stores</a></li>");
                out.println("<li><a href=\"contact.html\">Contact</a></li>");
                out.println("</ul>");
                out.println("<ul class=\"nav navbar-nav navbar-right\">");
                out.println("<li><a href=\"account.jsp\"><span class=\"glyphicon glyphicon-user\"></span> Your Account</a></li>");
                out.println("<li><a href=\"cart.jsp\"><span class=\"glyphicon glyphicon-shopping-cart\"></span> Cart</a></li>");
                out.println("</ul>");
                out.println("</div>");
                out.println("</div>");
                out.println("</nav>");
                out.println("<div class=\"container\">");
                out.println("<div class=\"row\">");
                out.println("<nav class=\"navbar navbar-default\">");
                out.println("<div class=\"nav nav-justified navbar-nav\">");
                out.println("<form class=\"navbar-form navbar-search\" role=\"search\" action=\"search\" method=\"post\">");
                out.println("<div class=\"input-group\">");
                out.println("<div class=\"input-group-btn\">");
                out.println("<div class=\"form-group\">");
                out.println("<label for=\"sel1\">Select list:</label>");
                out.println("<select class=\"form-control\" name=\"querySel\" id=\"sel1\">");
                out.println("<option>Search by Title</option>");
                out.println("<option>Search by Author</option>");
                out.println("<option>Search by ISBN</option>");
                out.println("</select>");
                out.println("</div>");
                out.println("</div>");
                out.println("<input type=\"text\" name=\"queryString\" class=\"form-control\">");
                out.println("<div class=\"input-group-btn\">");
                out.println("<input type=\"submit\" name=\"action\" value=\"Go\" class=\"btn btn-search btn-default\">");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                
                if(st.next()) {
                    out.println("<form class=\"navbar-form navbar-search\" role=\"Cart\" action=\"Cart\" method=\"post\">");
                    out.println("<h1>Search Results</h1><br><br>");
                    
                    out.println("<div class=\"main\">");
                    out.println("<div class=\"cell_1\">image</div>");
                    out.println("<div class=\"cell_2\">Title - Author</div>");
                    out.println("<div class=\"cell_3\">ISBN</div>");
                    out.println("<div class=\"cell_4\">Cost</div>");
                    out.println("<div class=\"cell_5\">Inventory</div>");
                    out.println("</div>");

                    
                    out.println("<div class=\"back1\">");
                    out.println("<div class=\"cell_1\">" + st.getString("image") + "</div>");
                    out.println("<div class=\"cell_2\">" + st.getString("title") + " - " + st.getString("firstname") + " " + st.getString("lastname") + "</div>");
                    out.println("<div class=\"cell_3\">" + st.getString("ISBN") + "</div>");
                    out.println("<div class=\"cell_4\">Cost</div>");
                    out.println("<div class=\"cell_5\">"+ st.getString("count") + "</div>");
                    out.println("<div class=\"cell_6\"><button type=\"submit\" name=\"action\" Value=\"" + st.getString("ISBN") + "\";\">Add To Cart</button></div>");
                    out.println("</div>");
                    
                    int k=0;
                    
                    while(st.next()) {
                        if(k%2==0){
                            out.println("<div class=\"back2\">");
                        }else{
                            out.println("<div class=\"back1\">");
                        }
                        
                        k = k + 1;
                        
                        out.println("<div class=\"cell_1\">" + st.getString("image") + "</div>");
                        out.println("<div class=\"cell_2\">" + st.getString("title") + " - " + st.getString("firstname") + " " + st.getString("lastname") + "</div>");
                        out.println("<div class=\"cell_3\">" + st.getString("ISBN") + "</div>");
                        out.println("<div class=\"cell_4\">Cost</div>");
                        out.println("<div class=\"cell_5\">"+ st.getString("count") + "</div>");
                        out.println("<div class=\"cell_6\"><button type=\"submit\" name=\"action\" Value=\"" + st.getString("ISBN") + "\";\">Add To Cart</button></div>");
                        out.println("</div>");
                    }

                    conn.close();
                    
                    out.println("</form>");
                } else
                    out.println("<h1>No Results</h1>");

                out.println("</div>");
                out.println("</nav>");
                out.println("</div>");
                out.println("</div>");
                out.println("<footer class=\"container-fluid text-center\">");
                out.println("<p>Byte Bookstore 2017</p>  </br>");
                out.println("Johns Hopkins University </br>");
                out.println("EN,605.782.81.SU17");
                out.println("</footer>");
                out.println("</body>");
                out.println("</html>");
                
            } catch (SQLException ex) {
                Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            /* TODO output your page here. You may use following sample code. */

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
