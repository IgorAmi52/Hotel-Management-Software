package com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.Guest;
import com.models.Staff;
import com.models.User;
import com.models.enums.DataTypes;
import com.models.enums.Role;

public class UserService {
	
	private static DataAccessImpl dataAccessService = new DataAccessImpl();
	private static Gson gson = new Gson();

	public static Staff[] getStaff() throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.USERS);
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
	
		JsonObject jsonObject = dataAccessService.getData(DataTypes.USERS);
		String username = user.getUserName();
	    jsonObject.remove(username);
	    
	    dataAccessService.setData(DataTypes.USERS, jsonObject);
	}
	
	public static Guest getGuest(String username) throws IOException{
		JsonObject jsonObject = dataAccessService.getData(DataTypes.USERS);
		Guest guest = gson.fromJson(jsonObject.getAsJsonObject(username), Guest.class);
		
		return guest;
	}
	public static Staff getStaff(String username) throws IOException{
		JsonObject jsonObject = dataAccessService.getData(DataTypes.USERS);
		Staff staff = gson.fromJson(jsonObject.getAsJsonObject(username), Staff.class);
		
		return staff;
	}
}
