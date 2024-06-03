package com.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComponent;

import com.exceptions.*;
import com.frame.LoginPanel;
import com.frame.AdminMainPanel;
import com.frame.AgentMainPanel;
import com.frame.CleanerMainPanel;
import com.frame.GuestMainPanel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.Guest;
import com.models.User;
import com.models.Staff;
import com.models.enums.Role;

public class AuthService {
	
	private static Gson gson = new Gson();
	
	public static User login(String username, String password) throws IOException, BadLoginException { 
	
		FileReader reader = new FileReader("data/users.json"); 
		
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
	    reader.close();
	    
	    if(jsonObject.has(username)) {	
	    	JsonObject userObject = jsonObject.get(username).getAsJsonObject();
	    	String pass = userObject.get("password").getAsString();
	    	if(pass.equals(password)) {
	    		 return getUserType(userObject);	    	
	    	}
	    }
		throw new BadLoginException("Username or password are incorect! Please try again."); 
	}
	
	private static User getUserType(JsonObject userObject) {
		String role = userObject.get("role").getAsString();

		if(role.equals(Role.GUEST.toString())) {
			return gson.fromJson(userObject, Guest.class);
		}
		return gson.fromJson(userObject, Staff.class);
	}
	
	public static JComponent userRedirect(User user) { 

		if(user == null) {
			return new LoginPanel();
		}
		if(user.getRole()==Role.ADMIN) {
			return new AdminMainPanel();
		}
		else if(user.getRole()==Role.AGENT) {
			return new AgentMainPanel();
		}
		else if(user.getRole()==Role.CLEANER) {
			return new CleanerMainPanel();
		}
		return new GuestMainPanel();
	}
	public static void registerUser(User user) throws IOException {
		FileReader reader = new FileReader("data/users.json");
		
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
	    reader.close();
	    
	    jsonObject.add(user.getUserName(), user.getJson());
	    FileWriter writer = new FileWriter("data/users.json");
	    writer.write(new Gson().toJson(jsonObject));
	    writer.close();
	}
	
}
