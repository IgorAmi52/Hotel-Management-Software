package com.models.enums;

public enum ReservationStatus {
	PENDING("Pending"),CONFIRMED("Confirmed"),REJECTED("Rejected"),CANCELED("Canceled"),ERROR("Error");

	private final String status;
	ReservationStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
}
