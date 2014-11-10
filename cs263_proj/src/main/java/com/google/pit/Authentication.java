package com.google.pit;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Authentication extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();

        String thisURL = req.getRequestURI();

        resp.setContentType("text/html");
        resp.sendRedirect("/home.html");
        
      
        System.out.println("outside");
        
        if (req.getUserPrincipal() != null) {
        	System.out.println("inside");
        	resp.getWriter().println("<p>hello please redirect to home.html</p>");
        	//resp.sendRedirect("/home.html");
        	
        	/* Add some css and redirect to the home page of the app 
        
            resp.getWriter().println("<p>Hello, " +
                                     req.getUserPrincipal().getName() +
                                     "!  You can <a href=\"" +
                                     userService.createLogoutURL("/login.html") +
                                     "\">sign out</a>.</p>");
        */    
        } 
        
       /* 
        else {
            resp.getWriter().println("<p>Please <a href=\"" +
                                     userService.createLoginURL("/login.html") +
                                     "\">sign in</a>.</p>");
        }
        */
    }
}