<%-- 
    Document   : checkout
    Created on : Aug 13, 2017, 6:35:24 PM
    Author     : mbenso14
--%>

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
  <script src="script/cc_validate.js"></script>
  
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
  <% int account_id = (Integer) session.getAttribute("token"); %>

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
        <li><a href="/ByteBookstore/Cart"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
      </ul>
    </div>
  </div>
</nav>


    <%
    
    if (session.getAttribute("token") == null) {
        response.sendRedirect("/ByteBookstore/account.jsp");
    }
        
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
        out.println("<div class=\"cell_5\">$"+ String.format("%.2f",cart.getCartItem(i).getPrice() * cart.getCartItem(i).getQuantity()) +"</div></div>");
    }
    
    out.println("<div class=\"main\">");
    out.println("<div class=\"cell_4\">Total:</div>");
    out.println("<div class=\"cell_5\">$" + String.format("%.2f",cart.getTotalCost()) + "</div></div>");
    
    out.println("</form>");
    %>

<br><hr><br>

<form name="cc_validate" action="CartCheckout" onsubmit="return validateForm()" method="post">

<fieldset>
    <legend>Payment Details</legend>
    
	<div class="buyer_info">
		<div class="main">Buyer Info</div>
		<div class="main">Name: <input type="text" name="buyerName"></div>
		<div class="main">Street: <input type="text" name="buyerStreet"></div>
		<div class="main">
			<div class="c_cell_1">City: <input type="text" name="buyerCity"></div>
			<div class="c_cell_2">State: <select name="buyerState">
                            <option value="Alabama">Alabama</option> 
                            <option value="Alaska">Alaska</option>
                            <option value="Arizona">Arizona</option> 
                            <option value="Arkansas">Arkansas</option> 
                            <option value="California">California</option> 
                            <option value="Colorado">Colorado</option> 
                            <option value="Connecticut">Connecticut</option> 
                            <option value="Delaware">Delaware</option> 
                            <option value="Florida">Florida</option> 
                            <option value="Georgia">Georgia</option> 
                            <option value="Hawaii">Hawaii</option> 
                            <option value="Idaho">Idaho</option> 
                            <option value="Illinois">Illinois</option>
                            <option vaule="Indiana">Indiana</option> 
                            <option value="Iowa">Iowa</option> 
                            <option value="Kansas">Kansas</option> 
                            <option value="Kentucky">Kentucky</option> 
                            <option value="Louisiana">Louisiana</option> 
                            <option value="Maine">Maine</option> 
                            <option value="Maryland">Maryland</option> 
                            <option value="Massachusetts">Massachusetts</option> 
                            <option value="Michigan">Michigan</option> 
                            <option value="Minnesota">Minnesota</option> 
                            <option value="Mississippi">Mississippi</option> 
                            <option value="Missouri">Missouri</option> 
                            <option value="Montana">Montana</option>
                            <option value="Nebraska">Nebraska</option> 
                            <option value="Nevada">Nevada</option> 
                            <option value="New Hampshire">New Hampshire</option> 
                            <option value="New Jersey">New Jersey</option> 
                            <option value="New Mexico">New Mexico</option> 
                            <option value="New York">New York</option> 
                            <option value="North Carolina">North Carolina</option> 
                            <option value="North Dakota">North Dakota</option> 
                            <option value="Ohio">Ohio</option> 
                            <option value="Oklahoma">Oklahoma</option> 
                            <option value="Oregon">Oregon</option> 
                            <option value="Pennsylvania">Pennsylvania</option> 
                            <Option value="Rhode Island">Rhode Island</option> 
                            <option value="South Carolina">South Carolina</option> 
                            <option value="South Dakota">South Dakota</option> 
                            <option value="Tennessee">Tennessee</option> 
                            <option value="Texas">Texas</option> 
                            <option value="Utah">Utah</option> 
                            <option value="Vermont">Vermont</option> 
                            <option value="Virginia">Virginia</option> 
                            <option value="Washington">Washington</option> 
                            <option value="West Virginia">West Virginia</option> 
                            <option value="Wisconsin">Wisconsin</option> 
                            <option value="Wyoming">Wyoming</option>
                        </select></div>
		</div>
		<div class="main">ZIPCODE: <input type="text" name="buyerZIP"></div>
	</div>
</fieldest>

<br><br>

<fieldset>
    <legend>Credit Card Type</legend>
	<div class="buyer_info">
		<div class="main">
			<div class="c_type_1"><input type="radio" name="cardtype" value="VISA" checked>VISA</div>
			<div class="c_type_2"><input type="radio" name="cardtype" value="MasterCard">Master Card</div>
			<div class="c_type_3"><input type="radio" name="cardtype" value="Discover">Discover</div>
		</div>

		<div class="main">Credit Card Number: <input type="text" name="cardnumber"></div>
		<div class="main">Credit Card Expiration Date (MM/YY): <input type="text" name="expiry"></div>
	</div>
</fieldset>

<br><br>

<input type="submit" value="Confirm Purchase">

</form>

<footer class="container-fluid text-center">
  <p>Byte Bookstore 2017</p>  </br>
  Johns Hopkins University </br>
  EN,605.782.81.SU17
</footer>

</body>
</html>
