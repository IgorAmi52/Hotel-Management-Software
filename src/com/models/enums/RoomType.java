package com.models.enums;

public enum RoomType {
	ONE_BED("One bed"),TWO_BEDS("Two beds"),ONE_DOUBLE_BED("One double bed"),THREE_BEDS("Three beds");

	private final String type;
	private RoomType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public static String[] getTypes() {
		String[] values = new String[RoomType.values().length];
		int i = 0;
		for(RoomType type: RoomType.values()) {
			values[i] = type.getType();
			i++;
		}
		return values;
	}
}
