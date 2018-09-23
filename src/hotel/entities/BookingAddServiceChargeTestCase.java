package hotel.testcases;
/**
 * The following JUnit testing is used to test a specific method which is independent and unit tested.
 * @author  ChittyVaishnav
 * @studentID 11639078
 */
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.Booking;
import hotel.entities.Guest;
import hotel.entities.Room;
import hotel.entities.RoomType;
import hotel.entities.ServiceCharge;
import hotel.entities.ServiceType;

//The class test the functionality of add service charge
public class BookingAddServiceChargeTestCase {

	@Test
	public void test() {
		//Creating all the mock objects needed for testing
				Guest newGuest = new Guest("Vaishnav Reddy","9 Gooble Street",789328990);
				Room newRoom = new Room(101,RoomType.SINGLE);
				ServiceCharge newServiceCharge = new ServiceCharge(ServiceType.ROOM_SERVICE, 1000.50);
				Date currentDate = new Date();
				CreditCard cardCredentials = new CreditCard(CreditCardType.VISA,10001,543);
				Booking newBooking = new Booking(newGuest, newRoom, currentDate, 1, 1, cardCredentials);
		//Calling the method that need to be tested
				newBooking.checkIn();
		newBooking.addServiceCharge(ServiceType.ROOM_SERVICE, 1000.50);
		//Calling the list to check the contents in the list
		List<ServiceCharge> l1 = newBooking.getCharges();
		//Only single object added so checking for the single object in a loop.
		for(ServiceCharge s:l1)
		{
			assertEquals(newServiceCharge.getType(),s.getType());
			assertEquals(true,s.getCost()==newServiceCharge.getCost());
			
		}
	}

}

