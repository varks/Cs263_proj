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
<html lang="en">
  <head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Justified Nav Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="justified-nav.css" rel="stylesheet">

   </head>

  <body>





    <div class="container">

      <div class="masthead">
      
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
%>

<h3 class="text-muted"> Hello, ${fn:escapeXml(user.nickname)}! </h3>
<%
}
    else {
    	response.sendRedirect("/coverindex.jsp");
    	
    }
%>
      
         
        <div role="navigation">
          <ul class="nav nav-justified">
            <li class="active"><a href="#"></a></li>
            <li><a href="#"></a></li>
            <li><a href="#"></a></li>
            <li><a href="/stopsms">Stop Notifications</a></li>
            <li><a href="/About.html">About</a></li>
            <li><a href="/contact.html">Contact</a></li>
            <li><a href="<%= userService.createLogoutURL("/coverindex.jsp") %>">Sign out</a></li>
          </ul>
        </div>
      
     
     </div>
      <!-- Jumbotron -->
      <div class="jumbotron">
      
        <h1>CEN BOT</h1>
        <p class="lead">CEN Bot helps you keep track of INR on the go and notifies you if the exchange rate falls below your specified threshold value. You can update the threshold value by clicking the below button. </p>
        <p><a class="btn btn-lg btn-success" href="/reg.jsp" role="button">Update Trigger (Threshold Value)</a></p>
    
      </div>

      <!-- Example row of columns -->
      <div class="row">
        <div class="col-lg-4">
        
<a class="twitter-timeline" href="https://twitter.com/YahooFinance" data-widget-id="544021336838782977">Tweets by @YahooFinance</a>
<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>

        
       </div>
        <div class="col-lg-4">

<!-- FXEXCHANGERATE.COM EXCHANGE RATE CONVERTER START --><div style="width:196px;border:1px solid #2D6AB4;background-color:#F0F0F0;"><div style="text-align:left;background-color:#2D6AB4;border-bottom:0px;height:18px; font-size:12px;font-weight:bold;padding-top:2px; padding-left:5px"><span  style="background-image:url(http://ww.fxexchangerate.com/flag.png); background-position: 0 -2064px; width:100%; height:15px; background-repeat:no-repeat;padding-left:2px;"><a href="http://usd.fxexchangerate.com/" target="_blank" style="color:#FFFFFF; text-decoration:none;padding-left:22px;">United States Dollar</a></span></div><script type="text/javascript">var fm="USD";var ft="INR,";var hb="2D6AB4";var hc="FFFFFF";var bb = "F0F0F0";var bo = "2D6AB4";var tz="timezone";var wh="196x80";var lg="en";</script><script type="text/javascript" src="http://www.fxexchangerate.com/converter.php"></script></div><!-- FXEXCHANGERATE.COM  EXCHANGE RATE CONVERTER END -->         
         
       </div>
        <div class="col-lg-4">

<a class="twitter-timeline" href="https://twitter.com/iamrupee" data-widget-id="544029543393009664">Tweets by @iamrupee</a>
<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
       </div>
      </div>

      <!-- Site footer -->
      <footer class="footer">
        <p>&copy; Company 2014</p>
      </footer>

    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
