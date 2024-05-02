package com.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.models.Room;

public class RoomService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;

	
	public static int getNextRoomID() throws IOException {
		
		reader = new FileReader("data/rooms.json");
		
		int nextID = gson.fromJson(reader, JsonObject.class).getAsJsonPrimitive("next ID").getAsInt();
		reader.close();
		
		return nextID;
	}


	public static void addRoom(Room room) throws IOException {
		
		reader = new FileReader("data/rooms.json");
		
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		jsonObject.get("rooms").getAsJsonArray().add(room.getJson());
		int nextID = getNextRoomID()+1;
		jsonObject.addProperty("next ID", nextID);
		
		FileWriter writer = new FileWriter("data/rooms.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
	}
	public static String[][] getRooms() throws IOException{
		reader = new FileReader("data/rooms.json");
		

		JsonArray jsonArr = gson.fromJson(reader, JsonObject.class).getAsJsonArray("rooms");
		reader.close();

		if(jsonArr.size()==0) {
			return new String[0][3];
		}
		
		String[][] ret = new String[jsonArr.size()][];
		
		for(int i = 0; i < jsonArr.size(); i++) {
			JsonObject roomJson = jsonArr.get(i).getAsJsonObject();
			String roomType = roomJson.get("type").getAsString();
			String roomID =  roomJson.get("ID").getAsString();
			String status = roomJson.get("status").getAsString();
			String[] room = {roomType,roomID,status};
			
			ret[i] = room;
		}
		return ret;
	}
}
