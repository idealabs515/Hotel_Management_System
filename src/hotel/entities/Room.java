package hotel.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hotel.credit.CreditCard;
import hotel.utils.IOUtils;

public class Room {
	
	private enum State {READY, OCCUPIED}
	
	int id;
	RoomType roomType;
	List<Booking> bookings;
	State state;

	
	public Room(int id, RoomType roomType) {
		this.id = id;
		this.roomType = roomType;
		bookings = new ArrayList<>();
		state = State.READY;
	}
	

	public String toString() {
		return String.format("Room : %d, %s", id, roomType);
	}


	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return roomType.getDescription();
	}
	
	
	public RoomType getType() {
		return roomType;
	}
	
	public boolean isAvailable(Date arrivalDate, int stayLength) {
		IOUtils.trace("Room: isAvailable");
		for (Booking b : bookings) {
			if (b.doTimesConflict(arrivalDate, stayLength)) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean isReady() {
		return state == State.READY;
	}

//Implemented this method, creating a new booking object and passing the values which have been received as an
//argument in the method. adding booking in bookings list and returning the newly created booking object.
	public Booking book(Guest guest, Date arrivalDate, int stayLength, int numberOfOccupants, CreditCard creditCard) {
		Booking newBooking = new Booking(guest, this, arrivalDate, stayLength, numberOfOccupants, creditCard);
		bookings.add(newBooking);
		return newBooking;	
			
	}

//Implemented this method, checks if state is not ready then throws the runtime exception
//if its ready then changes state to OCCUPIED.
	public void checkin() {
		
		if(!isReady()) {
			
			throw new RuntimeException("Room is not Ready yet. ");
		}
		else {
			state = State.OCCUPIED;
		}
	}

//Implemented this method, takes booking object as an argument and checks if state is ready throws 
//an exception otherwise removes the booking from booking list and changes state to READY.
	public void checkout(Booking booking) {
		if(isReady()) {
			throw new RuntimeException("Room is not Ready yet. ");
		}
		else {
			bookings.remove(booking);
			state = State.READY;
		}
	}


}
