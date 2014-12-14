package test1.test1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
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
//import com.google.appengine.labs.repackaged.org.json.JSONArray;
//import com.google.appengine.labs.repackaged.org.json.JSONObject;


@Path("/jerseyws")
public class TestJersey {
	@GET
    @Path("/rates")
    public String getRates() {
		System.out.println("In get method");
		String jsonResp = "";
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		jsonResp = (String)memcache.get("json_resp");
		if (jsonResp == null)
			return "Please register for the app to use this Memcache service";
		else
        return jsonResp;
    }
	
	@GET
    @Path("/users")
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
	
	
	
	
	@POST
	@Path("/post/users")
	public String testPost() {
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
}
