<%-- 
    Document   : index
    Created on : Aug 19, 2017, 7:26:21 PM
    Author     : wjlax
--%>

<%@page import="com.bytebookstore.daoimpl.BookDaoImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.bytebookstore.models.Book"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<Book> allBooks = new ArrayList<>();
    allBooks = (ArrayList)request.getSession().getAttribute("display-books");
    if(allBooks == null){
        allBooks = (ArrayList) ((new BookDaoImpl()).getAllBookModels(0));
        request.getSession().setAttribute("display-books", allBooks);
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>ByteBookstore</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <style>
            /* Remove the navbar's default rounded borders and increase the bottom margin */ 
            .navbar {
                margin-bottom: 50px;
                border-radius: 0;
            }

            /* Remove the jumbotron's default bottom margin */ 
            .jumbotron {
                margin-bottom: 0;
            }

            /* Add a gray background color and some padding to the footer */
            footer {
                background-color: #f2f2f2;
                padding: 25px;
            }
        </style>
    </head>
    <body>

        <div class="jumbotron">
            <div class="container text-center">
                <img src="img/ByteBookstore.jpg">
            </div>
        </div>

        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>                        
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="/ByteBookstore/">Home</a></li>
                        <li><a href="search.html">Search Products</a></li>
                        <li><a href="stores.html">Stores</a></li>
                        <li><a href="contact.html">Contact</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="account.jsp"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
                        <li><a href="cart.jsp"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
                            <% if (request.getSession().getAttribute("user") != null) { %>
                        <li><a href="LoginServlet?tag=logout"><span class="glyphicon glyphicon-return"></span>Logout</a></li>
                        <% } %>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container">    
                <% for (Book book : allBooks) {%>
                <div class="col-md-4">
                    <div class="thumbnail" style="min-height:400px;">
                        <img src="data:image/jpg;base64,<%= book.getImage()%>" class="img img-responsive img-thumbnail" style="max-height:225px;">
                             <div class="caption">
                            <h3><%= book.getTitle()%></h3>
                            <p>Author goes here</p>
                            <p><a href="#" class="btn btn-primary" role="button">Add To Cart</a></p>
                        </div>
                    </div>
                </div>
                <% }%>
        </div><br>

        <footer class="container-fluid text-center">
            <p>Byte Bookstore 2017</p>  </br>
            Johns Hopkins University </br>
            EN,605.782.81.SU17
        </footer>

    </body>
</html>

