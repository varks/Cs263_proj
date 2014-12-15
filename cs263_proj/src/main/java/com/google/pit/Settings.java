package com.google.pit;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Settings extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     /* Reads the form parameters, stores in datastore and creates a task queue to process them */
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    
	    /* Redirect to cover page to prevent unauthorized access */
	    if (user == null)
	    	resp.sendRedirect("/coverindex.jsp");

	    String currency = req.getParameter("currency");
	    String phoneNumber = req.getParameter("Phone-Number");
	
	    String tvalue = req.getParameter("tvalue");
	    String frequency = req.getParameter("frequency");
	    String first_name = req.getParameter("first_name");
	    String last_name = req.getParameter("last_name");	
	    Date date = new Date();
	    
	   /* Debug Statements */
	    System.out.println("In Worker - " + "first_name " + first_name + " last_name " + last_name 
	    + " phonenumber " + phoneNumber +  " tvalue " + tvalue + " user" + user.toString());
	    
	    /* convert user to string */
	    Key currencyKey = KeyFactory.createKey("CurrencyTracker", user.toString());
	    Entity  c1 = new Entity(currencyKey);
	    c1.setProperty("user", user);
	    c1.setProperty("date", date);
	    if(!first_name.equals(""))
	    	c1.setProperty("first_name", first_name);
	    if(!last_name.equals(""))
	    	c1.setProperty("last_name", last_name);
	    if(!currency.equals(""))
	    	c1.setProperty("currency", currency);
	    if(!phoneNumber.equals(""))
	    	c1.setProperty("phoneNumber", phoneNumber);
	    if(!tvalue.equals(""))
	    	c1.setProperty("tvalue", tvalue);
	    

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(c1);
	    
	    /* Start a worker Thread and return back a home page */
	    
	    Queue queue = QueueFactory.getDefaultQueue();
	    queue.add(TaskOptions.Builder
        		.withUrl("/worker"));    
      
      
	    
	   resp.sendRedirect("/homePage.jsp");
	  }

}
