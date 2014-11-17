package com.google.pit;

import java.util.*;
import java.util.Map;
import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/* Twilio SMS Api */
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;


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

public class SendSms extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public static final String ACCOUNT_SID = "AC5dd34f2b8a80e573c9c636ea4900b48a";
    public static final String AUTH_TOKEN = "0a5940ce7a8585d733170a4aab1620a6";
 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Create a Twilio REST client
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account account = client.getAccount();
 
        //Use the API to send a text message
        SmsFactory smsFactory = account.getSmsFactory();
        Map<String, String> smsParams = new HashMap<String, String>();
        smsParams.put("To", "+18056377338 "); 
        smsParams.put("From", "(805) 883-6351"); // Replace with a Twilio phone number in your account
        smsParams.put("Body", "Test Sample 1");
        try {
        Sms sms = smsFactory.create(smsParams);
        }
        catch (Exception e) {
        	
        	
        }
    } 
}

