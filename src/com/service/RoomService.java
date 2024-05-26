package com.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.exceptions.ElementAlreadyExistsException;
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
		
		jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).add(room.getID(), room.getJson());
		int nextID = getNextRoomID()+1;
		jsonObject.addProperty("next ID", nextID);
		
		writer = new FileWriter("data/rooms.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
	}
	public static void deleteRoom(Room room) throws IOException {
	reader = new FileReader("data/rooms.json");
		
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		JsonObject roomObj = room.getJson();
		
		jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).remove(room.getID());
		
		writer = new FileWriter("data/rooms.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
	}
	public static void addRoomType(String type)throws IOException{
		reader = new FileReader("data/rooms.json");
		
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		jsonObject.get("roomTypes").getAsJsonArray().add(type);
		writer = new FileWriter("data/rooms.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
	}
	public static String[] getRoomTypes() throws IOException {
		reader = new FileReader("data/rooms.json");
		

		JsonArray jsonArray = gson.fromJson(reader, JsonObject.class).getAsJsonArray("roomTypes");
		reader.close();
		
        String[] roomTypes = new String[jsonArray.size()];

        // Iterate through the JsonArray and populate the String array
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement element = jsonArray.get(i);
            roomTypes[i] = element.getAsString();
        }
        return roomTypes;
	}

	public static String[][] getRooms() throws IOException{
		reader = new FileReader("data/rooms.json");
		

		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class).getAsJsonObject("rooms");
		reader.close();
		
		int arrLength = 0;
		for(String type: jsonObject.keySet()) {
			for(String ID: jsonObject.getAsJsonObject(type).keySet()) {
				arrLength++;
			}
		}
		if(arrLength==0) {
			return new String[0][4];
		}
		String[][] ret = new String[arrLength][];
		
		int i = 0;
		for(String type: jsonObject.keySet()) {
			JsonObject typeObject = jsonObject.getAsJsonObject(type);
			for(String ID: typeObject.keySet()) {
				String status = typeObject.getAsJsonObject(ID).get("status").getAsString();
				String [] roomArr = {type,ID,status};
				ret[i]= roomArr;
				i++;
			}
		}
		return ret;
	}
	public static void addAddService(String name) throws IOException, ElementAlreadyExistsException {
		reader = new FileReader("data/addServices.json");
		
		JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
		JsonArray jsonArr = jsonObj.getAsJsonArray("services");
		reader.close();
		
		//check if already exists
		
		for(int i = 0; i < jsonArr.size(); i++) {
		        String elementString = jsonArr.get(i).getAsString();
		        if (elementString.equals(name)) {
		          throw new ElementAlreadyExistsException("This service already exists in the database!");
			}
		}
		jsonObj.getAsJsonArray("services").add(name);
		
		
		writer = new FileWriter("data/addServices.json");
		writer.write(new Gson().toJson(jsonObj));
		writer.close();
	}
	public static void deleteAddService(String name) throws IOException {
		
		reader = new FileReader("data/addServices.json");
		JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
		JsonArray jsonArr = jsonObj.getAsJsonArray("services");
		reader.close();
		
		for(int i = 0; i < jsonArr.size(); i++) {
			JsonElement element = jsonArr.get(i);
	        String elementString = element.getAsString();
	        
	        if (elementString.equals(name)) {
	        	jsonObj.getAsJsonArray("services").remove(element);
	          break;
	        }
		}
		
		writer = new FileWriter("data/addServices.json");
		writer.write(new Gson().toJson(jsonObj));
		writer.close();
	}
	
	public static String[][] getAddServices() throws IOException{
	reader = new FileReader("data/addServices.json");
		
		JsonArray jsonArr = gson.fromJson(reader, JsonObject.class).getAsJsonArray("services");
		
		reader.close();
		
		String[][] ret = new String [jsonArr.size()][];
		
		for(int i = 0; i < jsonArr.size(); i++) {
		      String elementString = jsonArr.get(i).getAsString();
			String[] addService = {elementString};
			ret[i] = addService;
		}
		return ret;
	}
	public static String[] getAddServicesArr() {
		String[][] addMatrix;
		try {
			addMatrix = getAddServices();
		} catch (IOException e) {
			e.printStackTrace();
			return new String[0];
		}
		
		String[] ret = new String[addMatrix.length];
		
		for(int i = 0; i < addMatrix.length;i++) {
			ret[i]= addMatrix[i][0];
		}
		return ret;
 	}
}
