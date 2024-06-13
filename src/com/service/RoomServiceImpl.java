package com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exceptions.ElementAlreadyExistsException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.models.Room;
import com.models.Staff;
import com.models.User;
import com.models.enums.DataTypes;
import com.models.enums.Role;
import com.models.enums.RoomStatus;

public class RoomServiceImpl implements RoomServiceInterface {

	private DataAccessInterface dataAccessService;
	private ReportsServiceInterface reportsService;
	private UserService userService;
	private Gson gson = new Gson();

	public RoomServiceImpl(DataAccessInterface dataAccessService, ReportsServiceInterface reportsService,
			UserService userService) {
		this.dataAccessService = dataAccessService;
		this.reportsService = reportsService;
		this.userService = userService;
	}

	public int getNextRoomID() throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);
		int nextID = jsonObject.getAsJsonPrimitive("next ID").getAsInt();
		return 2;
	}

	public void addRoom(Room room) throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);
		if (jsonObject.getAsJsonObject("rooms").has(room.getType())) {
			// Room type exists, add the new room to the existing type
			jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).add(room.getID(), room.getJson());
		} else {
			// Room type does not exist, create a new room type and add the room
			JsonObject newRoomType = new JsonObject();
			newRoomType.add(room.getID(), room.getJson());
			jsonObject.getAsJsonObject("rooms").add(room.getType(), newRoomType);
		}
		int nextID = this.getNextRoomID() + 1;
		jsonObject.addProperty("next ID", nextID);

		dataAccessService.setData(DataTypes.ROOMS, jsonObject);
	}

	public void deleteRoom(Room room) throws IOException {
		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);
		jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).remove(room.getID());
		dataAccessService.setData(DataTypes.ROOMS, jsonObject);
	}

	public void addRoomType(String type) throws IOException {
		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);
		jsonObject.get("roomTypes").getAsJsonArray().add(type);
		dataAccessService.setData(DataTypes.ROOMS, jsonObject);
	}

	public void deleteRoomType(String name) throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);
		JsonArray jsonArr = jsonObject.getAsJsonArray("roomTypes");

		for (int i = 0; i < jsonArr.size(); i++) {
			JsonElement element = jsonArr.get(i);
			String elementString = element.getAsString();

			if (elementString.equals(name)) {
				jsonObject.getAsJsonArray("roomTypes").remove(element);
				break;
			}
		}
		dataAccessService.setData(DataTypes.ROOMS, jsonObject);
	}

	public String[] getRoomTypes() throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);
		JsonArray jsonArray = jsonObject.getAsJsonArray("roomTypes");

		String[] roomTypes = new String[jsonArray.size()];

		// Iterate through the JsonArray and populate the String array
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement element = jsonArray.get(i);
			roomTypes[i] = element.getAsString();
		}
		return roomTypes;
	}

	public Room[] getRooms() throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS).getAsJsonObject("rooms");
		List<Room> roomArrList = new ArrayList<Room>();

		for (String type : jsonObject.keySet()) {
			JsonObject typeObject = jsonObject.getAsJsonObject(type);
			for (String ID : typeObject.keySet()) {
				Room currentRoom = gson.fromJson(typeObject.get(ID), Room.class);
				roomArrList.add(currentRoom);
			}
		}
		Room[] ret = new Room[roomArrList.size()];
		int i = 0;
		for (Room room : roomArrList) {
			ret[i++] = room;
		}

		return ret;
	}

	public void addAddService(String name) throws IOException, ElementAlreadyExistsException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ADD_SERVICES);
		JsonArray jsonArr = jsonObject.getAsJsonArray("services");

		// check if already exists

		for (int i = 0; i < jsonArr.size(); i++) {
			String elementString = jsonArr.get(i).getAsString();
			if (elementString.equals(name)) {
				throw new ElementAlreadyExistsException("This service already exists in the database!");
			}
		}
		jsonObject.getAsJsonArray("services").add(name);
		dataAccessService.setData(DataTypes.ADD_SERVICES, jsonObject);
	}

	public void deleteAddService(String name) throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ADD_SERVICES);
		JsonArray jsonArr = jsonObject.getAsJsonArray("services");

		for (int i = 0; i < jsonArr.size(); i++) {
			JsonElement element = jsonArr.get(i);
			String elementString = element.getAsString();

			if (elementString.equals(name)) {
				jsonObject.getAsJsonArray("services").remove(element);
				dataAccessService.setData(DataTypes.ROOMS, jsonObject);
				return;
			}
		}
	}

	public String[] getRoomIDs() throws IOException {
		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS).getAsJsonObject("rooms");
		List<String> idsArrayList = new ArrayList<String>();
		for (String type : jsonObject.keySet()) {
			JsonObject typeObject = jsonObject.getAsJsonObject(type);
			for (String ID : typeObject.keySet()) {
				idsArrayList.add(ID);
			}
		}
		String[] ret = new String[idsArrayList.size()];
		int i = 0;

		for (String ID : idsArrayList) {
			ret[i++] = ID;
		}
		return ret;
	}

	public String[][] getAddServices() throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ADD_SERVICES);
		JsonArray jsonArr = jsonObject.getAsJsonArray("services");

		String[][] ret = new String[jsonArr.size()][];

		for (int i = 0; i < jsonArr.size(); i++) {
			String elementString = jsonArr.get(i).getAsString();
			String[] addService = { elementString };
			ret[i] = addService;
		}
		return ret;
	}

	public String[] getAddServicesArr() {
		String[][] addMatrix;
		try {
			addMatrix = getAddServices();
		} catch (IOException e) {
			e.printStackTrace();
			return new String[0];
		}

		String[] ret = new String[addMatrix.length];

		for (int i = 0; i < addMatrix.length; i++) {
			ret[i] = addMatrix[i][0];
		}
		return ret;
	}

	public void checkInRoom(Room room) throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);
		room.changeStatus(RoomStatus.BUSY);
		jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).add(room.getID(), room.getJson());
		dataAccessService.setData(DataTypes.ROOMS, jsonObject);
	}

	public void checkOutRoom(Room room) throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);

		room.changeStatus(RoomStatus.CLEANING);
		jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).add(room.getID(), room.getJson());

		dataAccessService.setData(DataTypes.ROOMS, jsonObject);
		assignCleaner(room);
	}

	public void cleanRoom(Room room, User cleaner) throws IOException {
		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);

		room.changeStatus(RoomStatus.AVAILABLE);
		jsonObject.getAsJsonObject("rooms").getAsJsonObject(room.getType()).add(room.getID(), room.getJson());

		dataAccessService.setData(DataTypes.ROOMS, jsonObject);

		jsonObject = dataAccessService.getData(DataTypes.CLEANING);
		JsonArray cleanerArr = jsonObject.getAsJsonArray(cleaner.getUserName());

		for (int i = 0; i < cleanerArr.size(); i++) {
			Room currentRoom = gson.fromJson(cleanerArr.get(i), Room.class);
			if (currentRoom.getID().equals(room.getID())) {
				cleanerArr.remove(i);
				break;
			}
		}
		jsonObject.add(cleaner.getUserName(), cleanerArr);

		dataAccessService.setData(DataTypes.CLEANING, jsonObject);

	}

	private void assignCleaner(Room room) throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.USERS);

		Map<String, Integer> cleaners = new HashMap<>();

		for (String username : jsonObject.keySet()) {
			String role = jsonObject.getAsJsonObject(username).get("role").getAsString();
			if (role.equals(Role.CLEANER.toString())) {
				Staff cleaner = gson.fromJson(jsonObject.getAsJsonObject(username), Staff.class);
				cleaners.put(cleaner.getUserName(), 0);
			}
		}
		jsonObject = dataAccessService.getData(DataTypes.CLEANING);

		Staff selectedCleaner;

		for (String cleaner : jsonObject.keySet()) {
			cleaners.put(cleaner, jsonObject.getAsJsonArray(cleaner).size());
		}
		String cleaner = "";
		Integer work = Integer.MAX_VALUE;
		for (Map.Entry<String, Integer> entry : cleaners.entrySet()) {
			if (entry.getValue() < work) {
				work = entry.getValue();
				cleaner = entry.getKey();
			}
		}
		if (jsonObject.has(cleaner)) {
			jsonObject.getAsJsonArray(cleaner).add(room.getJson());
		} else {
			JsonArray cleanerArr = new JsonArray();
			cleanerArr.add(room.getJson());
			jsonObject.add(cleaner, cleanerArr);
		}
		selectedCleaner = userService.getStaff(cleaner);

		dataAccessService.setData(DataTypes.CLEANING, jsonObject);
		reportsService.cleaningAssigned(selectedCleaner);
	}

	public Room[] getCleanersRooms(User cleaner) throws IOException {

		JsonObject jsonObject = dataAccessService.getData(DataTypes.CLEANING);

		if (!jsonObject.has(cleaner.getUserName())) {
			return new Room[0];
		}
		JsonArray roomsJsonArr = jsonObject.getAsJsonArray(cleaner.getUserName());

		List<Room> roomArrList = new ArrayList<Room>();

		for (JsonElement roomJson : roomsJsonArr) {
			Room room = gson.fromJson(roomJson, Room.class);
			roomArrList.add(room);
		}
		Room[] ret = new Room[roomArrList.size()];
		int i = 0;
		for (Room room : roomArrList) {
			ret[i++] = room;
		}
		return ret;
	}
}
