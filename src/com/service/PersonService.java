package com.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.enums.Role;

public class PersonService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;
	
	public static String[][] getStaff() throws IOException {

		reader = new FileReader(Holder.getProjectPath()+"/src/com/database/users.json");
	
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
	
		reader.close();

	    //get arr count
	    int arrLength = 0;
	    for(String username: jsonObject.keySet()) {
	     	String role = jsonObject.getAsJsonObject(username).get("role").getAsString();
	      	if(!role.equals(Role.GUEST.getRole()) && !role.equals(Role.ADMIN.getRole())) {
	      		arrLength++;
	      	}
	    }
	    String[][] staffArr = new String[arrLength][];
	    int i = 0;
	    for(String username: jsonObject.keySet()) {
	       	String role = jsonObject.getAsJsonObject(username).get("role").getAsString();
	    	if(!role.equals(Role.GUEST.getRole()) && !role.equals(Role.ADMIN.getRole())) {
	 
	    		JsonObject staffObject = jsonObject.getAsJsonObject(username);
	            String[] staff = {
	            		staffObject.get("username").getAsString(),
	            		staffObject.get("name").getAsString(),
	            		staffObject.get("lastname").getAsString(),
	            		staffObject.get("role").getAsString(),
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
	
		reader = new FileReader(Holder.getProjectPath()+"/src/com/database/users.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
	
	    jsonObject.remove(username);
	    
	    writer = new FileWriter(Holder.getProjectPath()+"/src/com/database/users.json");
	    writer.write(new Gson().toJson(jsonObject));
	    writer.close();
	}
}
