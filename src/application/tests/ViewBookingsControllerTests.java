package application.tests;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.sun.javafx.application.PlatformImpl;

import application.controllers.ViewBookingsController;
import application.models.Booking;
import application.models.Business;
import application.models.Customer;
import application.models.Employee;
import application.models.Service;
import application.views.PublicMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

@RunWith(Parameterized.class)
public class ViewBookingsControllerTests {

	static SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
	
	@Parameters
    public static Collection<Object[]> data() 
    {
    	try
    	{
    		//Test Structure: {username, password, re-entered password, name, address, number, expected output of handleRegister}
        	return Arrays.asList(new Object[][] {
        		{sdf.parse("Tue 02/05/2017 12:30"), sdf.parse("Tue 02/05/2017 1:30"), "EName", "Service", "CName", true}
          });
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
    @Parameter(0)
    public Date startDate;
    
    @Parameter(1)
    public Date endDate;
    
    @Parameter(2)
    public String employee;
    
    @Parameter(3)
    public String service;
    
    @Parameter(4)
    public String customer;
    
    @Parameter(5)
    public boolean isDisplayed;
    
    
    
	@BeforeClass
	public static void setupPlatform()
	{
		try
	    {
	    	//Countsdown latch once the platform starts
	    	final CountDownLatch latch = new CountDownLatch(1);

			PlatformImpl.startup(() -> {});
			latch.countDown();
			latch.await();
	    }
	    catch (final InterruptedException e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	@Test
	public void testHandleView()
	{
		ViewBookingsController controller = new ViewBookingsController();
		mock(controller);
		
		ArrayList<Booking> bookingsArray = new ArrayList<Booking>();
		bookingsArray.add(new Booking(sdf.format(startDate), sdf.format(endDate), new Employee("email", employee), new Service(service, 60)));
		Customer c = new Customer("username", customer, "password", bookingsArray);
		controller.getBusiness().addCustomer(c);
		controller.getBusiness().getCustomer("username").getBookings().get(0).setCustomer(c);
		//System.out.println(controller.getBusiness().getCustomer("username").toJSONObject().toString(4));
		
		if (checkBeforeWeek(bookingsArray.get(0).getStart())) controller.setWeekChoiceBoxValue("Last week");
		else if (checkAfterWeek(bookingsArray.get(0).getStart())) controller.setWeekChoiceBoxValue("Next week");
		else controller.setWeekChoiceBoxValue("This week");
		
		for(Customer cust : controller.getBusiness().getCustomers())
		{
			System.out.println(cust.toJSONObject().toString(4));
		}
		
		controller.handleView();

		System.out.println(controller.getBookingsTable().getItems().size());
		System.out.println(controller.getBookingsTable().getItems().get(0).toJSONObject().toString(4));
		System.out.println(controller.getBookingsTable().getColumns().size());
		
		for(TableColumn<Booking,?> cust : controller.getBookingsTable().getColumns())
		{
			if(cust.getCellObservableValue(0) != null) System.out.println(cust.getCellObservableValue(0).getValue().toString());
			else System.out.println("NULL");
		}
		
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat displayDayFormat = new SimpleDateFormat("EEE");
		
		if(!isDisplayed)
		{
			Assert.assertNull(controller.getBookingsTable().getColumns().get(0).getCellObservableValue(0));
		}
		else
		{
			System.out.println(controller.getBookingsTable().getColumns().get(1).getCellObservableValue(0).getValue());
			Assert.assertEquals(controller.getBookingsTable().getColumns().get(0).getCellObservableValue(0).getValue(), displayFormat.format(startDate) + "-" + displayFormat.format(endDate));
		}
	}
	
	//@AfterClass
	public static void cleanFile()
	{
		//Clearing the empty file
				try {
					FileWriter writer = new FileWriter("src/JSONdatabase/empty.json");
					writer.write("{}");
					writer.flush();
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	public void mock(ViewBookingsController controller)
	{
		Business business = new Business("../../JSONdatabase/empty.json");
		controller.setBusiness(business);
		
		TableView<Booking> bt = new TableView<Booking>();
		TableColumn<Booking,String> dc = new TableColumn<Booking,String>();
		TableColumn<Booking,String> tc = new TableColumn<Booking,String>();
		TableColumn<Booking,String> ec = new TableColumn<Booking,String>();
		TableColumn<Booking,String> cc = new TableColumn<Booking,String>();
		TableColumn<Booking,String> sc = new TableColumn<Booking,String>();
		
		bt.getColumns().setAll(dc, tc, ec, cc, sc);
		
		controller.setBookingsTable(bt);
		controller.setDayColumn(dc);
		controller.setTimeColumn(dc);
		controller.setEmployeeColumn(dc);
		controller.setCustomerColumn(dc);
		controller.setServiceColumn(dc);
		
		
		System.out.println(bt.getColumns().toString());
		controller.setWeekChoiceBox(new ChoiceBox<String>());
		controller.setWeekChoiceBox();
	}
	
	public boolean checkBeforeWeek(Date inputDate)
	{
		Calendar startOfWeek = Calendar.getInstance();
		startOfWeek.setFirstDayOfWeek(Calendar.MONDAY);

		//Calendar representing today
		startOfWeek.set(Calendar.HOUR_OF_DAY, 0);
		startOfWeek.set(Calendar.MINUTE, 0);
		startOfWeek.set(Calendar.SECOND, 0);
		startOfWeek.set(Calendar.MILLISECOND, 0);
		startOfWeek.set(Calendar.DAY_OF_WEEK, startOfWeek.getFirstDayOfWeek());
		startOfWeek.add(Calendar.DAY_OF_WEEK, -1);
		Date startOfWeekDate = startOfWeek.getTime();
		
		return inputDate.before(startOfWeekDate);
	}
	
	public boolean checkAfterWeek(Date inputDate)
	{
		Calendar endOfWeek = Calendar.getInstance();
		endOfWeek.setFirstDayOfWeek(Calendar.MONDAY);

		//Calendar representing end of this week
		endOfWeek.set(Calendar.HOUR_OF_DAY, 0);
		endOfWeek.set(Calendar.MINUTE, 0);
		endOfWeek.set(Calendar.SECOND, 0);
		endOfWeek.set(Calendar.MILLISECOND, 0);
		endOfWeek.set(Calendar.DAY_OF_WEEK, endOfWeek.getFirstDayOfWeek());
		endOfWeek.add(Calendar.DAY_OF_WEEK, -1);
		Date endOfWeekDate = endOfWeek.getTime();
		
		return inputDate.before(endOfWeekDate);
		
		
	}

}
