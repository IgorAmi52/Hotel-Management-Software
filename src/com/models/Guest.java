package com.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.enums.Role;

public class Guest implements Person{

	protected String name;
	protected String lastname;
	protected String sex;
	protected String dateOfBirth;
	protected String phoneNumber;
	protected String address;
	protected String username;
	protected String password;
	protected Role role;
	public Guest(String name, String lastname, String sex, String dateOfBirth, String phoneNumber, String address, String username, String password) {
		this.name = name;
		this.lastname = lastname;
		this.sex = sex;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.username = username;
		this.password = password;
		this.role = Role.GUEST;

	}
	@Override
	public Role getRole() {
	
		return role;
	}
	@Override
	public JsonObject getJson() {
			
		Gson gson = new Gson();
        String jsonString = gson.toJson(Guest.this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

	    return jsonObject;
	}
	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return this.username;
	}
	@Override
	public String getFullName() {
		// TODO Auto-generated method stub
		return (name+" " + lastname);
	}
}
