package com.google.pit;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class StopSMS extends HttpServlet {

	/* Responds to Stop Notfications signal from home page by taking appropriate actions */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    
	    if (user == null)
	    	response.sendRedirect("/coverindex.jsp");

	    Key userKey = KeyFactory.createKey("CurrencyTracker", user.toString());
		 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity currentUser;
		
		try {
		    currentUser = datastore.get(userKey);
		    currentUser.setProperty("tvalue", "0");
		    
		    System.out.println("Properties of User " + currentUser.getProperty("user").toString() + "  " + 
    	            currentUser.getKey().toString() + "currency " +
    	            currentUser.getProperty("currency").toString() + " Date "
    	            		+ currentUser.getProperty("date").toString()) ;
		 
		    datastore.put(currentUser);
		} catch (EntityNotFoundException e) {
		    // User doesn't exist!
		}

		response.sendRedirect("/homePage.jsp");
                          
	
	}

}