package com.models.enums;

public enum RoomStatus {
	AVAILABLE("Available"),BUSY("Busy"),CLEANING("Cleaning");
	
	private final String status;
	private RoomStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
}
