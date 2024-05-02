package com.models.enums;

public enum ServiceType {
	
	ONE_BED(RoomType.ONE_BED),
	TWO_BEDS(RoomType.TWO_BEDS),
	ONE_DOUBLE_BED(RoomType.ONE_DOUBLE_BED),
	THREE_BEDS(RoomType.THREE_BEDS),
	BREAKFAST(AdditionalServiceType.BREAKFAST),
	LUNCH(AdditionalServiceType.LUNCH),
	DINNER(AdditionalServiceType.DINNER);

    private final Enum<?> type;
    private final String service;
    
    ServiceType(Enum<?> type) {
        this.type = type;
        if(type instanceof RoomType) {
        	this.service = ((RoomType) type).getType();
        }
        else {
        	this.service = ((AdditionalServiceType) type).getService();
        }
    }

    public Enum<?> getType() {
        return type;
    }
    public String getService() {
    	return service;
    }
    public static ServiceType getByAssociatedValue(String value) {
        for (ServiceType enumValue : values()) {
        	if(enumValue.service == value) {
        		return enumValue;
        	}
        }
        throw new IllegalArgumentException("No enum value found for associated value: " + value);
    }

}
