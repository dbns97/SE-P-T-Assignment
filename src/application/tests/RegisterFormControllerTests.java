package application.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import application.controllers.RegisterFormController;
import application.models.Business;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@RunWith(Parameterized.class)
public class RegisterFormControllerTests {

	@Parameters
    public static Collection<Object[]> data() 
    {
    	//Test Structure: {username, password, re-entered password, name, address, number, expected output of handleRegister}
    	return Arrays.asList(new Object[][] {     
            {"username1", "password", "password", "Joe Citizen", "10 Example Street Example City", "99999999", true}, 
            {"username2", "", "password", "Joe Citizen", "10 Example Street Example City", "99999999", false},
            {"username3", "password", "", "Joe Citizen", "10 Example Street Example City", "99999999", false},
            {"username4", "password", "password", "", "10 Example Street Example City", "99999999", false},
            {"username5", "password", "password", "Joe Citizen", "", "99999999", false},
            {"username6", "password", "password", "Joe Citizen", "10 Example Street Example City", "", false},
            {"~!@#", "password", "password", "Joe Citizen", "10 Example Street Example City", "99999999", false},
            {"username7", "", "password", "Joe Citizen", "10 Example Street Example City", "99999999", false},
            {"username8", "password", "password1", "Joe Citizen", "10 Example Street Example City", "99999999", false},
            {"username9", "password", "password", "Joe Citizen1", "10 Example Street Example City", "99999999", false},
            {"username10", "password", "password", "Joe Citizen", "10 Example Street Example City!", "99999999", false},
            {"username11", "password", "password", "Joe Citizen", "10 Example Street Example City", "999999s99", false}
      });
    }
    
    @Parameter(0)
    public String username;
    @Parameter(1)
    public String password;
    @Parameter(2)
    public String reenteredPassword;
    @Parameter(3)
    public String name;
    @Parameter(4)
    public String address;
    @Parameter(5)
    public String contactNumber;
    @Parameter(6)
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
		
		try {
			(new File("src/JSONdatabase/usersTest.json")).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHandleRegister()
	{
		RegisterFormController controller = new RegisterFormController();
		mock(controller);
		
		controller.setUsername(username);
		controller.setPassword(password);
		controller.setReenteredPassword(reenteredPassword);
		controller.setName(name);
		controller.setAddress(address);
		controller.setContactNumber(contactNumber);
		
		//System.out.println(controller.getUsername().getText() + controller.getBusiness().getCustomers().get(0).getUsername() + controller.getErrorLabel().getText());
		assertEquals(expected, controller.handleRegister());
		//System.out.println(controller.getUsername().getText() + controller.getBusiness().getCustomers().get(0).getUsername() + controller.getErrorLabel().getText() + controller.getBusiness().getCustomer(username).getUsername());
		if(expected)
		{
			assertNotNull(controller.getBusiness().getCustomer(username));
		}
		else
		{
			assertNull(controller.getBusiness().getCustomer(username));
		}
	}
	
	//@AfterClass
	public static void deleteFile()
	{
		(new File("src/JSONdatabase/usersTest.json")).delete();
	}
	
	public void mock(RegisterFormController controller)
	{
		//Clearing the usersTest file
		try {
			FileWriter writer = new FileWriter("src/JSONdatabase/usersTest.json");
			writer.write("{}");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Business business = new Business("../../JSONdatabase/usersTest.json");
		
		controller.setBusiness(business);
		controller.setErrorLabel(new Label());
		controller.setUsername(new TextField());
		controller.setPassword(new PasswordField());
		controller.setReenteredPassword(new PasswordField());
		controller.setAddress(new TextField());
		controller.setContactNumber(new TextField());
		controller.setName(new TextField());
		controller.setRegisterButton(new Button());
		
	}
}
