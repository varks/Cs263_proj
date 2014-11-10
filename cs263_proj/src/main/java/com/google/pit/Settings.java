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

	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();

	    String currency = req.getParameter("currency");
	    String phoneNumber = req.getParameter("Phone-Number");
	
	    String tvalue = req.getParameter("tvalue");
	    String frequency = req.getParameter("frequency");
	    Date date = new Date();
	    Key currencyKey = KeyFactory.createKey("CurrencyTracker", "Test");
	    Entity  c1 = new Entity(currencyKey);
	    c1.setProperty("user", user);
	    c1.setProperty("date", date);
	    c1.setProperty("currency", currency);
	    c1.setProperty("phoneNumber", phoneNumber);
	    c1.setProperty("tvalue", tvalue);
	    c1.setProperty("frequency", frequency);

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    datastore.put(c1);
	    
	    /* Start a worker Thread and return back a home page */
	    /*
	    Queue queue = QueueFactory.getDefaultQueue();
	    queue.add(TaskOptions.Builder
        		.withUrl("/worker"));    
      
      */
	   resp.sendRedirect("/worker");
	  }

}
