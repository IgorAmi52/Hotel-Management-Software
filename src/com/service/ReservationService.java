package com.service;

import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.Reservation;

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
}
