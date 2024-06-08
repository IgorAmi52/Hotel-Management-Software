package com.models.enums;

public enum DataTypes {
	ADD_SERVICES("addServices"),CLEANING("cleaning"),PRICING("pricing"),REPORTS("reports"),RESERVATIONS("reservations"),ROOMS("rooms"),USERS("users");
	
	private final String type;
	
	DataTypes(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return this.type;
	}
}
