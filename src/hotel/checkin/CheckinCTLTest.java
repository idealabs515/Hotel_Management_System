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
/*
 Muhammad Ahmed Shoaib
 11628053
 This is the test class created to do the integration testing of the Checkin Control class 
 the method which we are going to test is CheckinConfirmed method. since this method is dependent upon 
 a lot of other things so first we have to build up the scenario accordingly and then we can test the
 required method.
 */
		
		
		 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	     Date date = format.parse("30-12-2018"); //to specify the date and its format.
	     Hotel htl = new Hotel(); //creating hotel object.
	     CreditCard card = new CreditCard(CreditCardType.VISA, 11111, 123); //creating creditcard object
	     htl.addRoom(RoomType.SINGLE, 101);   //adding a room in hotel
	    htl.registerGuest("Muhammad Ahmed Shoaib", "Fawkner", 123); //registering the guest in hotel
	   Room room = htl.findAvailableRoom(RoomType.SINGLE, date, 2); //checking available room.
	   Guest guest = htl.findGuestByPhoneNumber(123); //storing guest in Guest object.
	   long cnfrmNo = htl.book(room, guest, date, 2, 1, card); //booking the room in hotel against the registered guest.
	   
	   CheckinCTL checkin = new CheckinCTL(htl); //creating CheckinCTL class object and passing hotel object.
	   checkin.confirmationNumberEntered(cnfrmNo); //to run checkInConfirmed method confirmationNumberEntered method must run before
	   checkin.checkInConfirmed(true); //once confirmationNumberEntered method is run checkinConfirmed should be passed true
	 //after running the method CheckinCTL status should be complete and CheckinUI state should also be complete
	   //we can see from the implementation of the control class that if one state changes the other one 
	   //also changes so we can test checkinCTL state and it should be COMPLETED.
	   assertTrue(checkin.isStateCompleted()); 
	
	}

}
