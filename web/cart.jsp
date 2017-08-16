<%-- 
    Document   : cart
    Created on : Aug 13, 2017, 12:57:12 AM
    Author     : mbenso14
--%>

<%@page import="com.bytebookstore.models.CartItem"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>ByteBookstore</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link href="style/style.css" rel="stylesheet" type="text/css">
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

  <%@ page import="com.bytebookstore.models.Cart" %>
  <% Cart cart = (Cart)session.getAttribute("cart"); %>
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
        <li class="active"><a href="index.html">Home</a></li>
        <li><a href="search.html">Search Products</a></li>
        <li><a href="stores.html">Stores</a></li>
        <li><a href="contact.html">Contact</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="account.jsp"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
        <li><a href="cart.jsp"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
      </ul>
    </div>
  </div>
</nav>

    
<div class="container">
<div class="row">
<nav class="navbar navbar-default">
<div class="nav nav-justified navbar-nav">
    
    <%
        
    out.println("<form class=\"navbar-form navbar-search\" role=\"Cart\" action=\"Cart\" method=\"post\">");

    if (session.getAttribute("cart") == null) {
        cart = new Cart();
    } else {
        cart = (Cart) session.getAttribute("cart");
    }
    
    if(cart.getItemCount()>0){
        out.println("<div class=\"main\"><div class=\"cell_1\">Title - Author</div>");
        out.println("<div class=\"cell_2\">ISBN</div><div class=\"cell_3\">Quantity</div><div class=\"cell_4\">Item Price</div>");
        out.println("<div class=\"cell_5\">Total Cost</div></div>");
    } else {
        out.println("<div class=\"main\">Cart Is Empty</div>");
    }
    
    for(int i=0; i<cart.getItemCount(); i++) {
        if(i%2==0){
            out.println("<div class=\"back1\">");
        } else {
            out.println("<div class=\"back2\">");
        }
        out.println("<div class=\"cell_1\">" + cart.getCartItem(i).getTitle() + " - " + cart.getCartItem(i).getFirstName() + " " + cart.getCartItem(i).getLastName() + "</div>");
        out.println("<div class=\"cell_2\">" + cart.getCartItem(i).getISBN() + "</div>");
        out.println("<div class=\"cell_3\">" + cart.getCartItem(i).getQuantity() + "</div>");
        out.println("<div class=\"cell_4\">$"+ String.format("%.2f",cart.getCartItem(i).getPrice()) +"</div>");
        out.println("<div class=\"cell_5\">$"+ String.format("%.2f",cart.getCartItem(i).getPrice() * cart.getCartItem(i).getQuantity()) +"</div>");
        out.println("<div class=\"cell_6_1\"><button type=\"submit\" name=\"action\" Value=\"" + cart.getCartItem(i).getISBN() + "\";\">+</button></div>");
        out.println("<div class=\"cell_7\"><button type=\"submit\" name=\"remove\" Value=\"" + cart.getCartItem(i).getISBN() + "\";\">-</button></div></div>");
    }
    
    out.println("<div class=\"main\">");
    out.println("<div class=\"cell_4\">Total:</div>");
    out.println("<div class=\"cell_5\">$" + String.format("%.2f",cart.getTotalCost()) + "</div></div>");
    
    out.println("</form>");
    %>

    <%
    if(cart.getItemCount()>0) {
        out.println("<br><br><br>");
        out.println("<input type=\"button\" name=\"pay\" VALUE=\"Secure Check Out\" onclick=\"window.location='https://localhost:8443/ByteBookstore/checkout.jsp';\">");
    }

    %>

</div></nav></div></div>
    
<footer class="container-fluid text-center">
  <p>Byte Bookstore 2017</p>  </br>
  Johns Hopkins University </br>
  EN,605.782.81.SU17
</footer>

</body>
</html>