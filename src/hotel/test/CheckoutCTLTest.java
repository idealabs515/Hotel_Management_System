package hotel.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;

import hotel.checkout.CheckoutCTL;
import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Booking;
import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType; 

public class CheckoutCTLTest {
	Hotel hotel;
	Date date;
	int stayLength = 5;
	Room room;
	Guest guest;
	CreditCard card;
	CheckoutCTL checkoutCTL;
	
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

		checkoutCTL = new CheckoutCTL(hotel);
	}
	
	@After 
    public void tearDown() throws Exception {
		//remove references for the created global objects
		card = null;
		guest = null;
		room = null;
		hotel = null;
		checkoutCTL = null;
	}
	
	@Test 
    public void testCreditDetailsEntered() throws Exception {
		//change state to room to prevent user input
		checkoutCTL.setStateToRoom();
		//create a booking and checkin before testing creditDetailsEntered method
		long confirmationNumber = hotel.book(room, guest, date, stayLength, 1, card);
		hotel.checkin(confirmationNumber);
		//give the room id of the booking above
		checkoutCTL.roomIdEntered(room.getId());
		//accept the charges to continue testing
		checkoutCTL.chargesAccepted(true);
		//enter the credit card details
		checkoutCTL.creditDetailsEntered(card.getType(), card.getNumber(), card.getCcv());
		
		//Check the state of booking
		Booking booking = hotel.findBookingByConfirmationNumber(confirmationNumber);
		assertEquals(booking.isCheckedOut(), true);
		
		//Check the state of room
		assertEquals(room.isReady(), true);
		
		//Check if the booking exists in active bookings
		boolean bookingActive = (hotel.findActiveBookingByRoomId(room.getId()) != null);
		assertEquals(bookingActive, false);

	}
	
	@Test(expected = RuntimeException.class)
	public void testCreditDetailsEnteredIncorrectState() { 
		//check for the state in control class
		checkoutCTL.creditDetailsEntered(card.getType(), card.getNumber(), card.getCcv());
	}
	
}
