package application.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import application.controllers.AddServiceController;
import application.models.Business;
import application.models.Customer;
import application.models.Employee;
import application.models.Owner;
import application.models.Service;

@RunWith(Parameterized.class)
public class AddServiceTests {

	@Parameters
	public static Collection<Object[]> data() 
	{
		//Test Structure: {Employee email, Employee name, whether the employee is added to the business, expect output}
		return Arrays.asList(new Object[][] {     
			{"Service name", "120", false, "Successfully created service: Service name"},
			{"Service name", "120", true, "Failed to create service. Name: Service name already in use"},
			{"@!$", "120", false, "Please enter a valid name"},
			{"", "120", false, "Please enter a valid name"},
			{" ", "120", false, "Please enter a valid name"},
			{"Service Name", "", false, "Please enter a valid duration in minutes (maximum 3 digits)"},
			{"Service Name", " ", false, "Please enter a valid duration in minutes (maximum 3 digits)"},
			{"Service Name", "string", false, "Please enter a valid duration in minutes (maximum 3 digits)"},
			{"Service Name", "1234", false, "Please enter a valid duration in minutes (maximum 3 digits)"},
			
		});
	}
	
	@Parameter(0)
	public String name;
	@Parameter(1)
	public String duration;
	@Parameter(2)
	public boolean addToBusiness;
	@Parameter(3)
	public String expected;
	
	@Test
	public void checkServiceDetailsTest() {
		Business business;
		ArrayList<Service> services = new ArrayList<Service>();
		
		if(addToBusiness)
		{
			services.add(new Service(name, Integer.parseInt(duration)));
		}
		
		business = new Business("Business Name", new Owner("username", "password"), new ArrayList<Employee>(), services, new ArrayList<Customer>());
		
		AddServiceController controller = new AddServiceController();
		String result = controller.checkServiceDetails(name, duration, business);
		assertEquals(expected, result);
	}

}
