package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.exceptions.BadLoginException;
import com.google.gson.JsonObject;
import com.models.User;
import com.models.enums.DataTypes;
import com.models.enums.Role;
import com.service.AuthService;
import com.service.DataAccess;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

	@Mock
	private DataAccess dataAccessService;

	@InjectMocks
	private AuthService authService;
	private JsonObject mockData;

	@Before
	public void setUp() throws Exception {
		mockData = new JsonObject();
		JsonObject userObject = new JsonObject();
		userObject.addProperty("password", "validPassword");
		userObject.addProperty("role", Role.GUEST.toString());
		mockData.add("validUser", userObject);
	}

	@Test
	public void testLoginSuccess() throws IOException, BadLoginException {
		when(dataAccessService.getData(DataTypes.USERS)).thenReturn(mockData);

		User user = authService.login("validUser", "validPassword");

		assertNotNull(user);
		assertEquals("ValidUser", user.getUserName());
		assertEquals(Role.GUEST, user.getRole());
	}

	@Test
	public void testLoginFailed() throws IOException, BadLoginException {
		when(dataAccessService.getData(DataTypes.USERS)).thenReturn(mockData);

		assertThrows(BadLoginException.class, () -> {
			authService.login("validUser", "invalidPassword");
		});
		assertThrows(BadLoginException.class, () -> {
			authService.login("invalidUser", "validPassword");
		});
	}

}
