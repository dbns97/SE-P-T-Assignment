package owner;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class shiftTest 
{

	@Before
	public void setup()
	{
		ownerMenu shifts = new ownerMenu(null);
	}
	
	//when employee email is null or doesn't match
	String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+";
	String email;
	String addShift = shifts.addShift(email);
	String result = emailRegex;
	
	@Test
	public void testEmail() 
	{
		System.out.println("No employees exist under that email");
		assertNotEquals(addShift, email);	    
	}
	
	//when the date doens't match regex
	private String dateRegex= "[0-9]{2}/[0-9]{2}/[0-9]{4}";
	String date;
	String addShift2 = shifts.addShift2(date);
	String result2 = dateRegex;
	
	@Test
	public void testExist()
	{
		System.out.println("Employee Already Exists!");
		assertEquals(addShift2, result2);
	}
	
	//when the date is in the past
	int[] year = {2017, 2018};
	//test input
	int sampleYear;
	
	@Test
	public void testPast()
	{
		System.out.println("Inavlid Year. Year is in the past");
		assertNotEquals(sampleYear, year);
	}
	
}

