package com.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.enums.Role;

abstract class Person {
	protected String name;
	protected String lastname;
	protected String sex;
	protected String dateOfBirth;
	protected String phoneNumber;
	protected String address;
	protected String username;
	protected String password;

	
	public Person(String name, String lastname, String sex, String dateOfBirth, String phoneNumber, String address, String username, String password) {
		this.name = name;
		this.lastname = lastname;
		this.sex = sex;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.username = username;
		this.password = password;
	}
	

	public String getUserName() {
		// TODO Auto-generated method stub
		return this.username;
	}

	public String getFullName() {
		// TODO Auto-generated method stub
		return (name+" " + lastname);
	}


}
