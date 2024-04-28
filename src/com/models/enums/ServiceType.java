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

    ServiceType(Enum<?> type) {
        this.type = type;
    }

    public Enum<?> getType() {
        return type;
    }

}
