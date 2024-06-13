package tests;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.exceptions.NoRoomAvailableException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.models.Guest;
import com.models.Reservation;
import com.models.User;
import com.models.enums.DataTypes;
import com.service.DataAccessInterface;
import com.service.PricingServiceInterface;
import com.service.ReportsServiceInterface;
import com.service.ReservationService;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

	@Mock
	private DataAccessInterface dataAccessService;

	@Mock
	private PricingServiceInterface pricingService;

	@Mock
	private ReportsServiceInterface reportsService;

	@InjectMocks
	private ReservationService reservationService;

	private JsonObject mockData;

	@Before
	public void setUp() throws Exception {
		JsonObject reservation = new JsonObject();
		reservation.addProperty("checkInDate", "2024-06-06");
		reservation.addProperty("checkOutDate", "2024-06-13");
		reservation.addProperty("creationDate", "2024-05-27");
		reservation.addProperty("roomType", "two-bedded+one bed");
		reservation.addProperty("price", 2520.0);
		reservation.addProperty("comment", "");
		reservation.addProperty("status", "PENDING");
		JsonObject user = new JsonObject();
		user.addProperty("username", "testUser");
		user.addProperty("role", "GUEST");
		reservation.add("guest", user);

		mockData = new JsonObject();
		JsonArray pending = new JsonArray();
		JsonArray confirmed = new JsonArray();
		JsonArray rejected = new JsonArray();
		JsonArray cancelled = new JsonArray();
		JsonArray archive = new JsonArray();

		pending.add(reservation);
		reservation.addProperty("status", "REJECTED");
		rejected.add(reservation);

		JsonObject room = new JsonObject();
		room.addProperty("ID", 1);
		room.addProperty("type", "one bed");
		reservation.add("room", room);

		reservation.addProperty("status", "CONFIRMED");
		confirmed.add(reservation);
		reservation.addProperty("status", "CANCELLED");
		cancelled.add(reservation);
		reservation.addProperty("status", "ARCHIVE");
		archive.add(reservation);

		mockData.add("Pending", pending);
		mockData.add("Confirmed", confirmed);
		mockData.add("Rejected", rejected);
		mockData.add("Cancelled", cancelled);
		mockData.add("Archive", archive);

	}

	@Test
	public void testGetReservationsGuest() throws IOException {
		when(dataAccessService.getData(DataTypes.RESERVATIONS)).thenReturn(mockData);
		User guest = new Guest(null, null, null, null, null, null, "testUser", "password");
		Reservation[] ret = reservationService.getReservations(guest);

		assertTrue(ret.length == 5);
	}

	@Test
	public void testProccessReservationSuccess() throws IOException, NoRoomAvailableException {
		JsonObject dataRooms = new JsonObject();
		JsonObject rooms = new JsonObject();
		JsonObject oneBed = new JsonObject();
		JsonObject room = new JsonObject();
		room.addProperty("ID", 1);
		room.addProperty("type", "one bed");
		oneBed.add("1", room);
		rooms.add("one bed", oneBed);
		dataRooms.add("rooms", rooms);
		dataRooms.addProperty("next ID", 2);
		JsonArray roomTypes = new JsonArray();
		roomTypes.add("one bed");
		dataRooms.add("roomTypes", roomTypes);

		Reservation reservation = new Reservation("2024-01-01", "2024-01-04", "one bed", new String[] {}, null);

		when(dataAccessService.getData(DataTypes.ROOMS)).thenReturn(dataRooms);
		when(dataAccessService.getData(DataTypes.RESERVATIONS)).thenReturn(mockData);
		doNothing().when(dataAccessService).setData(DataTypes.RESERVATIONS, mockData);
		doNothing().when(reportsService).reservationConfirmed(any());

		reservationService.proccessReservation(reservation);
		verify(dataAccessService).setData(DataTypes.RESERVATIONS, mockData);
	}

	@Test
	public void testProccessReservationFailed() throws IOException {
		JsonObject dataRooms = new JsonObject();
		JsonObject rooms = new JsonObject();
		JsonObject oneBed = new JsonObject();
		JsonObject room = new JsonObject();
		room.addProperty("ID", 1);
		room.addProperty("type", "one bed");
		oneBed.add("1", room);
		rooms.add("one bed", oneBed);
		dataRooms.add("rooms", rooms);
		dataRooms.addProperty("next ID", 2);
		JsonArray roomTypes = new JsonArray();
		roomTypes.add("one bed");
		dataRooms.add("roomTypes", roomTypes);

		Reservation reservation = new Reservation("2024-01-01", "2024-1-04", "two bed", new String[] {}, null);

		when(dataAccessService.getData(DataTypes.ROOMS)).thenReturn(dataRooms);

		assertThrows(NoRoomAvailableException.class, () -> {
			reservationService.proccessReservation(reservation);
		});
	}

	@Test
	public void testProccessReservationNoRoomFailed() throws IOException {
		JsonObject dataRooms = new JsonObject();
		JsonObject rooms = new JsonObject();
		JsonObject oneBed = new JsonObject();
		JsonObject room = new JsonObject();
		room.addProperty("ID", 1);
		room.addProperty("type", "one bed");
		oneBed.add("1", room);
		rooms.add("one bed", oneBed);
		dataRooms.add("rooms", rooms);
		dataRooms.addProperty("next ID", 2);
		JsonArray roomTypes = new JsonArray();
		roomTypes.add("one bed");
		dataRooms.add("roomTypes", roomTypes);

		Reservation reservation = new Reservation("2024-06-06", "2024-06-08", "one bed", new String[] {}, null);

		when(dataAccessService.getData(DataTypes.ROOMS)).thenReturn(dataRooms);
		when(dataAccessService.getData(DataTypes.RESERVATIONS)).thenReturn(mockData);
		assertThrows(NoRoomAvailableException.class, () -> {
			reservationService.proccessReservation(reservation);
		});
	}
}
