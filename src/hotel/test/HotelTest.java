package hotel.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Booking;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType;
import hotel.entities.ServiceCharge;
import hotel.entities.ServiceType; 

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
		long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
		long expectedConfirmationNumber = 1012019101L; //01012019101 for ddMMyyyyXXX
		assertEquals(expectedConfirmationNumber, confirmationNumber);
		
		boolean bookingExist = (hotel.findBookingByConfirmationNumber(confirmationNumber) != null);
		assertEquals(bookingExist, true);
		
		boolean roomNotAvailable = room.isAvailable(date, stayLength);
		assertEquals(roomNotAvailable, false);
	}
	
	@Test 
    public void testCheckin() { 
		long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
		
		hotel.checkin(confirmationNumber);
		
		Booking booking = hotel.findActiveBookingByRoomId(room.getId());
		
		boolean bookingExist = (booking != null);
		assertEquals(bookingExist, true);
		
		boolean isCheckedIn = (booking.isCheckedIn());
		assertEquals(isCheckedIn, true);
	}
	
	@Test 
    public void testAddServiceCharge() { 
		long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
		hotel.checkin(confirmationNumber);
		hotel.addServiceCharge(room.getId(), ServiceType.BAR_FRIDGE, 20.00);
		
		Booking booking = hotel.findActiveBookingByRoomId(room.getId());
		
		boolean isServicedded = false;
		for (ServiceCharge serviceCharge : booking.getCharges()) {
			if (serviceCharge.getType() == ServiceType.BAR_FRIDGE) {
				isServicedded = true;
				break;
			}
		}
		
		assertEquals(isServicedded, true);
	}
	
	@Test 
    public void testCheckout() { 
		long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
		hotel.checkin(confirmationNumber);
		hotel.checkout(room.getId());
		
		Booking booking = hotel.findBookingByConfirmationNumber(confirmationNumber);
		boolean isCheckedOut = (booking.isCheckedOut());
		assertEquals(isCheckedOut, true);	
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