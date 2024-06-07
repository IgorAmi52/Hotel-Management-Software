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
import com.models.Staff;
import com.models.User;
import com.models.enums.Role;

public class UserService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;
	
	public static Staff[] getStaff() throws IOException {

		reader = new FileReader("data/users.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();

	    List<Staff> staffArrList = new ArrayList<Staff>();

	    for(String username: jsonObject.keySet()) {
	       	String role = jsonObject.getAsJsonObject(username).get("role").getAsString();
	    	if(!role.equals(Role.GUEST.toString()) && !role.equals(Role.ADMIN.toString())) {
	    		JsonObject staffObject = jsonObject.getAsJsonObject(username);
	    		Staff currentStaff = gson.fromJson(staffObject, Staff.class);
	    		staffArrList.add(currentStaff);
	    	}
	    }
	    Staff[] ret = new Staff[staffArrList.size()];
	    int i = 0;
	    
	    for(Staff staff: staffArrList) {
	    	ret[i++] = staff;
	    }
	    return ret;
	}

	public static void deleteUser(User user) throws IOException {
	
		reader = new FileReader("data/users.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		String username = user.getUserName();
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
	public static Staff getStaff(String username) throws IOException{
		reader = new FileReader("data/users.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		Staff staff = gson.fromJson(jsonObject.getAsJsonObject(username), Staff.class);
		
		return staff;
	}
}
