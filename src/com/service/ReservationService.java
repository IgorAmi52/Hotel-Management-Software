package com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.exceptions.NoPricingException;
import com.exceptions.NoRoomAvailableException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.models.User;
import com.models.enums.DataTypes;
import com.models.enums.ReservationStatus;
import com.models.enums.Role;
import com.models.enums.RoomStatus;
import com.models.Reservation;
import com.models.Room;

public class ReservationService {
	
	private static DataAccessImpl dataAccessService = new DataAccessImpl();
	private static Gson gson = new Gson();
	
	public static void requestReservation(Reservation reservation) throws IOException {
		JsonObject jsonObject = dataAccessService.getData(DataTypes.RESERVATIONS);
 		jsonObject.get(reservation.getStatus()).getAsJsonArray().add(reservation.getJson());
 		dataAccessService.setData(DataTypes.RESERVATIONS, jsonObject);
	}
	
	public static Reservation[] getReservations(User user) throws IOException{
		
		JsonObject jsonObject = dataAccessService.getData(DataTypes.RESERVATIONS);
		Role role = user.getRole();
		List<Reservation> resArrayList = new ArrayList<Reservation>();
		
		for(String status: jsonObject.keySet()) {
			for(JsonElement res: jsonObject.getAsJsonArray(status)) {				
				JsonObject resGuest = ((JsonObject)res).get("guest").getAsJsonObject();
				String resUsername = resGuest.get("username").getAsString();
				if(resUsername.equals(user.getUserName()) || role != Role.GUEST) { 
			        Reservation currentReservation  = gson.fromJson(res, Reservation.class);
			        resArrayList.add(currentReservation);   		      			        
				}
			}
		}
		Reservation[] ret = new Reservation[resArrayList.size()];
		int i = 0;
		for(Reservation reservation: resArrayList) {
			ret[i++] = reservation;
		}
		return ret;
	}
	public static void cancelReservation(Reservation reservation) throws IOException {
		
		JsonObject jsonObject = dataAccessService.getData(DataTypes.RESERVATIONS);
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
        
        dataAccessService.setData(DataTypes.RESERVATIONS, jsonObject);
        ReportsService.reservationCancelled();
	}
	
	public static Reservation[] getTodaysCheckInReservations() throws IOException{
		
		JsonObject jsonObject = dataAccessService.getData(DataTypes.RESERVATIONS);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");
		
		ArrayList<Reservation> retArrList = new ArrayList<Reservation>();
		String todaysDate = DateLabelFormatter.getTodaysDateStr();

		for(int i = 0; i< confirmedArr.size();i++) {
			Reservation currentReservation = gson.fromJson(confirmedArr.get(i), Reservation.class);
			Room room = currentReservation.getRoom();
			String checkInDate = currentReservation.getCheckInDate();
			String checkOutDate = currentReservation.getCheckOutDate();
			if(DateLabelFormatter.isFirstDateGreater(checkInDate, todaysDate)) {
				rejectReservation(currentReservation,true);
			}
			else if(todaysDate.equals(checkInDate) && room.getStatus().equals(RoomStatus.AVAILABLE.getStatus())) {
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
		
		JsonObject jsonObject = dataAccessService.getData(DataTypes.RESERVATIONS);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");
		
		ArrayList<Reservation> retArrList = new ArrayList<Reservation>();
		String todaysDate = DateLabelFormatter.getTodaysDateStr();

		for(int i = 0; i< confirmedArr.size();i++) {
			Reservation currentReservation = gson.fromJson(confirmedArr.get(i), Reservation.class);
			String checkOutDate = currentReservation.getCheckOutDate();
			Room room = currentReservation.getRoom();

			if(todaysDate.equals(checkOutDate) && room.getStatus().equals(RoomStatus.BUSY.getStatus())) {
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
		
		JsonObject jsonObject = dataAccessService.getData(DataTypes.ROOMS);
		JsonObject roomsObject = jsonObject.getAsJsonObject("rooms");
		
		if (!roomsObject.has(reservation.getRoomType())){
			throw new NoRoomAvailableException("No room of this type exists currently!");
		}
		JsonObject typeObject = roomsObject.getAsJsonObject(reservation.getRoomType());
		Set<String> ids = typeObject.keySet();
		
		jsonObject = dataAccessService.getData(DataTypes.RESERVATIONS);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");
		JsonArray pendingArr = jsonObject.getAsJsonArray("Pending");

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
        
        dataAccessService.setData(DataTypes.RESERVATIONS, jsonObject);
        ReportsService.reservationConfirmed(reservation);
	}
	
	public static void rejectReservation(Reservation reservation, Boolean isDated) throws IOException {
		
		JsonObject jsonObject = dataAccessService.getData(DataTypes.RESERVATIONS);
		JsonArray rejectedArr = jsonObject.getAsJsonArray("Rejected");
		String resStageStr = "Pending";
		if(isDated) {
			resStageStr = "Confirmed";
		}
		JsonArray loopArr = jsonObject.getAsJsonArray(resStageStr);
	
		for(int i=0;i<loopArr.size();i++) {
			Reservation currentReservation = gson.fromJson(loopArr.get(i), Reservation.class);
			if(reservation.equals(currentReservation)) {
				loopArr.remove(i);
				break;
			}
		}
		reservation.setStatus(ReservationStatus.REJECTED);
		rejectedArr.add(reservation.getJson());
		jsonObject.add(resStageStr, loopArr);
		jsonObject.add("Rejected", rejectedArr);
		
		dataAccessService.setData(DataTypes.RESERVATIONS, jsonObject);
        ReportsService.reservationRejected();
	}
	public static void checkInReservation(Reservation reservation,String[] addServices) throws IOException, NoPricingException {
		
		JsonObject jsonObject = dataAccessService.getData(DataTypes.RESERVATIONS);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");

		for(int i=0; i<confirmedArr.size();i++) {
			Reservation currenctReservation = gson.fromJson(confirmedArr.get(i), Reservation.class);
			Room room = currenctReservation.getRoom();
			room.changeStatus(RoomStatus.BUSY);
			if(reservation.equals(currenctReservation)) {
				confirmedArr.remove(i);
				reservation.addAddServices(addServices);

				reservation.setPricing(PricingService.calculatePricing(reservation));
				// add pricing
				confirmedArr.add(reservation.getJson());
				break;
			}
		}
		jsonObject.add("Confirmed", confirmedArr);
		dataAccessService.setData(DataTypes.RESERVATIONS, jsonObject);
	}
	public static void archiveReservation(Reservation reservation) throws IOException {
		
		JsonObject jsonObject = dataAccessService.getData(DataTypes.RESERVATIONS);
		JsonArray confirmedArr = jsonObject.getAsJsonArray("Confirmed");
		JsonArray archiveArr = jsonObject.getAsJsonArray("Archive");

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
		dataAccessService.setData(DataTypes.RESERVATIONS, jsonObject);
	
	}
}
