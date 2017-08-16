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


!!! DISPLAY CART CONTENTS !!!!

<br><hr><br>

<form name="cc_validate" action="" onsubmit="return validateForm()" method="post">

<fieldset>
    <legend>Payment Details</legend>

    <table>
        <tr><td>Credit Card Type</td>
        <td><input type="radio" name="cardtype" value="VISA" checked>VISA
        <input type="radio" name="cardtype" value="MasterCard">Master Card
        <input type="radio" name="cardtype" value="Discover">Discover</td></tr>


        <tr><td>Credit Card Number</td>
        <td><input type="text" name="cardnumber"></td></tr>

        <tr><td>Credit Card Expiration Date (MM/YY)</td>
        <td><input type="text" name="expiry"></td></tr>
    </table>
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
