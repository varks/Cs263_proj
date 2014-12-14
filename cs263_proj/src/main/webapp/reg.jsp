<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>    


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title> Preference Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
 <!--    <link rel="stylesheet" type="text/css" href="WEB-INF/css/font-awesome.min.css" />
-->
    <script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
</head>
<body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);

   
} else {
	
	
%>
<!--  <p>Hello! -->
    <!--  <a href=" %>"> Please Sign in</a>
    to use your Currency Exchange rate Notification Bot. </p> -->
<% 
	String url = "/coverindex.jsp";
    response.sendRedirect(url);
    }
	
%>

<%
	if (user != null) {
%>
<div class="container">

<div class="page-header">
    <h2> <p> Hello, ${fn:escapeXml(user.nickname)}!Please update your preferences</p> </h2>
</div>

<!-- Registration Form - START -->
 <div class="container" id="container1">
        <div class="row centered-form">
            <div class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
                <div class="panel panel-default">
                  <div class="panel-body">
                        <form role="form" action = "/settings">
                            <div class="form-group">
                                <input type="text" name="first_name" id="first_name" class="form-control input-sm" placeholder="First Name">
                            </div>

                            <div class="form-group">
                                <input type="text" name="last_name" id="last_name" class="form-control input-sm" placeholder="Last Name">
                            </div>

                            <div class="form-group">
                                <input type="text" name="Phone-Number" id="Phone-Number" class="form-control input-sm" placeholder="Phone Number">
                            </div>
                            <div class="form-group">
  							<label for="select currency">Select Currency</label>
								  <select class="form-control" id="currency" name = "currency" >
								    <option>INR</option>
								   </select>
								</div>
			               	<div class="form-group">
			               	<label for="select Threshold">Select Threshold Value</label>
                                <input type="text" name="tvalue" id="tvalue" class="form-control input-sm" placeholder="Eg 61 for INR">
                            </div>
                            <div class="form-group">
  							<label for="select Frequency ( in days)">Notification Frequency</label>
								  <select class="form-control" id="frequency" name = "frequecy" >
								    <option>1</option>
								    <option>2</option>
								   </select>
							</div>
                            
                                          
                            
                           <div class="row">
                                <div class="col-xs-6 col-sm-6 col-md-6">
                                    <div class="form-group">
                                        <input type="password" name="password" id="password" class="form-control input-sm" placeholder="Password">
                                    </div>
                                </div>
                                <div class="col-xs-6 col-sm-6 col-md-6">
                                    <div class="form-group">
                                        <input type="password" name="password_confirmation" id="password_confirmation" class="form-control input-sm" placeholder="Confirm Password">
                                    </div>
                                </div>
                            </div>

                            <input type="submit" value="Register" class="btn btn-info btn-block">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<style>
#container1 {
    background-color: lighten(@gray-base, 93.5%);
}

.centered-form {
    margin-top: 120px;
    margin-bottom: 120px;
}

.centered-form .panel {
    background: rgba(255, 255, 255, 0.8);
    box-shadow: rgba(0, 0, 0, 0.3) 20px 20px 20px;
}
</style>
<!-- Registration Form - END -->

</div>

<%
	}
%>
</body>
</html>