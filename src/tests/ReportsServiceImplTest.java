package tests;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.gson.JsonObject;
import com.models.enums.DataTypes;
import com.service.DataAccessInterface;
import com.service.ReportsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReportsServiceImplTest {

	@Mock
	private DataAccessInterface dataAccessService;

	@Spy
	@InjectMocks
	private ReportsServiceImpl reportsService;

	private JsonObject mockData;

	@Before
	public void setUp() throws Exception {
		mockData = new JsonObject();
		mockData.addProperty("lastDate", "2024-01-03");

		JsonObject firstDate = new JsonObject();
		JsonObject secondDate = new JsonObject();
		JsonObject thirdDate = new JsonObject();

		firstDate.addProperty("Confirmed", 3);
		firstDate.addProperty("Rejected", 3);
		firstDate.addProperty("Cancelled", 3);
		secondDate.addProperty("Confirmed", 3);
		secondDate.addProperty("Rejected", 3);
		secondDate.addProperty("Cancelled", 3);
		thirdDate.addProperty("Confirmed", 3);
		thirdDate.addProperty("Rejected", 3);
		thirdDate.addProperty("Cancelled", 3);

		JsonObject rooms = new JsonObject();
		rooms.addProperty("1", 100);
		rooms.addProperty("2", 100);

		firstDate.add("rooms", rooms);
		secondDate.add("rooms", rooms);
		thirdDate.add("rooms", rooms);

		JsonObject roomTypes = new JsonObject();
		roomTypes.addProperty("one bed", 100);
		roomTypes.addProperty("two bed", 100);

		firstDate.add("roomTypes", roomTypes);
		secondDate.add("roomTypes", roomTypes);
		thirdDate.add("roomTypes", roomTypes);

		JsonObject cleaners = new JsonObject();
		cleaners.addProperty("cleaner1", 2);
		cleaners.addProperty("cleaner2", 2);
		firstDate.add("cleaners", cleaners);
		secondDate.add("cleaners", cleaners);
		thirdDate.add("cleaners", cleaners);

		mockData.add("2024-01-01", firstDate);
		mockData.add("2024-01-02", secondDate);
		mockData.add("2024-01-03", thirdDate);
	}

	@Test
	public void testCreateMissingReports() throws IOException {
		doNothing().when(dataAccessService).setData(DataTypes.REPORTS, mockData);
		when(dataAccessService.getData(DataTypes.REPORTS)).thenReturn(mockData);

		reportsService.createMissingReports("2024-01-05");

		assertTrue(mockData.size() == 6);
		verify(dataAccessService).setData(DataTypes.REPORTS, mockData);

	}

	@Test
	public void testGetYearlyRoomTypeStatistics() throws IOException {
		when(dataAccessService.getData(DataTypes.REPORTS)).thenReturn(mockData);
		Map<String, double[]> ret = reportsService.getYearlyRoomTypeStatistics(2024,
				new String[] { "one bed", "two bed" });
		assertTrue(ret.size() == 3);
		assertTrue(ret.get("one bed")[0] == 300);
	}

	@Test
	public void testgetCleanersActivityInLastThirtyDays() throws IOException {
		when(dataAccessService.getData(DataTypes.REPORTS)).thenReturn(mockData);
		Map<String, Integer> ret = reportsService.getCleanersActivityInLastThirtyDays();
		assertTrue(ret.size() == 0);

	}

	@Test
	public void testGetReservationStatusesCreatedInLastThirtyDays() throws IOException {
		when(dataAccessService.getData(DataTypes.RESERVATIONS)).thenReturn(new JsonObject());
		Map<String, Integer> ret = reportsService.getReservationStatusesCreatedInLastThirtyDays();

		assertTrue(ret.size() == 4);
	}

}
