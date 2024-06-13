package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AuthServiceTest.class, PricingServiceImplTest.class, ReportsServiceImplTest.class,
		ReservationServiceTest.class, RoomServiceImplTest.class })
public class AllTests {

}
