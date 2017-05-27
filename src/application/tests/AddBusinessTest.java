package application.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import application.controllers.AddBusinessController;

@RunWith(Parameterized.class)
public class AddBusinessTest {

	@Parameters
	public static Collection<Object[]> data() {
		// Test Structure: {Employee email, Employee name, whether the employee is added to the business, expect output}
		return Arrays.asList(new Object[][] { 
				{"Business Name", "OwnerUsername", "Password", "Password", "John", "1 StreetName, CityName", "99999999", "Successfully registered OwnerUsername"},
				{"Business Name 123", "OwnerUsername1", "Password1", "Password1", "John", "1 StreetName, CityName", "99999999", "Successfully registered OwnerUsername1"},
				{"", "OwnerUsername", "Password", "Password", "John", "1 StreetName, CityName", "99999999", "Please enter a business name"},
				{"Business Name", "", "Password", "Password", "John", "1 StreetName, CityName", "99999999", "Please enter a username without symbols or whitespace"},
				{"Business Name", "OwnerUsername!@#", "Password", "Password", "John", "1 StreetName, CityName", "99999999", "Please enter a username without symbols or whitespace"},
				{"Business Name", "Owner Username", "Password", "Password", "John", "1 StreetName, CityName", "99999999", "Please enter a username without symbols or whitespace"},
				{"Business Name", "OwnerUsername", "", "", "John", "1 StreetName, CityName", "99999999", "Please enter a password"},
				{"Business Name", "OwnerUsername", "Password", "DifferentPassword", "John", "1 StreetName, CityName", "99999999", "Please re-enter your password"},
				{"Business Name", "OwnerUsername", "Password", "", "John", "1 StreetName, CityName", "99999999", "Please re-enter your password"},
				{"Business Name", "OwnerUsername", "Password", "Password", "John1", "1 StreetName, CityName", "99999999", "Please enter a name without numbers or symbols"},
				{"Business Name", "OwnerUsername", "Password", "Password", "", "1 StreetName, CityName", "99999999", "Please enter a name without numbers or symbols"},
				{"Business Name", "OwnerUsername", "Password", "Password", "John", "1 StreetName, CityName!@#", "99999999", "Please enter an address without symbols"},
				{"Business Name", "OwnerUsername", "Password", "Password", "John", " ", "99999999", "Please enter an address without symbols"},
				{"Business Name", "OwnerUsername", "Password", "Password", "John", "1 StreetName, CityName", "99999999a", "Please enter a contact number with only numbers"},
				{"Business Name", "OwnerUsername", "Password", "Password", "John", "1 StreetName, CityName", "", "Please enter a contact number with only numbers"}
		});
	}

	@Parameter(0)
	public String businessName;
	@Parameter(1)
	public String username;
	@Parameter(2)
	public String password;
	@Parameter(3)
	public String reenter;
	@Parameter(4)
	public String ownerName;
	@Parameter(5)
	public String address;
	@Parameter(6)
	public String contactNumber;
	@Parameter(7)
	public String expected;

	@Test
	public void checkServiceDetailsTest() {
		AddBusinessController controller = new AddBusinessController();
		String result = controller.checkBusinessDetails(businessName, username, password, reenter, ownerName, address, contactNumber);
		assertEquals(expected, result);
	}

}
