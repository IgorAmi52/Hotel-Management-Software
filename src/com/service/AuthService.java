package com.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComponent;

import com.exceptions.*;
import com.frame.LoginPanel;
import com.frame.AdminMainPanel;
import com.frame.GuestMainPanel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.Guest;
import com.models.Person;
import com.models.Staff;
import com.models.enums.Role;

public class AuthService {
	
	private static Gson gson = new Gson();
	
	public static Person login(String username, String password) throws IOException, BadLoginException { 
	
		FileReader reader = new FileReader(Holder.getProjectPath()+"/src/com/database/users.json"); 
		
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
	    reader.close();
	    
	    if(jsonObject.has(username)) {	
	    	JsonObject userObject = jsonObject.get(username).getAsJsonObject();
	    	String pass = userObject.get("password").getAsString();
	    	if(pass.equals(password)) {
	    		 return getPersonType(userObject);	    	}
	    }
		
		throw new BadLoginException("Username or password are incorect! Please try again."); 
	}
	
	private static Person getPersonType(JsonObject userObject) {
		String role = userObject.get("role").getAsString();
		if(role == Role.GUEST.toString()) {
			
			return gson.fromJson(userObject, Guest.class);
		}
		return gson.fromJson(userObject, Staff.class);
	}
	
	public static JComponent userRedirect(Person user) { //change name
		if(user == null) {
			return new LoginPanel();
		}
		if(user.getRole()==Role.ADMIN) {
			return new AdminMainPanel();
		}
		else if(user.getRole()==Role.AGENT) {
			return new AdminMainPanel();
		}
		else if(user.getRole()==Role.CLEANER) {
			return new AdminMainPanel();
		}
		return new GuestMainPanel();
	}
	public static void registerUser(Person user) throws IOException {
		FileReader reader = new FileReader(Holder.getProjectPath()+"/src/com/database/users.json");
		
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
	    reader.close();
	    
	    jsonObject.add(user.getUserName(), user.getJson());
	    FileWriter writer = new FileWriter(Holder.getProjectPath()+"/src/com/database/users.json");
	    writer.write(new Gson().toJson(jsonObject));
	    writer.close();
	}
	
}
