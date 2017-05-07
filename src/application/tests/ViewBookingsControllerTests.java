package application.tests;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import org.junit.AfterClass;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@RunWith(Parameterized.class)
public class ViewBookingsControllerTests {

	@Parameters
    public static Collection<Object[]> data() 
    {
    	//Test Structure: {username, password, re-entered password, name, address, number, expected output of handleRegister}
    	return Arrays.asList(new Object[][] {
    		{"delete"}
      });
    }
    
    @Parameter
    public String adsf;
    
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
		ArrayList<Booking> test = new ArrayList<Booking>();
		test.add(new Booking("Tue 02/05/2017 12:30", "Tue 02/05/2017 1:30", new Employee("email","name"), new Service("Service", 60)));
		controller.getBusiness().addCustomer(new Customer("username", "name", "password", test));
		controller.getWeekChoiceBox().setValue("Last Week");
		controller.handleView();

		System.out.println(controller.getBookingsTable().getColumns().get(0).getCellObservableValue(0).getValue());
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
		Business business = new Business();
		
		controller.setBusiness(business);
		controller.setBookingsTable(new TableView<Booking>());
		controller.setDayColumn(new TableColumn<Booking,String>());
		controller.setTimeColumn(new TableColumn<Booking,String>());
		controller.setEmployeeColumn(new TableColumn<Booking,String>());
		controller.setCustomerColumn(new TableColumn<Booking,String>());
		controller.setServiceColumn(new TableColumn<Booking,String>());
		controller.setWeekChoiceBox(new ChoiceBox<String>());
		controller.setWeekChoiceBox();
		
		
	}

}
