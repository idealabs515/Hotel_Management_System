package hotel.checkin;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;

import hotel.entities.Guest;
import hotel.entities.Hotel;
import hotel.entities.Room;
import hotel.entities.RoomType;

public class CheckinCTLTest {

	@Test 
	public void testCheckinConfirmed() throws ParseException {
		
		
		
		 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	     Date date = format.parse("30-12-2018");
	     Hotel htl = new Hotel();
	     CreditCard card = new CreditCard(CreditCardType.VISA, 11111, 123);
	     htl.addRoom(RoomType.SINGLE, 101);   
	    htl.registerGuest("Muhammad Ahmed Shoaib", "Fawkner", 123);
	   Room room = htl.findAvailableRoom(RoomType.SINGLE, date, 2);
	   Guest guest = htl.findGuestByPhoneNumber(123);
	   long cnfrmNo = htl.book(room, guest, date, 2, 1, card);
	   
	   CheckinCTL checkin = new CheckinCTL(htl);
	   checkin.confirmationNumberEntered(cnfrmNo);
	   checkin.checkInConfirmed(true);
	   
	   assertTrue(checkin.isStateCompleted());


		
	}

}
