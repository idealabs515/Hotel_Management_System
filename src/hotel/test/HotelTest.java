package hotel.test;

import org.junit.After; 
import org.junit.Before; 
import org.junit.Test; 

public class HotelTest {

	
	@Before 
    public void setUp() throws Exception {
		
	}
	
	@After 
    public void tearDown() throws Exception {

	}
	
	@Test 
    public void testBook() throws Exception { 

	}
	
	@Test 
    public void testCheckin() { 

	}
	
	@Test 
    public void testAddServiceCharge() { 

	}
	
	@Test 
    public void testCheckout() { 

	}
	
	@Test(expected = RuntimeException.class)
    public void testCheckinNoBooking() { 

	}
	
	@Test(expected = RuntimeException.class)
    public void testAddServiceChargeNotCheckedIn() { 

	}
	
	@Test(expected = RuntimeException.class)
    public void testCheckoutNotCheckedIn() { 

	}
}