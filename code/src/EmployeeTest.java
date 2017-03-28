package owner;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmployeeTest 
{
	private String name;
	private String nameRegex="[a-zA-Z ,.'-]+";

	@Before
	public void setup()
	{
		ownerMenu employee = new ownerMenu(null);
	}
	
	//when the name is null or doesn't match
    String add = employee.add(name);
    String result = nameRegex;
	
	@Test
	public void testName() 
	{
		System.out.println("Invalid name!");
		assertNotEquals(add, name);
	    
	}
	
	//when the email is null or doesn't match
	String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+";
	String email;
	String add2 = employee.add2(email);
	String result2 = emailRegex;
	private Object employees;
	
	@Test
	public void testEmail() 
	{
		System.out.println("Invalid email!");
		assertNotEquals(add2, email);	    
	}
	
	//when the employees already exist in the system
	String[] existingEmails = JSONObject.getNames(employees);
	String[] exist;
	String add3 = employee.add3(exist);
	String[] result3 = existingEmails;
	
	@Test
	public void testExist()
	{
		System.out.println("Employee Already Exists!");
		assertEquals(add3, result3);
	}
	
}
