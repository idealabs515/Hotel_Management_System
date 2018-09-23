package hotel.testcases;
/**
 * This class is used to test the integrated functionality of The Record 
 * Service UI class and Record COntrol how their flow works. To make the 
 * testing simple the all the functionalities of the ui class are tested 
 * by calling all the methods in the run method of the UI class and 
 * testing the flow.
 * @author ChittyVaishnav
 * @studentId 11639078
 */


import org.junit.Test;


import hotel.HotelHelper;
import hotel.entities.Hotel;
import hotel.entities.ServiceType;
import hotel.service.RecordServiceCTL;



public class RecordServiceTest {
//Creating a hotel object 
	Hotel hotel = new Hotel();
	//Used to test the library object
	BookingAddServiceChargeTestCase bookTest = new BookingAddServiceChargeTestCase();
	


	@Test
	public void roomTestCase() throws Exception {
		//loadHotel() method basically loads the basic values needed for a hotel 
hotel = HotelHelper.loadHotel();
//creating a control object passing the loaded hotel class
RecordServiceCTL recordService = new RecordServiceCTL(hotel);
/*
 *Calling the method called room number entered to add service to that specific room
 *the method checks all the necessary steps that are required to check the available 
 *room 
 *methods invoked when this method is called:
 *If the state of controller is ROOM it is changed to SERVICE
 */
recordService.roomNumberEntered(301);
/*
 * Once the state is set to SERVICE 
 * The method invokes another method namely hotel.addSeerciveCharge which again calls the method
 * for finding the bookings done by the below method test() which is a unit test 
 * of the existing class booking and finally the state is set to
 * COMPLETED
 */
recordService.serviceDetailsEntered(ServiceType.ROOM_SERVICE, 1000.50);
bookTest.test();//calls the junit tested function along with the integrated functions
/*
 * The below method changes the state to  Completed regardless of whatever state it is
 */
recordService.cancel();
/*
 * The last method just prints a console output to pay for the above services
 */
recordService.completed();


	}





	

}






	

}
