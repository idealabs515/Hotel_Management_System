package hotel.entities;
/**
 * The following JUnit testing is used to test a specific method which is independent and unit tested.
 * @author  ChittyVaishnav
 * @studentID 11639078
 */
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
//The class tests the functionality of check-Out
public class BookingCheckOutTestCase {

	@Test
	public void test() {
		//Creating all the mock objects needed for testing
		Guest newGuest = new Guest("Vaishnav Reddy","9 Gooble Street",789328990);
		Room newRoom = new Room(101,RoomType.SINGLE);
		Date currentDate = new Date();
		CreditCard cardCredentials = new CreditCard(CreditCardType.VISA,10001,543);
		Booking newBooking = new Booking(newGuest, newRoom, currentDate, 1, 1, cardCredentials);
		newBooking.checkIn();
		newBooking.checkOut();
		//Checking the state after checking out
	assertEquals(true,newBooking.isCheckedOut());
	//Checking the state of room as available
	assertEquals(true,newRoom.isReady());
	}

}

