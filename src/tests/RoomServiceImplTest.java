package tests;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.exceptions.ElementAlreadyExistsException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.models.Room;
import com.models.enums.DataTypes;
import com.service.DataAccessInterface;
import com.service.ReportsServiceInterface;
import com.service.RoomServiceImpl;
import com.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {

	@Mock
	private DataAccessInterface dataAccessService;
	@Mock
	private ReportsServiceInterface reportsService;
	@Mock
	private UserService userService;
	@Spy
	@InjectMocks
	private RoomServiceImpl roomService;

	private JsonObject mockDataRooms;
	private JsonObject mockDataAddServices;

	@Before
	public void setUp() throws Exception {

		mockDataRooms = new JsonObject();
		JsonObject rooms = new JsonObject();
		JsonObject oneBed = new JsonObject();
		JsonObject room = new JsonObject();
		room.addProperty("id", 1);
		room.addProperty("type", "one bed");
		oneBed.add("1", room);
		rooms.add("one bed", oneBed);
		mockDataRooms.add("rooms", rooms);
		mockDataRooms.addProperty("next ID", 2);
		JsonArray roomTypes = new JsonArray();
		roomTypes.add("one bed");
		mockDataRooms.add("roomTypes", roomTypes);

		mockDataAddServices = new JsonObject();
		JsonArray addServices = new JsonArray();
		addServices.add("breakfast");
		mockDataAddServices.add("services", addServices);

	}

	@Test
	public void testAddRoom() throws IOException {

		doNothing().when(dataAccessService).setData(DataTypes.ROOMS, mockDataRooms);
		when(dataAccessService.getData(DataTypes.ROOMS)).thenReturn(mockDataRooms);
		when(roomService.getNextRoomID()).thenReturn(2);

		Room room = new Room("two bed", 1);

		roomService.addRoom(room);

		verify(dataAccessService).setData(DataTypes.ROOMS, mockDataRooms);

	}

	@Test
	public void testDeleteRoom() throws IOException {

		doNothing().when(dataAccessService).setData(DataTypes.ROOMS, mockDataRooms);
		when(dataAccessService.getData(DataTypes.ROOMS)).thenReturn(mockDataRooms);

		Room room = new Room("one bed", 1);

		roomService.deleteRoom(room);

		verify(dataAccessService).setData(DataTypes.ROOMS, mockDataRooms);

	}

	@Test
	public void testGetRoomTypes() throws IOException {
		when(dataAccessService.getData(DataTypes.ROOMS)).thenReturn(mockDataRooms);
		String[] roomTypes = roomService.getRoomTypes();

		assertTrue(roomTypes.length == 1);
		assertTrue(roomTypes[0].equals("one bed"));
	}

	@Test
	public void testGetRooms() throws IOException {
		when(dataAccessService.getData(DataTypes.ROOMS)).thenReturn(mockDataRooms);
		Room[] rooms = roomService.getRooms();
		assertTrue(rooms.length == 1);
		assertTrue(rooms[0].getType().equals("one bed"));
	}

	@Test
	public void testAddAddServiceSuccess() throws IOException, ElementAlreadyExistsException {
		doNothing().when(dataAccessService).setData(DataTypes.ADD_SERVICES, mockDataAddServices);
		when(dataAccessService.getData(DataTypes.ADD_SERVICES)).thenReturn(mockDataAddServices);

		roomService.addAddService("lunch");
		verify(dataAccessService).setData(DataTypes.ADD_SERVICES, mockDataAddServices);
	}

	@Test
	public void testAddAddServiceFailure() throws IOException, ElementAlreadyExistsException {
		when(dataAccessService.getData(DataTypes.ADD_SERVICES)).thenReturn(mockDataAddServices);

		assertThrows(ElementAlreadyExistsException.class, () -> {
			roomService.addAddService("breakfast");
		});
	}

	@Test
	public void testGetAddServices() throws IOException {
		when(dataAccessService.getData(DataTypes.ADD_SERVICES)).thenReturn(mockDataAddServices);
		String[][] addServices = roomService.getAddServices();
		assertTrue(addServices.length == 1);
	}

}
