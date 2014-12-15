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

public class SendSms extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	
	public static final String ACCOUNT_SID = "AC5dd34f2b8a80e573c9c636ea4900b48a";
    public static final String AUTH_TOKEN = "0a5940ce7a8585d733170a4aab1620a6";
    public static final String Twilio_Num = "(805) 883-6351";
    
    /* Debug Servlet to debug SMS service from Twilio */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Create a Twilio REST client
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account account = client.getAccount();
        
        /* Read SMS parameters */
        String to_num = request.getParameter("to_cell");
        String tvalue = request.getParameter("tvalue");
        String curr_value = request.getParameter("cvalue");
        String message = "CNBOT: The currency rate has come down below the set threshold rate to" + curr_value;
                          
        		
        /* Use the API to send a text message */
        SmsFactory smsFactory = account.getSmsFactory();
        Map<String, String> smsParams = new HashMap<String, String>();
        /* Read the number from DataStore */
        smsParams.put("To", to_num); 
        smsParams.put("From", Twilio_Num); // Replace with a Twilio phone number in your account
        smsParams.put("Body", message);
        try {
        Sms sms = smsFactory.create(smsParams);
        }
        catch (Exception e) {
        	e.printStackTrace();
        	
        }
    } 
}

