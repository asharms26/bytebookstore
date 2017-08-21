<%-- 
    Document   : account
    Created on : Aug 13, 2017, 1:37:13 PM
    Author     : wjlax
--%>

<%@page import="com.bytebookstore.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = null;
    if (request.getSession().getAttribute("user") != null) {
        user = (User) request.getSession().getAttribute("user");
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Bootstrap Example</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="script/utility.js"></script>

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

            /* nav bar search box - drop down menu button */
            .navbar .navbar-search .dropdown-menu { min-width: 25px; }
            .dropdown-menu .label-icon { margin-left: 5px; }
            .btn-outline {
                background-color: transparent;
                color: inherit;
                transition: all .5s;
            }

            #account-creation-container{
                padding-top:75px;
                padding-bottom:100px;
            }

            #registration-form, #login-form{
                background:steelblue;
                padding:10px;
                color:white;
            }

            .wrapper{
                background:rgba(0,0,0,0.1);
                border:1px solid lightgrey;
                padding:5px 10px;

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
                        <li><a href="/ByteBookstore/">Home</a></li>
                        <li><a href="search.html">Search Products</a></li>
                        <li><a href="stores.html">Stores</a></li>
                        <li><a href="contact.html">Contact</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="active"><a href="account.jsp"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
                        <li><a href="/ByteBookstore/Cart"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
                            <% if (request.getSession().getAttribute("user") != null) { %>
                        <li><a href="LoginServlet?tag=logout"><span class="glyphicon glyphicon-return"></span>Logout</a></li>
                            <% } %>
                    </ul>
                </div>
            </div>
        </nav>

        <% if (user == null) { %>
        <div class="container" id="account-creation-container">
            <div class="col-md-6">
                <form id="login-form">
                    <h2>Login</h2><br/>
                    <div class="form-group">
                        <label><strong>Email</strong></label>
                        <input class="form-control" type="email" placeholder="Enter Email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label><strong>Password</strong></label>
                        <input class="form-control" type="password" placeholder="Enter Password" name="password" required>
                    </div>
                    <input type="submit" class="btn btn-default"/>
                </form>
            </div>
            <div class="col-md-6">
                <form id="registration-form">
                    <h2>Register With Us</h2><br/>
                    <div class="form-group">
                        <label><strong>Email</strong></label>
                        <input class="form-control" type="email" placeholder="Enter Email" name="registration-email" required />
                    </div>
                    <div class="form-group">
                        <label><strong>First Name</strong></label>
                        <input class="form-control" type="text" placeholder="Enter First name" name="first-name" required />
                    </div>
                    <div class="form-group">
                        <label><strong>Second Name</strong></label>
                        <input class="form-control" type="text" placeholder="Enter Last Name" name="last-name" required />
                    </div>
                    <div class="form-group">
                        <label><strong>Enter Password</strong></label>
                        <input class="form-control" type="password" placeholder="Enter Password" name="first-pass" required />
                    </div>

                    <div class="form-group">
                        <label><strong>Re-Enter Password</strong></label>
                        <input class="form-control" type="password" placeholder="Re-Enter Password" name="second-pass" required />
                    </div>

                    <div class="form-group">
                        <label><input type="radio" name="privilege" value="false" class="radio radio-inline" style="vertical-align:top;margin-right:10px;"/>Buyer</label><br/>
                        <label><input type="radio" name="privilege" value="true" class="radio radio-inline" style="vertical-align:top;margin-right:10px;"/>Merchant</label>
                    </div>

                    <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>

                    <div class="clearfix">
                        <input type="submit" class="btn btn-default"/>
                    </div>   
                </form>
            </div>
        </div>    

        <% } else {%>
        <div class="container" style="padding-top:50px; padding-bottom:50px;">
            <h1 style="text-align:center; width:auto; margin:0 auto; padding:15px 10px;margin-bottom:30px; background:steelblue;color:white; border-bottom:10px solid silver;">User Dashboard</h1>

            <div class="row" style="margin-bottom:50px;">
                <div class="col-md-4">
                    <h1><%= user.getFirstName() + " " + user.getLastName()%></h1>
                    <h3><%= user.getPrivilege() ? "Merchant" : "Buyer"%></h3>
                    <h3><%= user.getEmail()%></h3>
                </div>
                <div class="col-md-4">
                    <div class="wrapper">
                        <h1>Edit Information</h1>
                        <form id="update-info-form">
                            <div class="form-group">
                                <label for="first-name">First Name</label>
                                <input type="text" class="form-control" name="first-name"/>
                            </div>
                            <div class="form-group">
                                <label for="first-name">Last Name</label>
                                <input type="text" class="form-control" name="last-name"/>
                            </div> 
                            <div class="form-group">
                                <label for="first-name">Email:</label>
                                <input type="email" class="form-control" name="email"/>
                            </div>
                            <input type="submit" class="btn btn-default"/>
                        </form>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="wrapper">
                        <h1>Update Password</h1>
                        <form id="update-password-form">
                            <div class="form-group">
                                <label for="old-password">Enter Old Password</label>
                                <input type="password" class="form-control" name="old-password"/>
                            </div>
                            <div class="form-group">
                                <label for="new-pass-one">Enter New Password</label>
                                <input type="password" class="form-control" name="new-pass-one"/>
                            </div> 
                            <div class="form-group">
                                <label for="new-pass-two">Re-Enter New Password</label>
                                <input type="password" class="form-control" name="new-pass-two"/>
                            </div>
                            <input type="submit" class="btn btn-default"/>
                        </form>
                    </div>
                </div>
            </div>
            <div class="row">
                <% if (user.getPrivilege()) { %>
                <div class="col-md-4">
                    <div class="wrapper">
                        <h1>Add a Book!</h1>
                        <form id="add-book-form">
                            <div class="form-group">
                                <label for="book-title">Book Title</label>
                                <input type="text" class="form-control" name="book-title" required/>
                            </div>
                            <div class="form-group">
                                <label for="book-author-first-name">Book Author First Name</label>
                                <input type="text" class="form-control" name="book-author-first-name" required/>
                            </div>
                            <div class="form-group">
                                <label for="book-author-last-name">Book Author Last Name</label>
                                <input type="text" class="form-control" name="book-author-last-name" required/>
                            </div>
                            <div class="form-group">
                                <label for="book-price">Price</label>
                                <input type="text" class="form-control" name="book-price" pattern="^(\d*\.\d{1,2}|\d+)$" required/>
                            </div>
                            <div class="form-group">
                                <label for="book-inventory">Inventory</label>
                                <input type="text" class="form-control" name="book-inventory" pattern="^(0|[1-9][0-9]*)$" required/>
                            </div>
                            <div class="form-group">
                                <label for="book-image">Book Image</label>
                                <input type="file" class="form-control" name="book-image" required/>
                            </div>
                            <input class="btn btn-default" type="submit"/>
                        </form>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="wrapper">
                        <h1>Book Uploads</h1>
                    </div>
                </div>
                <% } %>
                <div class="col-md-4">
                    <div class="wrapper">
                        <h1>Past Orders</h1>
                    </div>
                </div>

            </div>
        </div>

        <% }%>


        <div id="modal" title="Process Result">
            <p id="modal-content"></p>
        </div>


        <script>

            var dialogCloseSuccess = function () {
                window.location.reload();
            }

            var dialogCloseFailure = function () {
                //no-op
            }

            var showDialog = function (closeFunct) {
                $("#modal").dialog({
                    resizable: false,
                    height: "auto",
                    width: 400,
                    modal: true,
                    close: closeFunct,
                    show: {
                        effect: "fade",
                        duration: 150,
                        direction: "up"
                    },
                    hide: {
                        effect: "fade",
                        duration: 150,
                        direction: "up"
                    },
                    buttons: {
                        "OK": function () {
                            $(this).dialog("close");
                        }
                    }
                });
            }

            function checkPasswords() {
                var firstPass = $("#registration-form input[name='first-pass']");
                var secondPass = $("#registration-form input[name='second-pass']");
                return firstPass.val() == secondPass.val() ? true : false;
            }

            $("#registration-form").submit(function (e) {
                e.preventDefault();
                if (checkPasswords()) {
                    var route = "RegistrationServlet";
                    var dat = $(this).serializeArray();
                    var success = function (response) {
                        if (response.status == "success") {
                            $("#modal-content").html("Success you have registered! Please login on page reload.");
                            showDialog(dialogCloseSuccess);
                        } else {
                            $("#modal-content").html("There was an error in registration!");
                            showDialog(dialogCloseFailure);
                        }
                        hideLoadingGif();
                    }

                    var error = function (response) {
                        alert("There was an error in your registration!");
                    }
                    showLoadingGif();
                    doPost(route, dat, success, error);
                } else {
                    alert("Passwords don't match!");
                }
            });

            $("#login-form").submit(function (e) {
                e.preventDefault();
                if (checkPasswords()) {
                    var route = "LoginServlet";
                    var dat = $(this).serializeArray();
                    var success = function (response) {
                        var type = response.status.split("-")[1];
                        if (type.toLowerCase() == "true") {
                            $("#modal-content").html("Success you have logged in!");
                            showDialog(dialogCloseSuccess);
                        } else {
                            console.log(response.status);
                            var msg = "";
                            if (type == "PW") {
                                msg = "Password not correct!";
                            } else if (type == "USER") {
                                msg = "User does not exist!";
                            } else {
                                msg = "There was an error in login. Please contact us!";
                            }
                            $("#modal-content").html(msg);
                            showDialog(dialogCloseFailure);
                        }
                        hideLoadingGif();
                    }

                    var error = function (response) {
                        alert("There was an error in your registration!");
                    }
                    showLoadingGif();
                    doPost(route, dat, success, error);
                } else {
                    alert("Passwords don't match!");
                }
            });

            $("#add-book-form").submit(function (e) {
                e.preventDefault();
                var data = $(this).serializeArray();
                var confirm = window.confirm("Confirm new book submission!");
                if (confirm) {
                    var file = document.querySelector('input[type=file]').files[0];
                    var reader = new FileReader();
                    reader.addEventListener("load", function () {
                        var route = "AccountServlet";
                        data.push({"name": "tag", "value": "ADD_BOOK"});
                        data.push({"name": "img", "value": reader.result});
                        var success = function (response) {
                            hideLoadingGif();
                            $("#modal-content").html("Book has been added!");
                            showDialog(dialogCloseSuccess);
                        }
                        var error = function (response) {
                            hideLoadingGif();
                        }
                        showLoadingGif();
                        doPost(route, data, success, error);
                    }, false);

                    if (file) {
                        reader.readAsDataURL(file);
                    }

                } else {

                }
            });

            $("#update-info-form").submit(function (e) {
                e.preventDefault();
                var data = $(this).serializeArray();
                var confirm = window.confirm("Confirm information update!");
                if (confirm) {
                    var route = "AccountServlet";
                    data.push({"name": "tag", "value": "UPDATE_INFO"});
                    var success = function (response) {
                        hideLoadingGif();
                        $("#modal-content").html("Information updated!");
                        showDialog(dialogCloseSuccess);
                    }
                    var error = function (response) {
                        hideLoadingGif();
                    }
                    showLoadingGif();
                    doPost(route, data, success, error);
                } else {

                }
            });

            $("#update-password-form").submit(function (e) {
                e.preventDefault();
                var data = $(this).serializeArray();
                var confirm = window.confirm("Confirm password update!");
                if (confirm) {
                    if (data[1].value != data[2].value) {
                        alert("New password's don't match");
                    } else {
                        var route = "AccountServlet";
                        data.push({"name": "tag", "value": "UPDATE_PASSWORD"});
                        var success = function (response) {
                            if (response.status == "NOEXIST") {
                                alert("Existing password not correct!");
                            } else {
                                hideLoadingGif();
                                $("#modal-content").html("Password updated!");
                                showDialog(dialogCloseSuccess);
                            }
                        }
                        var error = function (response) {
                            hideLoadingGif();
                        }
                        showLoadingGif();
                        doPost(route, data, success, error);
                    }
                } else {

                }
            });
        </script>

        <footer class="container-fluid text-center">
            <p>Byte Bookstore 2017</p>  </br>
            Johns Hopkins University </br>
            EN,605.782.81.SU17
        </footer>

    </body>
</html>
