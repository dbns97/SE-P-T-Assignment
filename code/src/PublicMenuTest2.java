package code;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

//password test
public class PublicMenuTest2 
{

		private PublicMenu sample;

		//initialization
		@BeforeClass
		public void setUp()
		{
			sample = new PublicMenu();
		}
		
		//destroys the object
		@AfterClass
		public void tearDown()
		{
			sample = null;
		}
		
	//valid password test
	@Test
	public final void passwordValid() 
	{
		System.out.println("Please enter your password: ");
		String password = " ";
		String expectedResult = " ";
		//don't get why there's an error here?
		String result = sample.login(password);
		
		assertEquals(expectedResult, result);
		
		password = "pass3";
		expectedResult = "pass3, pass2, pass, pass1, pass1";
		result = sample.login(password);
		assertEquals(expectedResult, result);
	}	
	
	//password too long or short
	@Test
	public final void passwordLong() 
	{
		System.out.println("Please enter your password: ");
		String password = " ";
		String expectedResult = " ";
		//don't get why there's an error here?
		String result = sample.login(password);
		
		assertEquals(expectedResult, result);
		
		//using a short length password
		password = password.length(4);
		expectedResult = password.length(10);
		result = sample.login(password);
		assertEquals(expectedResult, result);
	}
	
		//password doesn't match username
		@Test
		public final void passwordMatch() 
		{
			System.out.println("Please enter your username: ");
			System.out.println("Please enter your password: ");
			String username = " ";
			String password = " ";
			String expectedResult = " ";
			//don't get why there's an error here?
			String result = sample.login(password);
			
			assertEquals(expectedResult, result);
			
			password = "myname3";
			expectedResult = "username3, pass3";
			expectedResult = "username2, pass2";
			expectedResult = "username1, pass";
			expectedResult = "username 1, pass1";
			expectedResult = "username, pass1";
			result = sample.login(password) == sample.login(username);
			assertEquals(expectedResult, result);
		}
	

}
