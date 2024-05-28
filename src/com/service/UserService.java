package com.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.Guest;
import com.models.User;
import com.models.enums.Role;

public class UserService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;
	
	public static String[][] getStaff() throws IOException {

		reader = new FileReader("data/users.json");
	
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
	
		reader.close();

	    //get arr count
	    int arrLength = 0;
	    for(String username: jsonObject.keySet()) {
	     	String role = jsonObject.getAsJsonObject(username).get("role").getAsString();
	      	if(!role.equals(Role.GUEST.toString()) && !role.equals(Role.ADMIN.toString())) {
	      		arrLength++;
	      	}
	    }
	    String[][] staffArr = new String[arrLength][];
	    int i = 0;
	    for(String username: jsonObject.keySet()) {
	       	String role = jsonObject.getAsJsonObject(username).get("role").getAsString();
	    	if(!role.equals(Role.GUEST.toString()) && !role.equals(Role.ADMIN.toString())) {
	 
	    		JsonObject staffObject = jsonObject.getAsJsonObject(username);
	    		
	            String[] staff = {
	            		staffObject.get("username").getAsString(),
	            		staffObject.get("name").getAsString(),
	            		staffObject.get("lastname").getAsString(),
	            		Role.valueOf(role).getRole(),
	            		staffObject.get("sex").getAsString(),
	            		staffObject.get("dateOfBirth").getAsString(),
	            		staffObject.get("phoneNumber").getAsString(),
	            		staffObject.get("address").getAsString()
	            };
	            staffArr[i]= staff;
	            i++;
	    	}
	    }
	    return staffArr;
	}

	public static void deleteUser(String username) throws IOException {
	
		reader = new FileReader("data/users.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
	
	    jsonObject.remove(username);
	    
	    writer = new FileWriter("data/users.json");
	    writer.write(new Gson().toJson(jsonObject));
	    writer.close();
	}
	
	public static Guest getGuest(String username) throws IOException{
		reader = new FileReader("data/users.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		Guest guest = gson.fromJson(jsonObject.getAsJsonObject(username), Guest.class);
		
		return guest;
	}
}
