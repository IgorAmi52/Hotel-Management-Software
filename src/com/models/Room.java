package com.models;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.enums.RoomStatus;
import com.models.enums.RoomType;

public class Room {
	private String type;
	private RoomStatus status = RoomStatus.AVAILABLE;
	private final int ID;

	public Room(String type, int ID) {
		this.type = type;
		this.ID = ID;
	}
	public void changeStatus(RoomStatus status) {
		this.status = status;
	}
	public JsonObject getJson() {
		Gson gson = new Gson();
        String jsonString = gson.toJson(Room.this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        
        return jsonObject;
	}
	public String getType() {
		return type;
	}
	public String getID() {
		return String.valueOf(ID);
	}
}
