package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import com.exceptions.ElementAlreadyExistsException;
import com.exceptions.NoPricingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.models.Pricing;
import com.models.Reservation;
import com.models.enums.DataTypes;
import com.service.DataAccessInterface;
import com.service.PricingServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PricingServiceImplTest {

	@Mock
	private DataAccessInterface dataAccessService;

	@InjectMocks
	private PricingServiceImpl pricingService;

	private JsonObject mockData;

	@Before
	public void setUp() throws Exception {
		mockData = new JsonObject();
		JsonObject pricing = new JsonObject();
		pricing.addProperty("price", 100);
		pricing.addProperty("type", "one bed");
		pricing.addProperty("fromDate", "2021-01-01");
		pricing.addProperty("toDate", "2021-01-30");

		JsonArray pricingArr = new JsonArray();
		pricingArr.add(pricing);
		mockData.add("one bed", pricingArr);
	}

	@Test
	public void testGetPricing() throws IOException {

		when(dataAccessService.getData(DataTypes.PRICING)).thenReturn(mockData);
		Pricing[] prices = pricingService.getPricing(true, new String[] { "one bed" });

		assertEquals(1, prices.length);
		assertEquals("one bed", prices[0].getType());
		assertEquals("2021-01-01", prices[0].getFromDate());
		assertEquals("2021-01-30", prices[0].getToDate());
		double expectedPrice = 100.0;
		double actualPrice = prices[0].getPrice();
		assertEquals(expectedPrice, actualPrice, 0);

	}

	@Test
	public void testDeletePricing() throws IOException {
		Pricing pricing = new Pricing("one bed", 100.0, "2021-01-01", "2021-01-30");

		doNothing().when(dataAccessService).setData(any(DataTypes.class), any(JsonObject.class));
		when(dataAccessService.getData(DataTypes.PRICING)).thenReturn(mockData);
		pricingService.deletePricing(pricing);

		verify(dataAccessService).setData(eq(DataTypes.PRICING), any(JsonObject.class));

	}

	@Test
	public void testAddPricingSuccess() throws IOException, ElementAlreadyExistsException {
		Pricing pricing = new Pricing("one bed", 100.0, "2021-02-05", "2021-02-10");
		when(dataAccessService.getData(DataTypes.PRICING)).thenReturn(mockData);
		doNothing().when(dataAccessService).setData(any(DataTypes.class), any(JsonObject.class));
		pricingService.addPricing(pricing);
		verify(dataAccessService).setData(eq(DataTypes.PRICING), any(JsonObject.class));
	}

	@Test
	public void testAddPricingFailure() throws IOException, ElementAlreadyExistsException {
		Pricing pricing = new Pricing("one bed", 100.0, "2021-01-01", "2021-01-05");
		when(dataAccessService.getData(DataTypes.PRICING)).thenReturn(mockData);

		assertThrows(ElementAlreadyExistsException.class, () -> {
			pricingService.addPricing(pricing);
		});
	}

	@Test
	public void testCalculatePricingSuccess() throws IOException, NoPricingException {
		Reservation reservation = new Reservation("2021-01-02", "2021-01-05", "one bed", new String[] {}, null);
		when(dataAccessService.getData(DataTypes.PRICING)).thenReturn(mockData);
		double expectedPrice = 300.0;
		double actualPrice = pricingService.calculatePricing(reservation);
		assertEquals(expectedPrice, actualPrice, 0);
	}

	@Test
	public void testCalculatePricingNoPricingDefinedFailure() throws IOException {
		Reservation reservation = new Reservation("2021-01-02", "2021-01-05", "two bed", new String[] {}, null);
		when(dataAccessService.getData(DataTypes.PRICING)).thenReturn(mockData);
		assertThrows(NoPricingException.class, () -> {
			pricingService.calculatePricing(reservation);
		});
	}

	@Test
	public void testCalculatePricingNoPricingRangeDefinedFailure() throws IOException {
		Reservation reservation = new Reservation("2021-02-02", "2021-02-05", "one bed", new String[] {}, null);
		when(dataAccessService.getData(DataTypes.PRICING)).thenReturn(mockData);
		assertThrows(NoPricingException.class, () -> {
			pricingService.calculatePricing(reservation);
		});
	}
}
