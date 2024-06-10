package com.models;

import java.sql.Date;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.enums.Role;

 public class Staff extends Person implements User{
	
	protected int lvlOfEdct;
	protected int yearsOfXp;
	protected double salary;
	protected Role role;
	public Staff(String name, String lastname, String sex, String dateOfBirth, String phoneNumber, String address,
			String username, String password, int lvlOfEdct,int yearsOfXp, Role role) {
		super(name, lastname, sex, dateOfBirth, phoneNumber, address, username, password);
		this.lvlOfEdct = lvlOfEdct;
		this.yearsOfXp = yearsOfXp;
		this.role = role;
		calculateSalary();
		
	}
	private void calculateSalary() { //to be implemented
		this.salary = lvlOfEdct*300+yearsOfXp*100;
		
	}
	@Override
	public JsonObject getJson() {
		
		Gson gson = new Gson();
        String jsonString = gson.toJson(Staff.this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

	    return jsonObject;
	}
	@Override
	public Role getRole() {
		// TODO Auto-generated method stub
		return this.role;
	}

}

