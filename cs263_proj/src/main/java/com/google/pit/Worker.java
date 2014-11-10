package com.google.pit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
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
import com.google.appengine.labs.repackaged.org.json.JSONObject;


import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;


public class Worker extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	final String urlString = "http://openexchangerates.org/api/latest.json?app_id=6be8c36ccb21453b8044563cb7abf2c4";
    	/* fetch the currency json api and fiddle with that */
    	URL url = new URL(urlString);
    	BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    	String line;
    	StringBuffer jsonResp = new StringBuffer();
    	
    	while((line = reader.readLine()) != null) {
    			jsonResp.append(line);
    	}
    	System.out.println("Print Json" + jsonResp);
    	response.getWriter().println("<p>" + jsonResp + "</p>");
    	
    	try {
    		
    		JSONObject jsonObj = new JSONObject(jsonResp);
    		String currency_rates = jsonObj.getString("rates");
        
    		response.getWriter().println("<p> rates is" + currency_rates + "</p>");
        
    		JSONObject jsonObj2 = new JSONObject(currency_rates);
    		String INR = jsonObj2.getString("INR");
    		
    		float value = Float.parseFloat(INR);
    		
    		response.getWriter().println("<p> INR is" + INR + "</p>");
    		
    	} catch(Exception e) {
        
    	
    		
    	}
    	
    }
}