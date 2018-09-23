package hotel.testcases;



import org.junit.Test;


import hotel.HotelHelper;
import hotel.entities.Hotel;
import hotel.entities.ServiceType;
import hotel.service.RecordServiceCTL;



public class RecordServiceTest {

	Hotel h = new Hotel();
	BookingAddServiceChargeTestCase bookTest = new BookingAddServiceChargeTestCase();
	


	@Test
	public void roomTestCase() throws Exception {
h = HotelHelper.loadHotel();
RecordServiceCTL rc = new RecordServiceCTL(h);
rc.roomNumberEntered(301);
rc.serviceDetailsEntered(ServiceType.ROOM_SERVICE, 1000.50);
bookTest.test();
rc.cancel();
rc.completed();


	}





	

}
