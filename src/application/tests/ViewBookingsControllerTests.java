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
import application.views.PublicMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

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
		//controller.getBusiness().addCustomer(new Customer("username", "name", "password", test));
		//controller.setWeekChoiceBox();
		controller.setWeekChoiceBoxValue("Last week");
		Customer temp = null;
		for(Customer c : controller.getBusiness().getCustomers())
		{
			System.out.println(c.getUsername());
			temp = c;
		}
		
		controller.handleView();

		System.out.println(controller.getBookingsTable().getItems().size());
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
		
		TableView<Booking> bt = new TableView<Booking>();
		TableColumn<Booking,String> dc = new TableColumn<Booking,String>();
		TableColumn<Booking,String> tc = new TableColumn<Booking,String>();
		TableColumn<Booking,String> ec = new TableColumn<Booking,String>();
		TableColumn<Booking,String> cc = new TableColumn<Booking,String>();
		TableColumn<Booking,String> sc = new TableColumn<Booking,String>();
		
		controller.setBookingsTable(bt);
		controller.setDayColumn(dc);
		controller.setTimeColumn(dc);
		controller.setEmployeeColumn(dc);
		controller.setCustomerColumn(dc);
		controller.setServiceColumn(dc);
		
		bt.getColumns().add(dc);
		bt.getColumns().add(tc);
		bt.getColumns().add(ec);
		bt.getColumns().add(cc);
		bt.getColumns().add(sc);
		System.out.println(bt.getColumns().toString());
		controller.setWeekChoiceBox(new ChoiceBox<String>());
		controller.setWeekChoiceBox();
		
		
	}

}
