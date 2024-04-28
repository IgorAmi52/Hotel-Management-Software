package com.models.enums;

public enum AdditionalServiceType {
	BREAKFAST("Breakfast"), LUNCH("Lunch"), DINNER("Dinner");
	
	private final String service;
	
	private AdditionalServiceType(String service) {
		this.service = service;
	}
    public String getService() {
        return service;
    }
	
}
