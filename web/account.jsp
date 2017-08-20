<%-- 
    Document   : account
    Created on : Aug 13, 2017, 1:37:13 PM
    Author     : wjlax
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 

    boolean value = true;


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
                        <li><a href="/ByteBookstore/Cart"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        
        <div class="container" id="account-creation-container">
            <div class="col-md-6">
                <form id="login-form">
                    <h2>Login</h2><br/>
                    <div class="form-group">
                        <label><strong>Email</strong></label>
                        <input class="form-control" type="email" placeholder="Enter Email" name="login-email" required>
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

        <div id="modal" title="Registration Status">
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

            var doPost = function (r, d, s, f) {
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: r,
                    data: d,
                    success: s,
                    error: f
                });
            };
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
                        if (response.status == "success") {
                            $("#modal-content").html("Success you have logged in!");
                            showDialog(dialogCloseSuccess);
                        } else {
                            var type = response.status.split("-")[1];
                            var msg = "";
                            if(type == "PW"){
                                msg = "Password not correct!";
                            } else if(type == "USER"){
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
        </script>

        <footer class="container-fluid text-center">
            <p>Byte Bookstore 2017</p>  </br>
            Johns Hopkins University </br>
            EN,605.782.81.SU17
        </footer>

    </body>
</html>
