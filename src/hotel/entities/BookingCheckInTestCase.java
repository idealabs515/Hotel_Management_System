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
//The class tests check-in functionality of booking class
public class BookingCheckInTestCase {



	@Test
	public void test() {
		//Creating all the mock objects needed for testing
		Guest newGuest = new Guest("Vaishnav Reddy","9 Gooble Street",789328990);
		Room newRoom = new Room(101,RoomType.SINGLE);
		//Creates a current date
		Date currentDate = new Date();
		CreditCard cardCredentials = new CreditCard(CreditCardType.VISA,10001,543);
		Booking newBooking = new Booking(newGuest, newRoom, currentDate, 1, 1, cardCredentials);
//Calling the method that need to be tested
		newBooking.checkIn();
		//Checking the state after calling the method
	assertEquals(true,newBooking.isCheckedIn());
	}

}

