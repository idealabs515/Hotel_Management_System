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
		//check if the room available before booking
		if (room.isAvailable(arrivalDate, stayLength)) {
			Booking booking = room.book(guest, arrivalDate, stayLength, occupantNumber, creditCard);
			//handle the case if the method returns null
			if (booking != null) {
				//get the confirmation number from booking
				long confirmationNumber = booking.getConfirmationNumber();
				//add booking to bookingsByConfirmationNumber map with confirmationNumber key
				bookingsByConfirmationNumber.put(confirmationNumber, booking);
				//return the confirmationNumber
				return confirmationNumber; 
			} else 
				return 0L;
		}
		
		return 0L;	
	}

	
	public void checkin(long confirmationNumber) {
		//get booking from bookingsByConfirmationNumber map with confirmationNumber key
		Booking booking = bookingsByConfirmationNumber.get(confirmationNumber);
		
		//if there is no booking then throw exception
		if (booking == null) {
			throw new RuntimeException("Booking could not be found");
		}
		//get roomId from booking
		int roomId = booking.getRoomId();
		//checkin the booking
		booking.checkIn();
		//add booking to activeBookingsByRoomId map with roomId key
		activeBookingsByRoomId.put(roomId, booking);
	}


	public void addServiceCharge(int roomId, ServiceType serviceType, double cost) {	
		//get booking from bookingsByConfirmationNumber map with confirmationNumber key
		Booking booking = activeBookingsByRoomId.get(roomId);
		//if there is no booking then throw exception
		if (booking == null) {
			throw new RuntimeException("No active booking for room");
		}
		//add service change to found booking
		booking.addServiceCharge(serviceType, cost);
	}

	
	public void checkout(int roomId) {
		//get booking from bookingsByConfirmationNumber map with confirmationNumber key
		Booking booking = activeBookingsByRoomId.get(roomId);
		//if there is no booking then throw exception
		if (booking == null) {
			throw new RuntimeException("No active booking for room");
		}
		//checkout for found booking
		booking.checkOut();
		//remove booking from activeBookingsByRoomId map
		activeBookingsByRoomId.remove(roomId);
	}


}
