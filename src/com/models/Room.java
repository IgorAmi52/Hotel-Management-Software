package com.models;

import java.util.UUID;

import com.google.gson.JsonObject;
import com.models.enums.RoomStatus;
import com.models.enums.RoomType;

public class Room {
	private RoomType type;
	private RoomStatus status = RoomStatus.AVAILABLE;
	private final int ID;

	public Room(RoomType type, int ID) {
		this.type = type;
		this.ID = ID;
	}
	public void changeStatus(RoomStatus status) {
		this.status = status;
	}
	public JsonObject getJson() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("status", status.getStatus());	
		return jsonObject;
	}
	public String getType() {
		return type.getType();
	}
	public String getID() {
		return String.valueOf(ID);
	}
}
