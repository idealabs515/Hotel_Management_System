package hotel.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType; 

public class HotelTest {
	
	Hotel hotel;
	Date date;
	int stayLength = 5;
	Room room;
	Guest guest;
	CreditCard card;
	
	@Before 
    public void setUp() throws Exception {
		hotel = new Hotel();
		hotel.addRoom(RoomType.SINGLE, 101);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		date = format.parse("01-01-2019");
		
		room = hotel.findAvailableRoom(RoomType.SINGLE, date, stayLength);
		guest = new Guest("Huseyin", "Caliskan", 424043907);
		card = new CreditCard(CreditCardType.VISA, 2, 123);	
	}
	
	@After 
    public void tearDown() throws Exception {
		card = null;
		guest = null;
		room = null;
		hotel = null;
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