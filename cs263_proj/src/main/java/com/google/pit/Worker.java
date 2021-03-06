package com.google.pit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.JSONTokener;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Sms;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;

/* Uses Urlfetch to fetch Json from a currency exchange site and triggers SMS notifications
 * if conditions are met. Twilio Api is used to send SMS. This also runs as a cron job.
 */
public class Worker extends HttpServlet {
	
	public static final String ACCOUNT_SID = "AC5dd34f2b8a80e573c9c636ea4900b48a";
    public static final String AUTH_TOKEN = "0a5940ce7a8585d733170a4aab1620a6";
    public static final String Twilio_Num = "+18664514765";
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
    	
    	/* Store JSON Response in MemCache */
    	    	
        MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
        memcache.put("json_resp", jsonResp.toString());
        
        	
    	
    	try {
    		
    		response.getWriter().println("<p> In try blcok </p>");
    		JSONTokener tokens = new JSONTokener(jsonResp.toString());
            JSONObject jsonObj = new JSONObject(tokens);
    		String currency_rates = jsonObj.getString("rates");
    		
        
    		JSONObject jsonObj2 = new JSONObject(currency_rates);
    		
    		/* Get Value based on datastore */
    		String INR = jsonObj2.getString("INR");
    		float cur_value = Float.parseFloat(INR);
    		response.getWriter().println("<p> INR is" + INR + "</p>");
    		
    		/* Iterate through all users , check and sendSMS if appropriate */
    	    Query q = new Query("CurrencyTracker");
    	    PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(q);
    	    List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
    	    response.setContentType("text/plain");
    	    /* Debug */
    	    System.out.println("rows " + results.size());
    	        
    	    for (int i = 0; i < results.size(); i++) {
    	    	Entity entity = results.get(i);
    	        response.getWriter().println("<p> " + 
    	        entity.getKey().toString() + "currency " +
    	        entity.getProperty("currency").toString() + " Date "
    	          		+ entity.getProperty("date").toString() + "</p> <br/>");
    	            
    	        String tvalue = entity.getProperty("tvalue").toString();
    	        String cell_num = entity.getProperty("phoneNumber").toString();
    	        float set_value = Float.parseFloat(tvalue);
    	            
    	        /* Debug */
    	        System.out.println("tvalue= " + tvalue);
    	        System.out.println("cell_num= " + cell_num);
    	        System.out.println("curr_value" + INR);
    	            
    	        if(cur_value <= set_value) {
    	        	sendSMS (cell_num, INR ,tvalue);	            	
    	        }
    	            
    	    }
    	        
    	    		
    	} catch(Exception e) {
        
    		response.getWriter().println("<p> In Exception </p>");
    		
    	}
    	
    }
	
	
	/* @param : Takes Phone number and Threshold. @return: Nothing */
	public void sendSMS(String To, String cvalue, String threshold) {
		
		 //Create a Twilio REST client
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account account = client.getAccount();
        
        /* Read SMS parameters */
        String to_num = To;
        String tvalue = threshold;
        String curr_value = cvalue;
        String message = "CNBOT: The currency rate has come down below the set threshold rate to " + curr_value;
                          
        System.out.println("Sending SMS to " + To + "tvalue= " + tvalue + " curr= " + cvalue);		
        //Use the API to send a text message
        SmsFactory smsFactory = account.getSmsFactory();
        Map<String, String> smsParams = new HashMap<String, String>();
        /* Read the number from DataStore */
        smsParams.put("To", to_num); 
        smsParams.put("From", Twilio_Num); // Replace with a Twilio phone number in your account
        smsParams.put("Body", message);
        try {
        	Sms sms = smsFactory.create(smsParams);
        	System.out.println("SMS Sent Succesfully !!!");
        }
        catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("SMS failed");
        	
        }
    } 
		
	/* @return: nothing @param: request, response. - GET requests for cron job */	
	protected void doGET(HttpServletRequest request, HttpServletResponse response)
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
    	
    	/* Store JSON Response in MemCache */
    	    	
        MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
        memcache.put("json_resp", jsonResp.toString());
        
        	
    	
    	try {
    		
    		response.getWriter().println("<p> In try blcok </p>");
    		JSONTokener tokens = new JSONTokener(jsonResp.toString());
            JSONObject jsonObj = new JSONObject(tokens);
    		String currency_rates = jsonObj.getString("rates");
    		
        
    		JSONObject jsonObj2 = new JSONObject(currency_rates);
    		
    		/* Get Value based on datastore */
    		String INR = jsonObj2.getString("INR");
    		float cur_value = Float.parseFloat(INR);
    		response.getWriter().println("<p> INR is" + INR + "</p>");
    		
    		/* Iterate through all users , check and sendSMS if appropriate */
    	    Query q = new Query("CurrencyTracker");
    	    PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(q);
    	    List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
    	    response.setContentType("text/plain");
    	    /* Debug */
    	    System.out.println("rows " + results.size());
    	        
    	    for (int i = 0; i < results.size(); i++) {
    	    	Entity entity = results.get(i);
    	        response.getWriter().println("<p> " + 
    	        entity.getKey().toString() + "currency " +
    	        entity.getProperty("currency").toString() + " Date "
    	          		+ entity.getProperty("date").toString() + "</p> <br/>");
    	            
    	        String tvalue = entity.getProperty("tvalue").toString();
    	        String cell_num = entity.getProperty("phoneNumber").toString();
    	        float set_value = Float.parseFloat(tvalue);
    	            
    	        /* Debug */
    	        System.out.println("tvalue= " + tvalue);
    	        System.out.println("cell_num= " + cell_num);
    	        System.out.println("curr_value" + INR);
    	            
    	        if(cur_value <= set_value) {
    	        	sendSMS (cell_num, INR ,tvalue);	            	
    	        }
    	            
    	    }
    	        
    	    		
    	} catch(Exception e) {
        
    		response.getWriter().println("<p> In Exception </p>");
    		
    	}
    	
    }		
	
	
}