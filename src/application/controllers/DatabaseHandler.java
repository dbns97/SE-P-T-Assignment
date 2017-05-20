package application.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import application.models.Booking;
import application.models.Business;
import application.models.Customer;
import application.models.Day;
import application.models.Employee;
import application.models.Owner;
import application.models.Service;
import application.models.Shift;

public class DatabaseHandler {
	
	private final static String adminFilePath      = "../../JSONdatabase/admin.json";
	private final static String businessesFilePath = "../../JSONdatabase/businesses.json";
	private final static String employeesFilePath  = "../../JSONdatabase/employees.json";
	private final static String usersFilePath      = "../../JSONdatabase/users.json";
    
	/**
	 * @description create an ArrayList containing all employees for a business
	 * @return ArrayList<Employee> the list of all employees
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	public static ArrayList<Employee> getEmployees(String businessName)
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		JSONObject jsonEmployees = getJSONObjectFromFile(employeesFilePath).getJSONObject(businessName);
		String[] emails = JSONObject.getNames(jsonEmployees);
		
		if (emails == null) return employees;
		
		// Iterate over each employee
		for(int i = 0; i < emails.length; i++) {
        	String email = emails[i];
        	String name = jsonEmployees.getJSONObject(email).getString("name");
        	
        	Employee newEmployee = new Employee(email, name);
        	employees.add(newEmployee);
        	
        	JSONObject jsonRoster = jsonEmployees.getJSONObject(email).getJSONObject("roster");
        	HashMap<Day,Shift> roster = getRoster(jsonRoster, newEmployee);
        	newEmployee.setRoster(roster);
		}
        
        return employees;
	}
	
	/**
	 * @description create an ArrayList containing all services offered by a business
	 * @return ArrayList<Service> the list of all services offered
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	public static ArrayList<Service> getServices(String businessName)
	{
		ArrayList<Service> services = new ArrayList<Service>();
		JSONArray jsonServices = getJSONObjectFromFile(businessesFilePath).getJSONObject(businessName).getJSONArray("services");
		
		// Iterate over each service
		for (int i = 0; i < jsonServices.length(); i++) {
			JSONObject jsonService = jsonServices.getJSONObject(i);
        	String name = jsonService.getString("name");
        	int duration = jsonService.getInt("duration");
        	
        	services.add(new Service(name, duration));
		}
        
        return services;
	}
	
	/**
	 * @description create an ArrayList containing all customers for a business
	 * @return ArrayList<Customer> the list of all customers
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	public static ArrayList<Customer> getCustomers(Business business)
	{
		ArrayList<Customer> customers = new ArrayList<Customer>();
		JSONObject jsonUsers = getJSONObjectFromFile(usersFilePath).getJSONObject(business.getName());
		String[] usernames = JSONObject.getNames(jsonUsers);
		
		if (usernames == null) return customers;
		
		// Find the owner in the list of users
		for(int i = 0; i < usernames.length; i++) {
        	String username = usernames[i];
        	if (!jsonUsers.getJSONObject(username).getBoolean("isOwner")) {
            	String name = jsonUsers.getJSONObject(username).getString("name");
            	String password = jsonUsers.getJSONObject(username).getString("password");
            	
            	JSONArray jsonBookings = jsonUsers.getJSONObject(username).getJSONArray("bookings");
            	ArrayList<Booking> bookings = new ArrayList<Booking>();
            	for (int j = 0; j < jsonBookings.length(); j++)
            	{
            		String bookingStart = jsonBookings.getJSONObject(j).getString("start");
            		String bookingEnd = jsonBookings.getJSONObject(j).getString("end");
            		String bookingEmployeeEmail = jsonBookings.getJSONObject(j).getString("employee");
            		Employee bookingEmployee = business.getEmployee(bookingEmployeeEmail);
            		String bookingServiceName = jsonBookings.getJSONObject(j).getString("service");
            		Service bookingService = business.getService(bookingServiceName);
            		
            		bookings.add(new Booking(bookingStart, bookingEnd, bookingEmployee, bookingService));
            	}
            	
            	// Add the new customer
            	Customer newCustomer = new Customer(username, name, password, bookings);
            	customers.add(newCustomer);
            	
            	// Add the reference to the customer in each booking
            	for (Booking b : newCustomer.getBookings())
            	{
            		b.setCustomer(newCustomer);
            	}
        	}
		}
        
        return customers;
	}
	
	/**
	 * @description Creates an object for the owner of a business
	 * @return Owner the owner object
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	public static Owner getOwner(String businessName)
	{
		// Get usernames of all users in selected business
		JSONObject jsonUsers = getJSONObjectFromFile(usersFilePath).getJSONObject(businessName);
		String[] usernames = JSONObject.getNames(jsonUsers);
		
		if (usernames == null) return null;
		
		// Find the owner in the list of users
		for(int i = 0; i < usernames.length; i++) {
        	String username = usernames[i];
        	if (jsonUsers.getJSONObject(usernames[i]).getBoolean("isOwner")) {
            	String password = jsonUsers.getJSONObject(username).getString("password");
            	
            	// Return the owner
            	return new Owner(username, password);
        	}
		}
        
		// If owner is not found return null
        return null;
	}
	
	/**
	 * @description Create a HashMap of a roster from a JSONObject of a roster
	 * @param jsonRoster A JSONObjects of a roster
	 * @return HashMap<String,Shift> the roster
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	public static HashMap<Day,Shift> getRoster(JSONObject jsonRoster, Employee employee)
	{
		HashMap<Day,Shift> roster = new HashMap<Day,Shift>(7);
		
		for (Day day : Day.values())
		{
			if (jsonRoster.has(day.name()))
			{
				JSONObject jsonShift = jsonRoster.getJSONObject(day.name());
            	String start = jsonShift.getString("start");
            	String end = jsonShift.getString("end");
            	Shift newShift = new Shift(start, end);
            	newShift.setEmployee(employee);
            	
            	roster.put(day, newShift);
			} else {
				roster.put(day, null);
			}
		}
        
        return roster;
	}
	
	/**
	 * @description write all objects from a business to file
	 * @return void
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	public static void writeBusinessToFile(Business business)
	{
		try
        {	
			// Get all users for the business
			JSONObject jsonUsers = new JSONObject();
			for (Customer c : business.getCustomers())
			{
				jsonUsers.put(c.getUsername(), customerToJSON(c));
			}
			jsonUsers.put(business.getOwner().getUsername(), ownerToJSON(business.getOwner()));
			
			// Write users to file
			JSONObject allJsonUsers = getJSONObjectFromFile(usersFilePath);
			allJsonUsers.put(business.getName(), jsonUsers);
            FileWriter custWriter = new FileWriter("src/JSONdatabase/users.json");
            custWriter.write(allJsonUsers.toString(4));
            custWriter.flush();
            custWriter.close();
            

            // Get all employees for this business
            JSONObject jsonEmployees = new JSONObject();
            for (Employee e : business.getEmployees())
            {
            	jsonEmployees.put(e.getEmail(), employeeToJSON(e));
            }
            
            // Write employees to file
			JSONObject allJsonEmployees = getJSONObjectFromFile(employeesFilePath);
			allJsonEmployees.put(business.getName(), jsonEmployees);
            FileWriter employeeWriter = new FileWriter("src/JSONdatabase/employees.json");
            employeeWriter.write(allJsonEmployees.toString(4));
            employeeWriter.flush();
            employeeWriter.close();
            
            // Get the details for this business
            JSONObject jsonBusiness = new JSONObject();
            JSONArray jsonServices = new JSONArray();
            for (Service s : business.getServices())
            {
            	jsonServices.put(serviceToJSON(s));
            }
            jsonBusiness.put("services", jsonServices);
           
            // Write business to file
			JSONObject allJsonBusinesses = getJSONObjectFromFile(businessesFilePath);
			allJsonBusinesses.put(business.getName(), jsonBusiness);
            FileWriter serviceWriter = new FileWriter("src/JSONdatabase/businesses.json");
            serviceWriter.write(allJsonBusinesses.toString(4));
            serviceWriter.flush();
            serviceWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}
		
	/**
	 * @description Check database to see if a business name already exists
	 * @return boolean whether the name exists or not
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	public static boolean businessExists(String businessName)
	{
		JSONObject jsonBusinesses = getJSONObjectFromFile(businessesFilePath);
		String[] businesses = JSONObject.getNames(jsonBusinesses);
		for (int i = 0; i < businesses.length; i++) {
			if (businesses[i].equals(businessName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @description Checks login details against database
	 * @return boolean whether the login details were valid
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	public static boolean checkLogin(String businessName, String username, String password)
	{
		// Load all users for the business
		JSONObject jsonUsers = getJSONObjectFromFile(usersFilePath).getJSONObject(businessName);

		// Create an array of usernames
		String[] usernames = JSONObject.getNames(jsonUsers);
		if(usernames == null) {
			return false;
		}

		// Loop over all usernames
		for (int i = 0; i < usernames.length; i++) {
			// Check if the username matches the current one in the list
			if (username.equals(usernames[i])) {
				JSONObject jsonUser = jsonUsers.getJSONObject(username);

				// checks password
				if (password.equals(jsonUser.getString("password")))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * @description Check login details for an admin user
	 * @return boolean
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	public static boolean checkAdminLogin(String username, String password)
	{
		JSONArray jsonUsers = getJSONObjectFromFile(adminFilePath).getJSONArray("users");
		
		for (int i = 0; i < jsonUsers.length(); i++) {
			if (jsonUsers.getJSONObject(i).get("username").equals(username) && jsonUsers.getJSONObject(i).get("password").equals(password)) {
				return true;
			}
		}
		
		return false;
	}
	
	/*-------------------- Private Methods --------------------*/
	
