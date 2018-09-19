package hotel.entities;

import hotel.credit.CreditCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/*
 * Author: Bikram Shrestha
 *         11645312
 *         ITC515 Professional Programming Practices
 *         Assessment 3
 *
 * This is the Unit test of Hotel class. JUnit and Mockito has been used to
 * conduct the test. Mockito has been specifically used to mock the dependent
 * objects so that the test can be conducted in isolation.
 * Following test are conducted in listed methods below:-
 * => book() method
 *  - testBookRoomBookCalled()
 *      This test is done to make sure room.book() method is called.
 *  - testBookConfirmationNumber()
 *      This test is done to make sure that the confirmationNumber is returned
 *      by calling booking.getConfirmationNumber()
 *
 * => checkin()
 *  - testCheckinThrowsRuntimeException()
 *      This test is done to make sure that checkin() throws RuntimeException
 *      if booking is not found for given confirmationNumber.
 *  - testCheckinBookingPresent()
 *      This test is done to make sure that the Booking is present in as an
 *      active booking and can be accessed by entering the booking confirmation
 *      number.
 *  - testCheckinBookingMethodCalled()
 *      This test is done to make sure that all the method call to the booking
 *      object is being carried out as expected by the checkin() method.
 *
 * => addServiceCharge()
 *  - testAddServiceChargeThrowsRunTimeException()
 *      This test is done to check whether the addServiceCharge() method throws
 *      RuntimeException as expected when booking is not found after providing
 *      room id.
 *  - testAddServiceChargeBookingIsCalled()
 *      This test is done to check that the booking object is present and all the
 *      related method call to the booking object is performed as expected.
 *
 * => checkout()
 *  - testCheckoutThrowsRuntimeException()
 *      This test is done to make sure that the checkout() method throws Runtime
 *      Exception when the booking is not found when searched by room id.
 *  - testCheckoutBookingIsCalled()
 *      This test is carried out to make sure all the method call to the booking
 *      object is carried out as expected.
 */

public class HotelTestUnit {

    int stayLength;
    int occupantNumber;
    int cost;

    @Mock Booking booking;
    @Mock Guest guest;
    @Mock Date date;
    @Mock Room room;
    @Mock CreditCard card;

    @InjectMocks
    Hotel hotel;

    @Before
    public void setUp()   {
        MockitoAnnotations.initMocks(this);
        stayLength = 2;
        occupantNumber =2;
        cost = 10;
    }

    @After
    public void tearDown()   {
        hotel = null;
        stayLength = 0;
        occupantNumber = 0;
        cost = 0;
    }

    /* => book() method
     *  - testBookConfirmationNumber()
     *      This test is done to make sure that the confirmationNumber is returned
     *      by calling booking.getConfirmationNumber()
     */
    @Test
    public void testBookConfirmationNumber() {
        long mockedConfirmationNumber = 25102018201L;
        when(room.book(any(Guest.class),any(Date.class),anyInt(),
                anyInt(),any(CreditCard.class))).thenReturn(booking);
        when(booking.getConfirmationNumber()).thenReturn(mockedConfirmationNumber);
        long confirmationNumber = hotel.book(room,guest,date,stayLength,occupantNumber,card);

        assertEquals(mockedConfirmationNumber,confirmationNumber);
        verify(booking).getConfirmationNumber();
    }


     /* => book() method
      *  - testBookRoomBookCalled()
      *      This test is done to make sure room.book() method is called.
      */
    @Test
    public void testBookRoomBookCalled() {
        long mockedConfirmationNumber = 25102018201L;
        when(room.book(any(Guest.class),any(Date.class),anyInt(),
                anyInt(),any(CreditCard.class))).thenReturn(booking);
        when(booking.getConfirmationNumber()).thenReturn(mockedConfirmationNumber);

        hotel.book(room,guest,date,1,2,card);

        verify(room).book(any(Guest.class),any(Date.class),anyInt(),
                anyInt(),any(CreditCard.class));
    }


