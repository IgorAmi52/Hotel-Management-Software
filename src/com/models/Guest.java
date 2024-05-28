package com.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.enums.Role;

public class Guest extends Person implements User{
	
	protected Role role;

	public Guest(String name, String lastname, String sex, String dateOfBirth, String phoneNumber, String address, String username, String password) {
		super(name, lastname, sex, dateOfBirth, phoneNumber, address, username, password);
		this.role = Role.GUEST;
	}
	public JsonObject getJson() {
		
		Gson gson = new Gson();
        String jsonString = gson.toJson(Guest.this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

	    return jsonObject;
	}
	@Override
	public Role getRole() {
		// TODO Auto-generated method stub
		return this.role;
	}
	   @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        if (!super.equals(o)) return false;
	        Guest guest = (Guest) o;
	        return role == guest.role;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(super.hashCode(), role);
	    }
}
