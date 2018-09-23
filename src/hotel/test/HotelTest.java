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
		//create prerequisite components 
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
		//remove references for the created global objects
		card = null;
		guest = null;
		room = null;
		hotel = null;
	}
	
	@Test 
    public void testBook() throws Exception { 
		//let the book method be tested
		long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
		long expectedConfirmationNumber = 1012019101L; //01012019101 for ddMMyyyyXXX
		//compare the confirmationNumber and the expected value according to explanation
		assertEquals(expectedConfirmationNumber, confirmationNumber);
		
		//check if booking exists after calling book method
		boolean bookingExist = (hotel.findBookingByConfirmationNumber(confirmationNumber) != null);
		assertEquals(bookingExist, true);
		
		//check if the room still available after calling book method
		boolean roomNotAvailable = room.isAvailable(date, stayLength);
		assertEquals(roomNotAvailable, false);
	}
	
	@Test 
    public void testCheckin() { 
		//make the booking before checkin
		long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
		//checkin with the confirmationNumber
		hotel.checkin(confirmationNumber);
		
		//check if any active booking can be found with the confirmationNumber
		Booking booking = hotel.findActiveBookingByRoomId(room.getId());
		boolean bookingExist = (booking != null);
		assertEquals(bookingExist, true);

		//check if the booking has the state of checked in
		boolean isCheckedIn = (booking.isCheckedIn());
		assertEquals(isCheckedIn, true);
	}
	
	@Test 
    public void testAddServiceCharge() { 
		//make the booking before checkin
		long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
		//checkin with the confirmationNumber
		hotel.checkin(confirmationNumber);
		//add BAR_FRIDGE service to the room checked in
		hotel.addServiceCharge(room.getId(), ServiceType.BAR_FRIDGE, 20.00);
		
		//check if any active booking can be found with the confirmationNumber
		Booking booking = hotel.findActiveBookingByRoomId(room.getId());
		
		//check if the added service can be found in booking as well
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
		//make the booking before checkin
		long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
		//checkin with the confirmationNumber
		hotel.checkin(confirmationNumber);
		//checkout with the room id
		hotel.checkout(room.getId());
		
		//check if booking exists after calling checkout method
		Booking booking = hotel.findBookingByConfirmationNumber(confirmationNumber);
		//check if the booking has the state of checked out
		boolean isCheckedOut = (booking.isCheckedOut());
		assertEquals(isCheckedOut, true);	
	}
	
	@Test(expected = RuntimeException.class)
    public void testCheckinNoBooking() { 
		//test checkin method with no booking
		hotel.checkin(1);
	}
	
	@Test(expected = RuntimeException.class)
    public void testAddServiceChargeNotCheckedIn() { 
		//test addServiceCharge method with not checked in
		hotel.addServiceCharge(1, ServiceType.RESTAURANT, 10);
	}
	
	@Test(expected = RuntimeException.class)
    public void testCheckoutNotCheckedIn() { 
		//test checkout method with not checked in
		hotel.checkout(1);
	}
}
