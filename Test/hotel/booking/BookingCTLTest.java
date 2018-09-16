package hotel.booking;

import hotel.credit.CreditAuthorizer;
import hotel.credit.CreditCard;
import hotel.credit.CreditCardType;
import hotel.entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;


/*
* This is a Integration testing for the BookingCTL. In this test, following
* criteria are being tested.
* - creditDetailsEntered()
*  When the method is called it need to throw RuntimeException when the state
*  is not in CREDIT state.
*  => testCreditDetailsEnteredWrongState()
*  When the method it called, the state need to be updated to the COMPLETED
*  state after successful execution.
*  => testCreditDetailsEnteredStateUpdated()
*  The method check whether the credit card has been authorised. This can be
*  confirmed by following test.
*  => testCreditDetailsEnteredCreditCardAuthorised()
*  When the method is called, this method calls method in hotel class called
*  book. This is been verified by following test.
*  =>testCreditDetailsEnteredHotelBookWasCalled()
* */
public class BookingCTLTest {

    private enum mockedState { COMPLETED}

    private Hotel hotel;
    private BookingCTL bookingCTL;

    private double cost;
    private int phoneNumber;
    private int occupantNumber;
    private Date date;
    private int stayLength;

    /*
    All the variables that is created in the before seems to be used frequently
    by all the methods to function. So, instead of writing the same code again
    and again, it is being provided as a default items to be crated before any
    test case is conducted.
    */
    @Before
    public void setUp() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        date = format.parse("25-11-2018");

        stayLength = 4;
        occupantNumber = 2;
        phoneNumber = 123;
        cost = 10;

        hotel = new Hotel();
        hotel.addRoom(RoomType.DOUBLE,201);
        hotel.addRoom(RoomType.TWIN_SHARE, 301);
    }

    /*
    All the information that was generated after test is reset to null or 0 as
    suitable for object type to eliminate any tampering the preceding test.
     */
    @After
    public void tearDown() {
        hotel = null;
        date = null;
        stayLength = 0;
        occupantNumber = 0;
        cost = 0;
        phoneNumber = 0;
    }


    /*
    * creditDetailsEntered() => This test is conducted to check whether
    * the method throws RuntimeException when the state is in different
    * state other then CREDIT. As the default state for control is PHONE,
    * It will throw RuntimeException
    * */
    @Test (expected = RuntimeException.class)      //Assert
    public void testCreditDetailsEnteredWrongState() {
        //Arrange
        bookingCTL = new BookingCTL(hotel);

        //Act
        bookingCTL.creditDetailsEntered(CreditCardType.VISA,2,2);
    }

    /*
    * creditDetailsEntered() => This test is conducted to check whether
    * the state are being updated to COMPLETED as expected after successful
    * execution of the code.*/
    @Test
    public void testCreditDetailsEnteredStateUpdated(){
        //Arrange
        bookingCTL = new BookingCTL(hotel);
        bookingCTL.phoneNumberEntered(phoneNumber);
        bookingCTL.guestDetailsEntered("Bikram","Kusma");
        bookingCTL.roomTypeAndOccupantsEntered(RoomType.TWIN_SHARE, occupantNumber);
        bookingCTL.bookingTimesEntered(date,stayLength);
        mockedState state = mockedState.COMPLETED;

        //Act
        bookingCTL.creditDetailsEntered(CreditCardType.VISA, 2, 2);
        boolean stateUpdated = String.valueOf(state).equals(String.valueOf(bookingCTL.getState()));

        //Assert
        assertTrue(stateUpdated);
    }

    /*
    * creditDetailsEntered() => This test is to verify that this method call the creditAuthorize to
    * check whether the card is been verified.*/
    @Test
    public void testCreditDetailsEnteredCreditCardAuthorised(){
        //Arrange
        CreditAuthorizer creditAuthorizer = new CreditAuthorizer();
        bookingCTL = new BookingCTL(hotel);
        bookingCTL.phoneNumberEntered(phoneNumber);
        bookingCTL.guestDetailsEntered("Bikram","Kusma");
        bookingCTL.roomTypeAndOccupantsEntered(RoomType.TWIN_SHARE, occupantNumber);
        bookingCTL.bookingTimesEntered(date,stayLength);

        //Act
        bookingCTL.creditDetailsEntered(CreditCardType.VISA, 2, 2);
        CreditCard creditCard = new CreditCard(CreditCardType.VISA,2,2);
        boolean cardAuthorised = (creditAuthorizer.authorize(creditCard, cost));

        //Assert
        assertTrue(cardAuthorised);
    }

    /*
    * creditDetailsEntered() => This test is conducted to check whether this method
    * call hotel.book() method.*/
    @Test
    public void testCreditDetailsEnteredHotelBookWasCalled(){
        //Arrange
        long mockedConfirmationNumber = 25102018201L;
        bookingCTL = new BookingCTL(hotel);
        bookingCTL.phoneNumberEntered(phoneNumber);
        bookingCTL.guestDetailsEntered("Bikram","Kusma");
        bookingCTL.roomTypeAndOccupantsEntered(RoomType.DOUBLE, occupantNumber);
        bookingCTL.bookingTimesEntered(date,stayLength);

        //Act
        bookingCTL.creditDetailsEntered(CreditCardType.VISA, 2, 2);
        Booking booking = hotel.findBookingByConfirmationNumber(mockedConfirmationNumber);
        long confirmationNumber = booking.getConfirmationNumber();
        boolean hotelBookWasCalled = (confirmationNumber == mockedConfirmationNumber);

        //Assert
        assertTrue(hotelBookWasCalled);

    }
}