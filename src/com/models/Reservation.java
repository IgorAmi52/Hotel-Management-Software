package com.models;

import com.models.enums.ReservationStatus;
import com.models.enums.RoomType;

public class Reservation {
	
	private String checkInDate;
	private String checkOutDate;
	private RoomType roomType;
	private  String roomID;
	private  String guestID;
	private AdditonalService[] addServices;
	private double price;
	
	private ReservationStatus status = ReservationStatus.PENDING;
	
	public Reservation(String checkInDate, String checkOutDate, RoomType roomType, AdditonalService[] addServices) {
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomType = roomType;
		this.addServices = addServices;
	}
}
