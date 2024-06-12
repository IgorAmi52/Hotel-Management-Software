
package com.service;

import java.io.IOException;

import com.exceptions.ElementAlreadyExistsException;
import com.models.Room;
import com.models.User;

public interface RoomServiceInterface {

	int getNextRoomID() throws IOException;

	void addRoom(Room room) throws IOException;

	void deleteRoom(Room room) throws IOException;

	void addRoomType(String type) throws IOException;

	void deleteRoomType(String name) throws IOException;

	String[] getRoomTypes() throws IOException;

	Room[] getRooms() throws IOException;

	void addAddService(String name) throws IOException, ElementAlreadyExistsException;

	void deleteAddService(String name) throws IOException;

	String[] getRoomIDs() throws IOException;

	String[][] getAddServices() throws IOException;

	String[] getAddServicesArr();

	void checkInRoom(Room room) throws IOException;

	void checkOutRoom(Room room) throws IOException;

	void cleanRoom(Room room, User cleaner) throws IOException;

	Room[] getCleanersRooms(User cleaner) throws IOException;
}
