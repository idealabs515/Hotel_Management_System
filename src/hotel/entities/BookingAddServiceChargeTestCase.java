package hotel.testcases;

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

public class BookingAddServiceChargeTestCase {

	@Test
	public void test() {
		Room r1 = new Room(101,RoomType.SINGLE);
		ServiceCharge sc = new ServiceCharge(ServiceType.ROOM_SERVICE,1000.50);
		Booking booking = new Booking(new Guest("Vaishnav Reddy","9 Gooble Street",789328990), r1, new Date(), 2, 1, new CreditCard(CreditCardType.VISA,10001,543));
		booking.checkIn();
		booking.addServiceCharge(ServiceType.ROOM_SERVICE, 1000.50);
		List<ServiceCharge> l1 = booking.getCharges();
		for(ServiceCharge s:l1)
		{
			assertEquals(sc.getType(),s.getType());
			assertEquals(true,s.getCost()==sc.getCost());
			
		}
	}

}
