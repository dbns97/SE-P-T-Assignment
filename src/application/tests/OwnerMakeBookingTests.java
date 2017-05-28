package application.tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import application.controllers.OwnerMakeBookingController;
import application.models.Booking;
import application.models.Business;
import application.models.Customer;
import application.models.Employee;
import application.models.Owner;
import application.models.Service;

@RunWith(Parameterized.class)
public class OwnerMakeBookingTests {
	@Parameters
	public static Collection<Object[]> data() 
	{
		//Test Structure: {Starting time of booking, whether to put another booking in the Business,
		//clashing booking's starting time, clashing booking's ending time, Expected output}
		return Arrays.asList(new Object[][] {     
			{"13:00", false, null , null},
			{"9:00", false, null , null},
			{"16:30", false, null , null},
			{"16:31", false, null, "Choose a time when employee is working"},
			{"17:00", false, null, "Choose a time when employee is working"},
			{"8:59", false, null, "Choose a time when employee is working"},
			{"8:30", false, null, "Choose a time when employee is working"},
			{"13:00", true, "11:00", null},
			{"13:00", true, "13:30" , null},
			{"13:00", true, "12:30" , null},
			{"13:00", true, "13:00" , "Your chosen time goes through another booking"},
			{"13:00", true, "13:15" , "Your chosen time goes through another booking"},
		});
	}
	
	@Parameter(0)
	public String time;
	@Parameter(1)
	public boolean writeBookingClash;
	@Parameter(2)
	public String clashStart;
	@Parameter(3)
	public String expected;
	
	@Test
	public void checkBookingTests() {
		// Current day stored in a string
		String today = new SimpleDateFormat("EEEE").format(new Date());
		
		Business business;
		ArrayList<Service> services = new ArrayList<Service>();
		services.add(new Service("Service Name", 30));
		
		Employee e = new Employee("Email", "Name");
		e.setShift(today, "9:00", "17:00");
		ArrayList<Employee> employees = new ArrayList<Employee>();
		employees.add(e);
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Customer c = new Customer("Username", "Name", "Password");
		if(writeBookingClash)
		{
			// Creates a date for today, at the time in clashStart
			Calendar startDate = Calendar.getInstance();
			startDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(clashStart.substring(0, 2))); 
			startDate.clear(Calendar.MINUTE);
			startDate.set(Calendar.MINUTE, Integer.parseInt(clashStart.substring(3)));
			startDate.clear(Calendar.SECOND);
			startDate.clear(Calendar.MILLISECOND);
			
			// Creates a date for today, at the time in clashStart + 30 mins
			Calendar endDate = Calendar.getInstance();
			endDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(clashStart.substring(0, 2))); 
			endDate.clear(Calendar.MINUTE);
			endDate.set(Calendar.MINUTE, Integer.parseInt(clashStart.substring(3)) + 30);
			endDate.clear(Calendar.SECOND);
			endDate.clear(Calendar.MILLISECOND);
			
			Booking b = new Booking(startDate.getTime(), endDate.getTime(), e, c, new Service("Service Name", 30));
			c.addBooking(b);
		}
		customers.add(c);
		
		business = new Business("Business Name", new Owner("username", "password"), employees, services, customers);
		
		OwnerMakeBookingController controller = new OwnerMakeBookingController();
		String result = controller.checkBooking("Username", "Service Name", "Name", today.toUpperCase(), time, business);
		assertEquals(expected, result);
	}

}