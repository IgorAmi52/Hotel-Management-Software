package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import com.exceptions.BadLoginException;
import com.exceptions.ElementAlreadyExistsException;
import com.google.gson.JsonObject;
import com.models.Guest;
import com.models.Staff;
import com.models.User;
import com.models.enums.DataTypes;
import com.models.enums.Role;
import com.service.AuthService;
import com.service.DataAccessInterface;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

	@Mock
	private DataAccessInterface dataAccessService;

	@InjectMocks
	private AuthService authService;

	private JsonObject mockData;

	@Before
	public void setUp() throws Exception {
		mockData = new JsonObject();

		JsonObject validGuest = new JsonObject();
		validGuest.addProperty("password", "validPassword");
		validGuest.addProperty("username", "validGuest");
		validGuest.addProperty("role", Role.GUEST.toString());
		mockData.add("validGuest", validGuest);

		JsonObject validStaff = new JsonObject();
		validStaff.addProperty("password", "validPassword");
		validStaff.addProperty("username", "validAdmin");
		validStaff.addProperty("role", Role.ADMIN.toString());
		mockData.add("validAdmin", validStaff);
	}

	@Test
	public void testLoginSuccess() throws IOException, BadLoginException {
		when(dataAccessService.getData(DataTypes.USERS)).thenReturn(mockData);

		User user = authService.login("validGuest", "validPassword");

		assertNotNull(user);
		assertEquals("validGuest", user.getUserName());
		assertEquals(Role.GUEST, user.getRole());
	}

	@Test
	public void testLoginFailed() throws IOException, BadLoginException {
		when(dataAccessService.getData(DataTypes.USERS)).thenReturn(mockData);

		assertThrows(BadLoginException.class, () -> {
			authService.login("validGuest", "invalidPassword");
		});
		assertThrows(BadLoginException.class, () -> {
			authService.login("invalidGuest", "validPassword");
		});
	}

	@Test
	public void testRegisterUserSuccess() throws IOException, ElementAlreadyExistsException {
		when(dataAccessService.getData(DataTypes.USERS)).thenReturn(mockData);
		doNothing().when(dataAccessService).setData(any(DataTypes.class), any(JsonObject.class));

		User guest = new Staff("validStaff", "validStaff", "M", "1999-01-01", "123456789", "validStaff",
				"validPassword", null, 1, 1, Role.ADMIN);
		authService.registerUser(guest);

		verify(dataAccessService).setData(eq(DataTypes.USERS), any(JsonObject.class));

	}

	@Test
	public void testRegisterUserFailed() throws IOException, ElementAlreadyExistsException {
		when(dataAccessService.getData(DataTypes.USERS)).thenReturn(mockData);

		User guest = new Guest(null, null, null, null, null, null, "validGuest", "validPassword");

		assertThrows(ElementAlreadyExistsException.class, () -> {
			authService.registerUser(guest);
		});
	}
}
