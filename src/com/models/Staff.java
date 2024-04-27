package com.models;

import java.sql.Date;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.enums.Role;

public class Staff extends Guest implements Person {
	
	protected int lvlOfEdct;
	protected int yearsOfXp;
	protected double salary;
	public Staff(String name, String lastname, String sex, String dateOfBirth, String phoneNumber, String adddress,
			String username, String password, int lvlOfEdct,int yearsOfXp, Role role) {
		super(name, lastname, sex, dateOfBirth, phoneNumber, adddress, username, password);
		this.lvlOfEdct = lvlOfEdct;
		this.yearsOfXp = yearsOfXp;
		this.role = role;
		calculateSalary();
		
	}
	private void calculateSalary() { //to be implemented
		this.salary = 0;
		
	}
	@Override
	public JsonObject getJson() {
		
		Gson gson = new Gson();
        String jsonString = gson.toJson(Staff.this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

	    return jsonObject;
	}
}

