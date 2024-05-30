package com.service;

import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.exceptions.NoRoomAvailableException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.models.User;
import com.models.enums.ReservationStatus;
import com.models.enums.Role;
import com.models.enums.RoomStatus;
import com.models.Pricing;
import com.models.Reservation;
import com.models.Room;

public class ReservationService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;

	
	public static void requestReservation(Reservation reservation) throws IOException {
		// a lot of checking and validation
		reader = new FileReader("data/reservations.json");
		
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();

 		jsonObject.get(reservation.getStatus()).getAsJsonArray().add(reservation.getJson());
		writer = new FileWriter("data/reservations.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
	}
	public static String[][] getReservations(User user) throws IOException{
		
		reader = new FileReader("data/reservations.json");
	
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class).getAsJsonObject();
		reader.close();
		
		int count = 0;
		Role role = user.getRole();
		for(String status: jsonObject.keySet()) {
			for(JsonElement res: jsonObject.getAsJsonArray(status)) {
				if(role == Role.GUEST) {
					JsonObject resGuest = ((JsonObject)res).get("guest").getAsJsonObject();
					String resUsername = resGuest.get("username").getAsString();
					if(resUsername.equals(user.getUserName())) { // if reservation from our guest
						count++;
					}
				}
				else {
					count++;
				}
			}
		}
		if(count==0) {
			return new String[0][7];
		}
		String[][] ret = new String[count][];
		int i = 0;
		for(String status: jsonObject.keySet()) {
			for(JsonElement res: jsonObject.getAsJsonArray(status)) {				
				JsonObject resGuest = ((JsonObject)res).get("guest").getAsJsonObject();
				String resUsername = resGuest.get("username").getAsString();
				if(resUsername.equals(user.getUserName()) || role != Role.GUEST) { 
					
					JsonObject resObj = (JsonObject)res;
					
					String roomType = resObj.get("roomType").getAsString();
					String checkInDate = resObj.get("checkInDate").getAsString();
				    String checkOutDate = resObj.get("checkOutDate").getAsString();
			        String comment = resObj.get("comment").getAsString();
			        if (role!=Role.GUEST) {
			        	comment = resUsername;
			        }
			        String price = resObj.get("price").getAsString();
			        String addServices = String.join(", ", gson.fromJson(resObj.get("addServices"), String[].class));
			        
			        
			        String[] resArr = {roomType,checkInDate,checkOutDate,addServices,status,price,comment};
			        ret[i] = resArr;
			        i++;
				}
			}
		}
		return ret;
	}
	public static void cancelReservation(Reservation reservation) throws IOException {
		reader = new FileReader("data/reservations.json");

		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		JsonArray arrPending = jsonObject.getAsJsonArray("Pending");
		JsonArray arrCancelled = jsonObject.getAsJsonArray("Cancelled");
        for (int i = 0; i < arrPending.size(); i++) {
            JsonObject current = arrPending.get(i).getAsJsonObject();
            Reservation currentReservation = gson.fromJson(current, Reservation.class);
            
            if (currentReservation.equals(reservation)) {
            	arrPending.remove(i);
                reservation.cancelReservation();
                arrCancelled.add(reservation.getJson());
                break;
            }
        }
        jsonObject.add("Pending", arrPending);
        jsonObject.add("Cancelled", arrCancelled);
        writer = new FileWriter("data/reservations.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
	}
	public static Reservation[] getTodaysCheckInReservations() throws IOException{
		
		reader = new FileReader("data/reservations.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");
		reader.close();
		
		ArrayList<Reservation> retArrList = new ArrayList<Reservation>();
		String todaysDate = DateLabelFormatter.getTodaysDateStr();

		for(int i = 0; i< confirmedArr.size();i++) {
			Reservation currentReservation = gson.fromJson(confirmedArr.get(i), Reservation.class);
			Room room = currentReservation.getRoom();
			String checkInDate = currentReservation.getCheckInDate();
			String checkOutDate = currentReservation.getCheckOutDate();
			if(DateLabelFormatter.isFirstDateGreater(checkInDate, todaysDate)) {
				rejectReservation(currentReservation);
			}
			else if(todaysDate.equals(checkInDate) && room.getStatus()==RoomStatus.AVAILABLE) {
				retArrList.add(currentReservation);
			}
		}
		Reservation[] ret = new Reservation[retArrList.size()];
		
		for(int i = 0; i < retArrList.size(); i++) {
			ret[i] = retArrList.get(i);
		}
		return ret;
	}
	public static Reservation[] getTodaysCheckOutReservations() throws IOException{
		
		reader = new FileReader("data/reservations.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");
		reader.close();
		
		ArrayList<Reservation> retArrList = new ArrayList<Reservation>();
		String todaysDate = DateLabelFormatter.getTodaysDateStr();

		for(int i = 0; i< confirmedArr.size();i++) {
			Reservation currentReservation = gson.fromJson(confirmedArr.get(i), Reservation.class);
			String checkOutDate = currentReservation.getCheckOutDate();

			if(todaysDate.equals(checkOutDate)) {
				retArrList.add(currentReservation);
			}
		}
		Reservation[] ret = new Reservation[retArrList.size()];
		
		for(int i = 0; i < retArrList.size(); i++) {
			ret[i] = retArrList.get(i);
		}
		return ret;
	}
	public static void proccessReservation(Reservation reservation) throws IOException, NoRoomAvailableException{
		reader = new FileReader("data/rooms.json");

		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		JsonObject roomsObject = jsonObject.getAsJsonObject("rooms");
		
		if (!roomsObject.has(reservation.getRoomType())){
			throw new NoRoomAvailableException("No room of this type exists currently!");
		}
		JsonObject typeObject = roomsObject.getAsJsonObject(reservation.getRoomType());
		Set<String> ids = typeObject.keySet();
		
		
		reader = new FileReader("data/reservations.json");
		jsonObject = gson.fromJson(reader, JsonObject.class);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");
		JsonArray pendingArr = jsonObject.getAsJsonArray("Pending");
		reader.close();
		
		
		for(int i = 0; i < confirmedArr.size();i++) {
			JsonObject currentReservation = confirmedArr.get(i).getAsJsonObject();
			Room currentRoom = gson.fromJson(currentReservation.getAsJsonObject("room"), Room.class);
			
			if(ids.contains(currentRoom.getID())) {
				String start1 = currentReservation.get("checkInDate").getAsString();
				String end1 = currentReservation.get("checkOutDate").getAsString();
				String start2 = reservation.getCheckInDate();
				String end2  = reservation.getCheckOutDate();
				
				if(DateLabelFormatter.checkIntervalOverlap(start1, end1, start2, end2)) {
					ids.remove(currentRoom.getID());
				}
			}		
		}
		if(ids.isEmpty()) {
			throw new NoRoomAvailableException("No room of this type is available in that period!");
		}
		for(int i=0;i<pendingArr.size();i++) {
			Reservation currentReservation = gson.fromJson(pendingArr.get(i), Reservation.class);
			if(reservation.equals(currentReservation)) {
				pendingArr.remove(i);
				break;
			}
		}

		List<String> idsList = new ArrayList<>(ids);
		Room room = gson.fromJson(roomsObject.getAsJsonObject(reservation.getRoomType()).getAsJsonObject(idsList.get(0)), Room.class); //get free room
		
		reservation.setRoom(room);
		reservation.setStatus(ReservationStatus.CONFIRMED);
		
		confirmedArr.add(reservation.getJson());
        jsonObject.add("Pending", pendingArr);
        jsonObject.add("Confirmed", confirmedArr);
        writer = new FileWriter("data/reservations.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
	}
	
	public static void rejectReservation(Reservation reservation) throws IOException {
		reader = new FileReader("data/reservations.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		JsonArray rejectedArr = jsonObject.getAsJsonArray("Rejected");
		JsonArray pendingArr = jsonObject.getAsJsonArray("Pending");
		reader.close();
		
		for(int i=0;i<pendingArr.size();i++) {
			Reservation currentReservation = gson.fromJson(pendingArr.get(i), Reservation.class);
			if(reservation.equals(currentReservation)) {
				pendingArr.remove(i);
				break;
			}
		}
		reservation.setStatus(ReservationStatus.REJECTED);
		rejectedArr.add(reservation.getJson());
		jsonObject.add("Pending", pendingArr);
		jsonObject.add("Rejected", rejectedArr);
        writer = new FileWriter("data/reservations.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
	}
	public static void checkInReservation(Reservation reservation) throws IOException {
		reader = new FileReader("data/reservations.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");

		reader.close();
		for(int i=0; i<confirmedArr.size();i++) {
			Reservation currenctReservation = gson.fromJson(confirmedArr.get(i), Reservation.class);
			if(reservation.equals(currenctReservation)) {
				confirmedArr.remove(i);
				Room room = currenctReservation.getRoom();
				room.changeStatus(RoomStatus.BUSY);
				confirmedArr.add(currenctReservation.getJson());
				break;
			}
		}
		jsonObject.add("Confirmed", confirmedArr);
        writer = new FileWriter("data/reservations.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
	
	}
	public static void archiveReservation(Reservation reservation) throws IOException {
		reader = new FileReader("data/reservations.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");
		JsonArray archiveArr = jsonObject.getAsJsonArray("Archive");
		reader.close();
		for(int i=0; i<confirmedArr.size();i++) {
			Reservation currenctReservation = gson.fromJson(confirmedArr.get(i), Reservation.class);
			if(reservation.equals(currenctReservation)) {
				confirmedArr.remove(i);
				break;
			}
		}
		archiveArr.add(reservation.getJson());
		jsonObject.add("Confirmed", confirmedArr);
		jsonObject.add("Archive", archiveArr);
        writer = new FileWriter("data/reservations.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
	
	}
}
