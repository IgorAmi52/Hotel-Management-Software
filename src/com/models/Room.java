package com.models;

import java.util.UUID;

import com.models.enums.RoomStatus;
import com.models.enums.RoomType;

public class Room {
	private RoomType type;
	private RoomStatus status = RoomStatus.AVAILABLE;
	private final String ID;
	
	public Room(RoomType type) {
		this.type = type;
		this.ID = UUID.randomUUID().toString();
	}
	public Room(RoomType type, String ID) {
		this.type = type;
		this.ID = ID;
	}
	public void changeStatus(RoomStatus status) {
		this.status = status;
	}
}
