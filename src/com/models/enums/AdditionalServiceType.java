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
	public static String[] getTypes() {
		String[] values = new String[AdditionalServiceType.values().length];
		int i = 0;
		for(AdditionalServiceType type: AdditionalServiceType.values()) {
			values[i] = type.getService();
			i++;
		}
		return values;
	}

}
