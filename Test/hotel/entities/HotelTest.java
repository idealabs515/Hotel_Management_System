package hotel.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is the Unit test of the Hotel entities. All the test has been
 * sub divided for clarity of the code and expected result.
 * This test include test of following methods:
 *
 * -> book()
 * In booking following conditions are checked
 * - Whether the book method return confirmation number
 *      => testBookConfirmationNumberIsReturned()
 * - After calling book() whether the booking is created
 *      => testBookBookingExist()
 * - After calling book() whether the room is unavailable
 *      => testBookRoomIsUnavailable()
 *
 * -> checkin()
 * - Whether the checkin method throws RuntimeException
 *   when there is no booking with given confirmationNumber.
 *      => testCheckinNoBooking()
 *  - After calling checkin() whether the Booking object generated can be
 *      find by using findActiveBookingByRoomId() method.
 *      => testCheckinBookingExist()
 *  - After calling checkin() whether the state of booking is changed to
 *      Checked in
 *      => testCheckinStateIsChanged()
 *
 * -> addServiceCharge()
 *  - when there is no booking with given roomId, it throws RuntimeException
 *      => testAddServiceChargeNoBookingForRoom()
 * - After calling addServiceCharge() whether the charge has been added to
 *      to the booking.
 *      => testAddServiceChargeToActiveBooking()
 *
 * -> checkout()
 * - when there is no booking with given roomId, it throws RuntimeException
 *     => testCheckOutNoBookingForRoom()
 * - After calling checkout() whether the state of booking has been changed
 *      to CheckedOut
 *      => testCheckoutCheckedOut()
 * */

public class HotelTest {

    Hotel hotel;
    Guest guest;
    Booking booking;
    CreditCard card;
    Room room;
    Date date;
    int stayLength;
    int occupantNumber;
    public Map<Long, Booking> bookingsByConfirmationNumber;
    public Map<Integer, Booking> activeBookingsByRoomId;


    @Before
    public void setUp() throws Exception {
        bookingsByConfirmationNumber = new HashMap<>();
        activeBookingsByRoomId = new HashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        date = format.parse("25-11-2018");

        stayLength = 4;
        occupantNumber = 2;

        hotel = new Hotel();
        hotel.addRoom(RoomType.SINGLE, 101);
        hotel.addRoom(RoomType.DOUBLE, 201);
        hotel.addRoom(RoomType.TWIN_SHARE, 301);

        guest = new Guest("Ramesh", "Nurke", 2);

        card = new CreditCard(CreditCardType.VISA, 2, 2);

        room = hotel.findAvailableRoom(RoomType.TWIN_SHARE, date, stayLength);
    }


    @After
    public void tearDown() {
        hotel = null;
        bookingsByConfirmationNumber = null;
        activeBookingsByRoomId = null;
        guest = null;
        booking = null;
        card = null;
        room = null;
        date = null;
        stayLength = 0;
        occupantNumber = 0;
    }

    /*book() method need to return the confirmation in the format of DDMMYYYYroomId
    This method test whether the number being returned follow this given criteria.
    */
    @Test
    public void testBookConfirmationNumberIsReturned() {

        long expectedConfirmationNumber = 25102018301L;

        long confirmationNumber = hotel.book(room, guest, date, stayLength, occupantNumber, card);

        assertEquals(expectedConfirmationNumber, confirmationNumber);
    }

    /*book() method should update the hashmap that contain confirmationNumber and booking information.
    This test test whether the value that this method is suppose to add is there in the hashmap.
    */
    @Test
    public void testBookBookingExist() {
        long confirmationNumber = hotel.book(room, guest, date, stayLength, occupantNumber, card);


        boolean bookingExist = (hotel.findBookingByConfirmationNumber(confirmationNumber) != null);

        assertEquals(true, bookingExist);
    }

    /*book() method should call the room.book() to make sure that the room is made unavailable for the
    booking time period. This test test whether that is the case.
    */
    @Test
    public void testBookRoomIsUnavailable() {
        long confirmationNumber = hotel.book(room, guest, date, stayLength, occupantNumber, card);

        boolean roomAvailable = room.isAvailable(date, stayLength);

        assertEquals(false, roomAvailable);
    }

    
    /* checkin() method is suppose to throw RuntimeException when there is no booking with provided
    confirmationNumber. This test make sure that it throws RuntimeException by proving fake number.
     */
    @Test(expected = RuntimeException.class)    //Assert
    public void testCheckinNoBooking() {
        //Arrange
        long randomConfirmationNumber = 123456L;

        //Act
        hotel.checkin(randomConfirmationNumber);
    }


    /*
    checkin() method should put the booking detail with room id in activebookings hashmap. This test
    check whether the Booking object generated can be find by using findActiveBookingByRoomId() method.
     */
    @Test
    public void testCheckinBookingExist() {
        //Arrange
        long confirmationNumber = hotel.book(room, guest, date, stayLength, occupantNumber, card);
        hotel.checkin(confirmationNumber);
        int roomId = room.getId();

        //Act
        boolean bookingExist = (hotel.findActiveBookingByRoomId(roomId)!=null);

        //Assert
        assertEquals(true,bookingExist);

    }


    /*
    checkin() method should update booking state after calling checkin(). This test check whether the
    state of booking is changed to Checked in
     */
    @Test
    public void testCheckinStateIsChanged() {
        //Arrange
        long confirmationNumber = hotel.book(room, guest, date, stayLength, occupantNumber, card);
        hotel.checkin(confirmationNumber);
        int roomId = room.getId();
        booking  = hotel.findActiveBookingByRoomId(roomId);

        //Act
        boolean checkInState  = booking.isCheckedIn();

        //Assert
        assertEquals(true,checkInState);

    }

}