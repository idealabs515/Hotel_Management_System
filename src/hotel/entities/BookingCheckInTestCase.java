
package hotel.entities;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;

public class BookingCheckInTestCase {


	@Test
	public void test() {
	
Room r1 = new Room(101,RoomType.SINGLE);
		Booking booking = new Booking(new Guest("Vaishnav Reddy","9 Gooble Street",789328990), r1, new Date(), 2, 1, new CreditCard(CreditCardType.VISA,10001,543));
	booking.checkIn();
	assertEquals(true,booking.isCheckedIn());
	}

}
