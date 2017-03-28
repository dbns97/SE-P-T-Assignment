package code;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

//username test
public class PublicMenuTest 
{
	//initialization
	@BeforeClass
	public void setUp()
	{
		PublicMenu sample = new PublicMenu();
	}
	
	//destroys the object
	@AfterClass
	public void tearDown()
	{
		sample = null;
	}
			
	
	@Test
	//tests to see if the username is correct
	public void usernameValid() 
	{
		System.out.println("Please enter your username: ");
		String username = " ";
		String expectedResult = " ";
		//don't get why there's an error here?
		String result = sample.login(username);
		
		//checks if the expected username is equal to the actual username
		assertEquals(expectedResult, result);
		
		//random username to test
		username = "customer123";
		expectedResult = "username, username 1, username1, username2, username3";
		result = sample.login(username);
		assertEquals(expectedResult, result);
	}
	
	
	@Test
	//tests when username is too long or too short
	public void usernameLong() 
	{
		System.out.println("Please enter your username: ");
		String username = " ";
		String expectedResult = " ";
		//don't get why there's an error here?
		String result = sample.login(username);
		
		//checks if the expected username is equal to the actual username
		assertEquals(expectedResult, result);
		
		//using a short lenth username
		username = username.length(4);
		expectedResult = username.length(10);
		result = sample.login(username);
		assertEquals(expectedResult, result);
	}		
}
