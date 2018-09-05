package hotel.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hotel.credit.CreditCard;
import hotel.utils.IOUtils;

public class Hotel {
	
	private Map<Integer, Guest> guests;
	public Map<RoomType, Map<Integer,Room>> roomsByType;
	public Map<Long, Booking> bookingsByConfirmationNumber;
	public Map<Integer, Booking> activeBookingsByRoomId;
	
	
	public Hotel() {
		guests = new HashMap<>();
		roomsByType = new HashMap<>();
		for (RoomType rt : RoomType.values()) {
			Map<Integer, Room> rooms = new HashMap<>();
			roomsByType.put(rt, rooms);
		}
		bookingsByConfirmationNumber = new HashMap<>();
		activeBookingsByRoomId = new HashMap<>();
	}

	
	public void addRoom(RoomType roomType, int id) {
		IOUtils.trace("Hotel: addRoom");
		for (Map<Integer, Room> rooms : roomsByType.values()) {
			if (rooms.containsKey(id)) {
				throw new RuntimeException("Hotel: addRoom : room number already exists");
			}
		}
		Map<Integer, Room> rooms = roomsByType.get(roomType);
		Room room = new Room(id, roomType);
		rooms.put(id, room);
	}

	
	public boolean isRegistered(int phoneNumber) {
		return guests.containsKey(phoneNumber);
	}

	
	public Guest registerGuest(String name, String address, int phoneNumber) {
		if (guests.containsKey(phoneNumber)) {
			throw new RuntimeException("Phone number already registered");
		}
		Guest guest = new Guest(name, address, phoneNumber);
		guests.put(phoneNumber, guest);		
		return guest;
	}

	
	public Guest findGuestByPhoneNumber(int phoneNumber) {
		Guest guest = guests.get(phoneNumber);
		return guest;
	}

	
	public Booking findActiveBookingByRoomId(int roomId) {
		Booking booking = activeBookingsByRoomId.get(roomId);;
		return booking;
	}


	public Room findAvailableRoom(RoomType selectedRoomType, Date arrivalDate, int stayLength) {
		IOUtils.trace("Hotel: checkRoomAvailability");
		Map<Integer, Room> rooms = roomsByType.get(selectedRoomType);
		for (Room room : rooms.values()) {
			IOUtils.trace(String.format("Hotel: checking room: %d",room.getId()));
			if (room.isAvailable(arrivalDate, stayLength)) {
				return room;
			}			
		}
		return null;
	}

	
	public Booking findBookingByConfirmationNumber(long confirmationNumber) {
		return bookingsByConfirmationNumber.get(confirmationNumber);
	}

	
	public long book(Room room, Guest guest, 
			Date arrivalDate, int stayLength, int occupantNumber,
			CreditCard creditCard) {
		/*// TODO Auto-generated method stub
		Booking object was created using the parameter provided using room.book() method.
		The confirmation number was generated using the booking.getConfirmationNumber() method.
		Once Booking object and confirmation number is generated, the values are sotred in 
		bookingByConfirmationNumber
		*/
		Booking booking = room.book(guest,arrivalDate,stayLength, occupantNumber,creditCard);
		long confirmationNumber = booking.getConfirmationNumber();
		bookingsByConfirmationNumber.put(confirmationNumber, booking);
		return confirmationNumber;		
	}

	
	public void checkin(long confirmationNumber) {
		/*// TODO Auto-generated method stub
		confirmationNumber was looked in the hashmap called bookingByConfirmationNumber.
		If key matches confirmation number then the Booking object is extracted from Map.
		Room id is extracted from the Booking object and the values are stored
		in a hashmap that take roomId as key and Booking object as value called 
		activeBookingsByRoomId. The status of booking was changed by calling booking.checkIn().
		If confirmationNumber does not match then a RuntimeException is thrown with message
		regarding "Booking not found." error message. 
		*/
		if(bookingByConfirmationNumber.containsKey(confirmationNumber)){
			Booking booking = bookingByConfirmationNumber.key(confirmationNumber);
			int roomId = booking.getRoomID();
			booking.checkIn();
			activeBookingsByRoomId.put(roomId, booking);
		}
		else{
			RuntimeException bookingNotFound = new RuntimeException("Booking not found");
			throw bookingNotFound;
		}
	}


	public void addServiceCharge(int roomId, ServiceType serviceType, double cost) {
		// TODO Auto-generated method stub
	}

	
	public void checkout(int roomId) {
		// TODO Auto-generated method stub
	}


}
