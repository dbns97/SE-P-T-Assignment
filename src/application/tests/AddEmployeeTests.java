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

import application.controllers.AddEmployeeController;
import application.models.Business;
import application.models.Customer;
import application.models.Employee;
import application.models.Owner;
import application.models.Service;
import junit.framework.Assert;

@RunWith(Parameterized.class)
public class AddEmployeeTests {

	@Parameters
    public static Collection<Object[]> data() 
    {
    	//Test Structure: {starting time of shift, ending time of shift, expected error message}
    	return Arrays.asList(new Object[][] {     
            {"test@domain.com", "Joe Citizen", false, null},
            {"test@domain.com", "Joe Citizen", true, "Employee already exists with that email"},
            {"NoAtSymbol.com", "Joe Citizen", false, "Please enter a valid email"},
            {"test@NoDotCom", "Joe Citizen", false, "Please enter a valid email"},
            {"", "Joe Citizen", false, "Please enter a valid email"},
            {"test@domain.com", " ", false, "Please enter a valid email"},
            {"test@domain.com", "Joe Citizen1", false, "Please enter a valid email324134234"},
            {"_%+-@domain.com", "Joe Citizen", false, "Please enter a valid email"}
            });
    }
    
	@Parameter(0)
    public String email;
    @Parameter(1)
    public String name;
    @Parameter(2)
    public boolean addToBusiness;
    @Parameter(3)
    public String expected;
    
	@Test
	public void checkEmployeeDetailsTests() {
		Business business;
		if(addToBusiness)
		{
			ArrayList<Employee> employees = new ArrayList<Employee>();
			if(addToBusiness)
			{
				employees.add(new Employee(email, name));
			}
			else
			{
				employees.add(new Employee("email@domain.com", "name"));
			}
			business = new Business("Business Name", new Owner("username", "password"), employees, new ArrayList<Service>(), new ArrayList<Customer>());
			System.out.println(business.getEmployees().get(0).toJSONObject().toString(4));
			AddEmployeeController controller = new AddEmployeeController();
			String result = controller.checkEmployeeDetails(email, name, business);
			System.out.println(result + expected);
			assertEquals(expected, result);
		}
	}

}
