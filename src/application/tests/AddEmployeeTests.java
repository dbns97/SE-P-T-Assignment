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

import application.models.Business;
import application.models.Employee;
import application.models.Owner;

@RunWith(Parameterized.class)
public class AddEmployeeTests {

	@Parameters
    public static Collection<Object[]> data() 
    {
    	//Test Structure: {starting time of shift, ending time of shift, expected error message}
    	return Arrays.asList(new Object[][] {     
            {"test@domain.com", "Joe Citizen", false, null},
            {"test@domain.com", "Joe Citizen", true, null}
            });
    }
    
	@Parameter(0)
    public String startTime;
    @Parameter(1)
    public String endTime;
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
			business = new Business("Business Name", new Owner("username", "password"), employees )
		}
	}

}
