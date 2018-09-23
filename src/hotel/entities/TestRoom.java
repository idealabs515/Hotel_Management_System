package hotel.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;

class TestRoom {
/*	
Author: Muhammad Ahmed Shoaib
11628053
This is a test class for testing the individual methods of Room.java class
the methods which we needed to implemnet were book, checkin and checkout.
testing each method individually is known as unit testing.
*/


	@Test // testing the book method for Room class
	void testBook() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	     Date date = format.parse("25-11-2018"); // to store the date in a specific format
		Room room = new Room(101,RoomType.SINGLE); // creating room object
		Guest guest = new Guest("Muhammad Ahmed Shoaib", "Fawkner", 123456); //creating guest object
		CreditCard card = new CreditCard(CreditCardType.VISA, 11111, 123); // creating credit card object
		//calling the method of room class room.book and specifying the detials 
		Booking booking = room.book(guest,date, 2, 1, card);
		assertEquals(false,room.isAvailable(date,2)); 
		//the room should not be available for the dates which have been already booked so it should return false
	
	}
	
	@Test //testing Checkin method for Room class
	void testCheckin() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	     Date date = format.parse("25-11-2018"); //to store date in a specific format
		
		Room room = new Room(101,RoomType.SINGLE); //creating room object
		Guest guest = new Guest("Muhammad Ahmed Shoaib", "Fawkner", 123456); //creating guest object
		CreditCard card = new CreditCard(CreditCardType.VISA, 11111, 123); //creating creditcard object
		Booking booking = room.book(guest,date, 2, 1, card); //making a booking for that room
		//In order to checkin the room you cannot directly call it from the Room class because 
		//this method is dependent on booking. so we have to call it from booking.checkin method 
		//when this method is invoked room.checkin is automatically invoked according to the class definition.
		booking.checkIn();
		
		assertEquals(true, booking.isCheckedIn()); // after calling method booking state should be checked in
		assertEquals(false, room.isReady()); // after calling method room state should not be ready. means its OCCUPIED
		
	}
	
	@Test //testing Checkout method for Room class
	void testCheckout() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	     Date date = format.parse("25-11-2018"); // to store date in a specific format
		Room room = new Room(101,RoomType.SINGLE); //creating room object
		Guest guest = new Guest("Muhammad Ahmed Shoaib", "Fawkner", 123456); //creating guest object
		CreditCard card = new CreditCard(CreditCardType.VISA, 11111, 123); // creating creditcard object
		Booking booking = room.book(guest,date, 2, 1, card); // making a booking
		booking.checkIn(); // to test checkout the room should be checked in first.
		booking.checkOut(); //checking out the booking, again, the room.checkout cannot be called 
		//directly as it is dependent upon the booking so booking.checkout should automatically envoke room.checkout.
		assertEquals(true, room.isReady()); //after calling method room state should be ready
		assertEquals(true, booking.isCheckedOut()); //after calling method booking state should be checked out.
		
	}

}
