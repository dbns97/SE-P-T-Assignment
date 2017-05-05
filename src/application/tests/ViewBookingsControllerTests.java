package application.tests;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

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
    		{"delete"},
    		{"me"}
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
		//System.out.println(controller.ge);
		controller.handleView();
	}
	
	public void mock(ViewBookingsController controller)
	{
		//Clearing the usersTest file
		try {
			FileWriter writer = new FileWriter("src/JSONdatabase/viewBookingsTest.json");
			writer.write("{}");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Business business = new Business("../../JSONdatabase/viewBookingsTest.json");
		
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
