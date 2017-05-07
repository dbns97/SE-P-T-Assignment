package application.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import com.sun.javafx.application.PlatformImpl;

import application.controllers.PublicMenuController;
import application.models.Business;
import application.models.Customer;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PublicControllerTests {

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();
	
    @Parameters
    public static Collection<Object[]> data() 
    {
    	//Test Structure: {username, password, if writeToFile() adds the data to the JSON database, expected output}
    	return Arrays.asList(new Object[][] {     
            {"username", "pass", true, true}, 
            {"username1", "pass1", true, true}, 
            {"123", "123", true, true}, 
            {"blank", " ", true, true}, 
            {"notInDatabase", "pass", false, false}, 
            {"", "", false, false}
      });
    }
    
    @Parameter
    public String username;
    @Parameter(1)
    public String password;
    @Parameter(2)
    public boolean writeToFile;
    @Parameter(3)
    public boolean expected;
    
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
	public void testCheckLogin() {
		
		PublicMenuController controller = new PublicMenuController();
		mock(controller);
		controller.setUsername(username);
		controller.setPassword(password);
		
		//Asserts that the expected value (found in the parameterized variable) is outputed by the function
		assertEquals(expected, controller.checkLogin());
	}
	
	//Encapsulates the controller in dummy data
	public void mock(PublicMenuController controller)
	{
		Business business = new Business("../../JSONdatabase/empty.json");
		controller.setBusiness(business);
		controller.setErrorLabel(new Label());
		controller.setUsername(new TextField());
		controller.setPassword(new PasswordField());
		if(writeToFile) controller.getBusiness().addCustomer(new Customer(username, "empty", password));
	}

}
