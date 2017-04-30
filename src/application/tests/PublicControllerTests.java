package application.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.sun.javafx.application.PlatformImpl;

import application.controllers.PublicMenuController;
import application.models.Business;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PublicControllerTests {

    @Parameters
    public static Collection<Object[]> data() 
    {
    	//Test Structure: {username, password, if writeToFile() adds the data to the JSON database, expected output}
    	return Arrays.asList(new Object[][] {     
            {"username", "pass", true, true}, {"username1", "pass1", true, true}, {"123", "123", true, true}, {"blank", " ", true, true}, {"notInDatabase", "pass", false, false}, {"", "", false, false}
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
	
	//Writes the test data to the JSON database based on if the 3rd parameter is true
	@Before
	public void writeToFile()
	{
		JSONObject wrapper = new JSONObject();
		for(Object[] test : data())
		{
			//Writes the test if the test specifies
			if((boolean)test[2])
			{
				JSONObject newUser = new JSONObject();
				newUser.put("password", (String)test[1]);
				newUser.put("name", "test");
				newUser.put("isOwner", false);
				newUser.put("bookings", new JSONArray());
			
				wrapper.put((String)test[0], newUser);
			}
		}
		
		//Finally writes the whole JSONObject to the test file
		try
		{
			FileWriter custWriter = new FileWriter("src/JSONdatabase/loginTest.json");
			System.out.println(wrapper.toString(4));
			custWriter.write(wrapper.toString(4));
			custWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCheckLogin() {

		JSONObject wrapper = new JSONObject();
		for(Object[] test : data())
		{
			//Writes the test if the test specifies
			if((boolean)test[2])
			{
				JSONObject newUser = new JSONObject();
				newUser.put("password", (String)test[1]);
				newUser.put("name", "test");
				newUser.put("isOwner", false);
				newUser.put("bookings", new JSONArray());
			
				wrapper.put((String)test[0], newUser);
			}
		}
		
		//Finally writes the whole JSONObject to the test file
		try
		{
			FileWriter custWriter = new FileWriter("src/JSONdatabase/loginTest.json");
			System.out.println(wrapper.toString(4));
			custWriter.write(wrapper.toString(4));
			custWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		PublicMenuController controller = new PublicMenuController();
		mock(controller);
		controller.setUsername(username);
		controller.setPassword(password);
		
		//Asserts that the expected value (found in the parameterized variable) is outputed by the function
		assertEquals(expected, controller.checkLogin());
	}	
	
	//@AfterClass
	public static void deleteFile()
	{
		(new File("src/JSONdatabase/loginTest.json")).delete();
	}
	
	//Encapsulates the controller in dummy data
	public void mock(PublicMenuController controller)
	{
		Business business = new Business();
		controller.setBusiness(business);
		controller.setErrorLabel(new Label());
		controller.setUsername(new TextField());
		controller.setPassword(new PasswordField());
		controller.setUsersFilepath("../../JSONdatabase/loginTest.json");
	}

}
