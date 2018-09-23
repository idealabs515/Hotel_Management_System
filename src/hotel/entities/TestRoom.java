package hotel.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;

class TestRoom {
	
//Author: Muhammad Ahmed Shoaib
//11628053
//


	@Test
	void testBook() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	     Date date = format.parse("25-11-2018");
		Room room = new Room(101,RoomType.SINGLE);
		Guest guest = new Guest("Muhammad Ahmed Shoaib", "Fawkner", 123456);
		CreditCard card = new CreditCard(CreditCardType.VISA, 11111, 123);
		
		Booking booking = room.book(guest,date, 2, 1, card);
		assertEquals(false,room.isAvailable(date,2));
	}
	
	@Test
	void testCheckin() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	     Date date = format.parse("25-11-2018");
		
		Room room = new Room(101,RoomType.SINGLE);
		Guest guest = new Guest("Muhammad Ahmed Shoaib", "Fawkner", 123456);
		CreditCard card = new CreditCard(CreditCardType.VISA, 11111, 123);
		Booking booking = room.book(guest,date, 2, 1, card);
		
		booking.checkIn();
		
		assertEquals(true, booking.isCheckedIn());
		assertEquals(false, room.isReady());
		
	}
	
	@Test
	void testCheckout() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	     Date date = format.parse("25-11-2018");
		Room room = new Room(101,RoomType.SINGLE);
		Guest guest = new Guest("Muhammad Ahmed Shoaib", "Fawkner", 123456);
		CreditCard card = new CreditCard(CreditCardType.VISA, 11111, 123);
		Booking booking = room.book(guest,date, 2, 1, card);
		booking.checkIn();
		booking.checkOut();
		assertEquals(true, room.isReady());
		assertEquals(true, booking.isCheckedOut());
		
	}

}