	/**
	 * @description Convert an owner object to a JSONObject
	 * @return JSONObject the owner as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	private static JSONObject ownerToJSON(Owner owner)
	{
		JSONObject jsonOwner = new JSONObject();
		jsonOwner.put("password", owner.getPassword());
		jsonOwner.put("isOwner", true);
		
		return jsonOwner;
	}

	/**
	 * @description Convert a shift to a JSONObject
	 * @return JSONObject the shift as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	private static JSONObject shiftToJSON(Shift shift)
	{
		JSONObject jsonShift = new JSONObject();

		jsonShift.put("start", Shift.sdf.format(shift.getStart()));
		jsonShift.put("end", Shift.sdf.format(shift.getEnd()));

		return jsonShift;
	}
	
	/**
	 * @description Convert a service to a JSONObject
	 * @return JSONObject the service as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	private static JSONObject serviceToJSON(Service service)
	{
		JSONObject jsonService = new JSONObject();
		
		jsonService.put("name", service.getName());
		jsonService.put("duration", service.getDuration());

		return jsonService;
	}

	/**
	 * @description Convert a employee to a JSONObject
	 * @return JSONObject the employee as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	private static JSONObject employeeToJSON(Employee employee)
	{
		JSONObject jsonEmployee = new JSONObject();
		
		jsonEmployee.put("email", employee.getEmail());
		jsonEmployee.put("name", employee.getName());
		
		JSONObject jsonRoster = new JSONObject();
		for (HashMap.Entry<Day,Shift> entry : employee.getRoster().entrySet()) {
			if (entry.getValue() != null) {
				jsonRoster.put(entry.getKey().name(), shiftToJSON(entry.getValue()));	
			}
		}
		jsonEmployee.put("roster", jsonRoster);

		return jsonEmployee;
	}

	/**
	 * @description Convert a customer to a JSONObject
	 * @return JSONObject the customer as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	private static JSONObject customerToJSON(Customer customer)
	{
		JSONObject newUser = new JSONObject();
		newUser.put("password", customer.getPassword());
		newUser.put("name", customer.getName());
		newUser.put("isOwner", false);
		
		JSONArray jsonBookings = new JSONArray();
		for (Booking b : customer.getBookings())
		{
			jsonBookings.put(bookingToJSON(b));
		}
		newUser.put("bookings", jsonBookings);
		
		return newUser;
	}
	
	/**
	 * @description Convert a booking to a JSONObject
	 * @return JSONObject the booking as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 20/5/2017
	 **/
	private static JSONObject bookingToJSON(Booking booking)
	{
		JSONObject jsonBooking = new JSONObject();

		jsonBooking.put("start", Booking.sdf.format(booking.getStart()));
		jsonBooking.put("end", Booking.sdf.format(booking.getEnd()));
		jsonBooking.put("customer", booking.getCustomer().getUsername());
		jsonBooking.put("employee", booking.getEmployee().getEmail());
		jsonBooking.put("service", booking.getService().getName());

		return jsonBooking;
	}
	
	private static JSONObject getJSONObjectFromFile(String path)
    {
        Scanner sc;
        InputStream in = inputStreamFromFile(path);
        sc = new Scanner(in);
        StringBuilder stringBuilder = new StringBuilder();
        
        while(sc.hasNextLine())
        {
            stringBuilder.append(sc.nextLine());
        }
        
        String json = stringBuilder.toString();
        sc.close();

        return new JSONObject(json);
    }
    
    private static InputStream inputStreamFromFile(String path)
    {
       try
       {
          InputStream inputStream = DatabaseHandler.class.getResourceAsStream(path);
          return inputStream;
       }
       catch(Exception e)
       {
          e.printStackTrace();
       }
       return null;
    }
	
}
