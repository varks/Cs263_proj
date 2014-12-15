package test1.test1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Path("/jerseyws")
public class TestJersey {
	
	/* Exposes 5 Rest APIs 
	 * 
	 * GET :
	 * 1. /get/rates_memcache
	 * 2. /get/rates
	 * 3. /get/users
	 * 
	 * POST:
	 * 
	 * 1. /post/rates
	 * 2. /post/users
	 */
	
	
	@GET
    @Path("/get/rates_memcache")
    public String getRates_mem() {
		System.out.println("In get method");
		String jsonResp = "";
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		jsonResp = (String)memcache.get("json_resp");
		if (jsonResp == null)
			return "Please sign for the app to use this Memcache service, Memcache might have timed out";
		else
        return jsonResp;
    }
	
	@GET
    @Path("/get/users")
    public String getUsers() {
		System.out.println("In get method for Users");
		JSONObject obj = new JSONObject();
		JSONArray lists = new JSONArray();
			 
		
		 try {
		 Query q = new Query("CurrencyTracker");
	     PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(q);
	     List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
	        
	     System.out.println("rows " + results.size());
	        
	        if (results.size() == 0)
	        	return "No users Yet";
	        for (int i = 0; i < results.size(); i++) {
	            Entity entity = results.get(i);
	            
	            String user = entity.getProperty("user").toString();
	            lists.add(user);
	            
	        }
		
	        obj.put("users", lists);
	        
	        
		 }
		 catch(Exception e) {
			 System.out.println("Stacktrace is ");
			 e.printStackTrace();
			 
		 }
		
		return obj.toString();
    }

	@GET
    @Path("/get/rates")
    public String getRates() {
		System.out.println("In get method for rates");
		String urlString = "http://openexchangerates.org/api/latest.json?app_id=6be8c36ccb21453b8044563cb7abf2c4";
		String ret = "";
    	/* fetch the currency json api */
		try {
			URL url = new URL(urlString);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			StringBuffer jsonResp = new StringBuffer();
    	
			while((line = reader.readLine()) != null) {
    			jsonResp.append(line);
			}
			ret = jsonResp.toString();
    	    System.out.println("Print Json" + jsonResp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	
	@POST
	@Path("/post/users")
	public String getPostUsers() {
		System.out.println("\n In post method");
		JSONObject obj = new JSONObject();
		JSONArray lists = new JSONArray();
			 
		
		 try {
		 Query q = new Query("CurrencyTracker");
	     PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(q);
	     List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());
	        
	     System.out.println("rows " + results.size());
	        
	        if (results.size() == 0)
	        	return "No users Yet";
	        for (int i = 0; i < results.size(); i++) {
	            Entity entity = results.get(i);
	            
	            String user = entity.getProperty("user").toString();
	            lists.add(user);
	            
	        }
		
	        obj.put("users", lists);
	        
	        
		 }
		 catch(Exception e) {
			 System.out.println("Stacktrace is ");
			 e.printStackTrace();
			 
		 }
		
		return obj.toString();
		
	}
	
	
	
	@POST
    @Path("/post/rates")
    public String getPostRates() {
		System.out.println("In get method for rates");
		String urlString = "http://openexchangerates.org/api/latest.json?app_id=6be8c36ccb21453b8044563cb7abf2c4";
		String ret = "";
    	/* fetch the currency json api */
		try {
			URL url = new URL(urlString);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			StringBuffer jsonResp = new StringBuffer();
    	
			while((line = reader.readLine()) != null) {
    			jsonResp.append(line);
			}
			ret = jsonResp.toString();
    	    System.out.println("Print Json" + jsonResp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
