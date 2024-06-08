package com.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Random;


import com.exceptions.ElementAlreadyExistsException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.models.Room;
import com.models.Staff;
import com.models.User;
import com.models.enums.Role;
import com.models.enums.RoomStatus;

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
        if (jsonObject.getAsJsonObject("rooms").has(room.getType())) {
            // Room type exists, add the new room to the existing type
        	jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).add(room.getID(), room.getJson());
        } else {
            // Room type does not exist, create a new room type and add the room
            JsonObject newRoomType = new JsonObject();
            newRoomType.add(room.getID(), room.getJson());
            jsonObject.getAsJsonObject("rooms").add(room.getType(), newRoomType);
        }
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

	public static void deleteRoomType(String name) throws IOException {
		
		reader = new FileReader("data/rooms.json");
		JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
		JsonArray jsonArr = jsonObj.getAsJsonArray("roomTypes");
		reader.close();
		
		for(int i = 0; i < jsonArr.size(); i++) {
			JsonElement element = jsonArr.get(i);
	        String elementString = element.getAsString();
	        
	        if (elementString.equals(name)) {
	        	jsonObj.getAsJsonArray("roomTypes").remove(element);
	          break;
	        }
		}
		
		writer = new FileWriter("data/rooms.json");
		writer.write(new Gson().toJson(jsonObj));
		writer.close();
	}
	
	public static String[] getRoomTypes() throws IOException {
		reader = new FileReader("data/rooms.json");
		

		JsonArray jsonArray = gson.fromJson(reader, JsonObject.class).getAsJsonArray("roomTypes");
		reader.close();
		
        String[]roomTypes = new String[jsonArray.size()];

        // Iterate through the JsonArray and populate the String array
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement element = jsonArray.get(i);
            roomTypes[i] = element.getAsString();
        }
        return roomTypes;
	}

	public static Room[] getRooms() throws IOException{
		
		reader = new FileReader("data/rooms.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class).getAsJsonObject("rooms");
		reader.close();
		
		List<Room> roomArrList = new ArrayList<Room>();

		for(String type: jsonObject.keySet()) {
			JsonObject typeObject = jsonObject.getAsJsonObject(type);
			for(String ID: typeObject.keySet()) {
				Room currentRoom = gson.fromJson(typeObject.get(ID), Room.class);
				roomArrList.add(currentRoom);
			}
		}
		Room[]ret = new Room[roomArrList.size()];
		int i = 0;
		for(Room room:roomArrList) {
			ret[i++]=room;
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
	public static void checkInRoom(Room room) throws IOException {
		reader = new FileReader("data/rooms.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		room.changeStatus(RoomStatus.BUSY);
		jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).add(room.getID(), room.getJson());
		writer = new FileWriter("data/rooms.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
	}
	public static void checkOutRoom(Room room) throws IOException {
		reader = new FileReader("data/rooms.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		room.changeStatus(RoomStatus.CLEANING);
		jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).add(room.getID(), room.getJson());
		writer = new FileWriter("data/rooms.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
		assignCleaner(room);
	}
	public static void cleanRoom(Room room, User cleaner) throws IOException {
		reader = new FileReader("data/rooms.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		room.changeStatus(RoomStatus.AVAILABLE);
		jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).add(room.getID(), room.getJson());
		
		writer = new FileWriter("data/rooms.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
		reader = new FileReader("data/cleaning.json");
		jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();

		JsonArray cleanerArr = jsonObject.getAsJsonArray(cleaner.getUserName());
		
		for(int i = 0; i < cleanerArr.size();i++) {
			Room currentRoom = gson.fromJson(cleanerArr.get(i), Room.class);
			if(currentRoom.getID().equals(room.getID())) {
				cleanerArr.remove(i);
				break;
			}
		}
		jsonObject.add(cleaner.getUserName(), cleanerArr);
		
		writer = new FileWriter("data/cleaning.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
	}
	private static void assignCleaner(Room room) throws IOException {
		reader = new FileReader("data/users.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		

		Map<String, Integer> cleaners = new HashMap<>();
	
		for(String username: jsonObject.keySet()) {
			String role = jsonObject.getAsJsonObject(username).get("role").getAsString();
			if(role.equals(Role.CLEANER.toString())) {
				Staff cleaner = gson.fromJson(jsonObject.getAsJsonObject(username), Staff.class);
				cleaners.put(cleaner.getUserName(), 0);
			}
		}
		reader = new FileReader("data/cleaning.json");
		jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();

		Staff selectedCleaner;
		
		for(String cleaner: jsonObject.keySet()) {
			cleaners.put(cleaner, jsonObject.getAsJsonArray(cleaner).size());
		}
		String cleaner = "";
		Integer work = Integer.MAX_VALUE;
        for (Map.Entry<String, Integer> entry : cleaners.entrySet()) {
        	if(entry.getValue()< work) {
        		work = entry.getValue();
        		cleaner = entry.getKey();
        	}
        }
        if(jsonObject.has(cleaner)) {
        	jsonObject.getAsJsonArray(cleaner).add(room.getJson());
        }
        else {
        	JsonArray cleanerArr = new JsonArray();
        	cleanerArr.add(room.getJson());
        	jsonObject.add(cleaner, cleanerArr);
        }
        selectedCleaner = UserService.getStaff(cleaner);

		writer = new FileWriter("data/cleaning.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
		ReportsService.cleaningAssigned(selectedCleaner);
	}
	
	public static Room[] getCleanersRooms(User cleaner) throws IOException {
		reader = new FileReader("data/cleaning.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
				
		if(!jsonObject.has(cleaner.getUserName())) {
			return new Room[0];
		}

		JsonArray roomsJsonArr = jsonObject.getAsJsonArray(cleaner.getUserName());
		
		List<Room> roomArrList = new ArrayList<Room>();

		for (JsonElement roomJson:roomsJsonArr) {
			Room room = gson.fromJson(roomJson, Room.class);
			roomArrList.add(room);	
		}
		Room [] ret = new Room[roomArrList.size()];
		int i = 0;
		for (Room room:roomArrList) {
			ret[i++] = room;
		}
		return ret;
	}
}
