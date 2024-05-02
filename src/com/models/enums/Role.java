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
    public static Role getByAssociatedValue(String value) {
        for (Role enumValue : values()) {
            if (enumValue.getRole() == value) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum value found for associated value: " + value);
    }
}