    /* => checkin()
     *  - testCheckinThrowsRuntimeException()
     *      This test is done to make sure that checkin() throws RuntimeException
     *      if booking is not found for given confirmationNumber.
     */
    @Test(expected = RuntimeException.class)
    public void testCheckinThrowsRuntimeException() {
        long confirmationNumber = 123456L;

        hotel.checkin(confirmationNumber);
    }

    /* => checkin()
     *  - testCheckinBookingMethodCalled()
     *      This test is done to make sure that all the method call to the booking
     *      object is being carried out as expected by the checkin() method.
     *
     */
    @Test
    public void testCheckInBookingMethodCalled() {
        int mockedRoomNumber = 201;
        long mockedConfirmationNumber = 25102018201L;
        hotel.bookingsByConfirmationNumber.put(mockedConfirmationNumber,booking);
        when(booking.getRoomId()).thenReturn(mockedRoomNumber);
        doNothing().when(booking).checkIn();

        hotel.checkin(mockedConfirmationNumber);

        verify(booking).getRoomId();
        verify(booking).checkIn();
    }

    /* => checkin()
     *  - testCheckinBookingPresent()
     *      This test is done to make sure that the Booking is present in as an
     *      active booking and can be accessed by entering the booking confirmation
     */
    @Test
    public void testCheckinBookingPresent() {
        long mockedConfirmationNumber = 25102018201L;
        int mockedRoomNumber = 201;
        hotel.bookingsByConfirmationNumber.put(mockedConfirmationNumber,booking);
        when(booking.getRoomId()).thenReturn(mockedRoomNumber);
        doNothing().when(booking).checkIn();

        hotel.checkin(mockedConfirmationNumber);
        Booking testBooking = hotel.activeBookingsByRoomId.get(mockedRoomNumber);

        assertNotNull(testBooking);

    }

     /* => addServiceCharge()
     *  - testAddServiceChargeThrowsRunTimeException()
     *      This test is done to check whether the addServiceCharge() method throws
     *      RuntimeException as expected when booking is not found after providing
     *      room id.
     */
    @Test(expected = RuntimeException.class)
    public void testAddServiceChargeThrowsRuntimeException() {
        int mockedRoomNumber = 201;

        hotel.addServiceCharge(mockedRoomNumber,ServiceType.ROOM_SERVICE,cost);
    }

    /* => addServiceCharge()
     *  - testAddServiceChargeBookingIsCalled()
     *      This test is done to check that the booking object is present and all the
     *      related method call to the booking object is performed as expected.
     */
    @Test
    public void testAddServiceChargeBookingIsCalled() {
        int mockedRoomNumber = 201;
        hotel.activeBookingsByRoomId.put(mockedRoomNumber,booking);
        doNothing().when(booking).addServiceCharge(ServiceType.ROOM_SERVICE,cost);

        hotel.addServiceCharge(mockedRoomNumber,ServiceType.ROOM_SERVICE,cost);

        verify(booking).addServiceCharge(ServiceType.ROOM_SERVICE,cost);
    }

    /* => checkout()
     *  - testCheckoutThrowsRuntimeException()
     *      This test is done to make sure that the checkout() method throws Runtime
     *      Exception when the booking is not found when searched by room id.
     */
    @Test(expected = RuntimeException.class)
    public void testCheckoutThrowsRuntimeException() {
        int mockedRoomNumber = 201;

        hotel.checkout(mockedRoomNumber);
    }

    /* => checkout()
     *  - testCheckoutBookingIsCalled()
     *      This test is carried out to make sure all the method call to the booking
     *      object is carried out as expected.
     */
    @Test
    public void testCheckoutBookingIsCalled() {
        int mockedRoomNumber = 201;

        hotel.activeBookingsByRoomId.put(mockedRoomNumber,booking);
        doNothing().when(booking).checkOut();

        hotel.checkout(mockedRoomNumber);

        verify(booking).checkOut();
    }
}