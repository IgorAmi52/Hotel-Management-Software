package com.models.enums;

public enum Role {
	ADMIN("Admin"), AGENT("Agent"), CLEANER("Cleaner"),GUEST("Guest");
	
	private final String role;
	Role(String role) {
		this.role = role;
	}
	public String getRole() {
		return role;
	}
}
