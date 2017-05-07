package application.models;
import application.views.*;
import application.controllers.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;

public class Business {
	private String usersFilepath     = "../../JSONdatabase/users.json";
	private String employeesFilepath = "../../JSONdatabase/employees.json";
	private String businessFilepath  = "../../JSONdatabase/business.json";
	//private JSONObject[] loadedUsers;
	private Owner owner;
	private ArrayList<Customer> customers;
	private ArrayList<Employee> employees;
	private ArrayList<Service> services;
	
	public Business()
	{
		owner = initialiseOwner();
		employees = initialiseEmployees();
		services = initialiseServices();
		customers = initialiseCustomers();
	}
	
	//Used in testing to change where the Business reads in the users
	public Business(String usersFilepath)
	{
		setUsersFilepath(usersFilepath);
		owner = initialiseOwner();
		employees = initialiseEmployees();
		customers = initialiseCustomers();
	}
	
	
	public Owner getOwner()
	{
		return owner;
	}
	
	public ArrayList<Service> getServices() 
	{
		
		return services;
	}
	
	
	
 	public ArrayList<Customer> getCustomers()
	{
		return customers;
	}
	
 	public void setCustomers(ArrayList<Customer> customers)
 	{
 		this.customers = customers;
 	}
	public void setUsersFilepath(String filepath)
	{
		this.usersFilepath = filepath;
	}
	/**
	 * @description return a customer with a specific username
	 * @param username the username of the customer to be returned
	 * @return Customer the customer
	 * @author Drew Nuttall-Smith
	 * @since 9/4/2017
	 **/
	public Customer getCustomer(String username)
	{
		for (Customer cust : customers)
		{
			if (cust.getUsername().equals(username))
			{
				return cust;
			}
		}
		
		return null;
	}
	
	public ArrayList<Employee> getEmployees()
	{
		return employees;
	}
	
	/**
	 * @description return an employee with a specific email
	 * @param email the email of the employee to be returned
	 * @return Employee the employee object
	 * @author Drew Nuttall-Smith
	 * @since 3/4/2017
	 **/
	public Employee getEmployee(String email)
	{
		for (Employee emp : employees)
		{
			if (emp.getEmail().equals(email))
			{
				return emp;
			}
		}
		
		return null;
	}
	
	/**
	 * @description return a service with a specific name
	 * @param name the name of the service to be returned
	 * @return Service the service object
	 * @author Drew Nuttall-Smith
	 * @since 30/4/2017
	 **/
	public Service getService(String name)
	{
		for (Service ser : services)
		{
			if (ser.getName().equals(name))
			{
				return ser;
			}
		}
		
		return null;
	}
	
	/*
	public void loadUsers()
	{
		System.out.println("LOADING");
		
		ArrayList<JSONObject> loadedUsersList = new ArrayList<JSONObject>();
		JSONArray usernames = users.names();
		System.out.println(usernames.toString());
		
		int i = 0;
        if(usernames != null)
        {
            while(!usernames.isNull(i))
            {
            	loadedUsersList.add(users.getJSONObject(usernames.getString(i)));
                i++;
            }
        }
        
        loadedUsers = loadedUsersList.toArray(new JSONObject[0]);
        
	}
	*/
	
	public void addCustomer(Customer customer)
	{
		customers.add(customer);
		/*
		JSONObject[] newArray = new JSONObject[loadedUsers.length + 1];
		for (int i = 0; i < loadedUsers.length; i++){
	        newArray[i] = loadedUsers[i];
	    }
		newArray[newArray.length - 1] = newUser;
		loadedUsers = newArray;
		*/
	}
	
	public void addEmployee(String email, String name)
	{	
		Employee newEmployee = new Employee(email, name);
		employees.add(newEmployee);
	}
	
	public void updateFile()
	{
		try
        {
			// Write users to file
			JSONObject jsonUsers = new JSONObject();
			for (Customer c : customers)
			{
				jsonUsers.put(c.getUsername(), c.toJSONObject());
			}
			jsonUsers.put("owner", owner.toJSONObject());
			
            FileWriter custWriter = new FileWriter("src/JSONdatabase/users.json");
            custWriter.write(jsonUsers.toString(4));
            custWriter.flush();
            custWriter.close();
            
            // Write employees to file
            JSONObject jsonEmployees = new JSONObject();
            for (Employee e : employees)
            {
            	jsonEmployees.put(e.getEmail(), e.toJSONObject());
            }
            
            FileWriter employeeWriter = new FileWriter("src/JSONdatabase/employees.json");
            employeeWriter.write(jsonEmployees.toString(4));
            employeeWriter.flush();
            employeeWriter.close();
            
            // Write business to file
            JSONObject jsonBusiness = new JSONObject();
            JSONArray jsonServices = new JSONArray();
            for (Service s : services)
            {
            	jsonServices.put(s.toJSONObject());
            }
            jsonBusiness.put("services", jsonServices);
            
            FileWriter serviceWriter = new FileWriter("src/JSONdatabase/business.json");
            serviceWriter.write(jsonBusiness.toString(4));
            serviceWriter.flush();
            serviceWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}

	/*-------------------- Private Methods --------------------*/
	
	/**
	 * @description create an ArrayList containing all employees from the employees JSON file
	 * @return ArrayList<Employee> the list of all employees
	 * @author Drew Nuttall-Smith
	 * @since 3/4/2017
	 **/
	private ArrayList<Employee> initialiseEmployees()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		JSONObject jsonEmployees = JSONUtils.getJSONObjectFromFile(employeesFilepath);
		JSONArray emails = jsonEmployees.names();
		
		// Iterate over each employee
        int i = 0;
        if(emails != null)
        {
            while(!emails.isNull(i))
            {
            	String email = emails.getString(i);
            	String name = jsonEmployees.getJSONObject(email).getString("name");
            	JSONObject jsonRoster = jsonEmployees.getJSONObject(email).getJSONObject("roster");
            	
            	employees.add(new Employee(email, name, jsonRoster));

                i++;
            }
        }
        
        return employees;
		
	}
	
	/**
	 * @description create an ArrayList containing all services offered from the business JSON file
	 * @return ArrayList<Service> the list of all services offered
	 * @author Drew Nuttall-Smith
	 * @since 27/4/2017
	 **/
	private ArrayList<Service> initialiseServices()
	{
		ArrayList<Service> services = new ArrayList<Service>();
		// TODO: add services to the JSON in a business file (in future this will hold all businesses)
		JSONArray jsonServices = JSONUtils.getJSONObjectFromFile(businessFilepath).getJSONArray("services");
		
		// Iterate over each service
		for (int i = 0; i < jsonServices.length(); i++) {
			JSONObject jsonService = jsonServices.getJSONObject(i);
        	String name = jsonService.getString("name");
        	int duration = jsonService.getInt("duration");
        	
        	services.add(new Service(name, duration));
		}
        
        return services;
		
	}
	
	private ArrayList<Customer> initialiseCustomers()
	{
		ArrayList<Customer> customers = new ArrayList<Customer>();
		JSONObject jsonUsers = JSONUtils.getJSONObjectFromFile(usersFilepath);
		JSONArray usernames = jsonUsers.names();
		
		// Iterate over each employee
        int i = 0;
        if(usernames != null)
        {
            while(!usernames.isNull(i))
            {
            	String username = usernames.getString(i);
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
                		Employee bookingEmployee = getEmployee(bookingEmployeeEmail);
                		String bookingServiceName = jsonBookings.getJSONObject(j).getString("service");
                		Service bookingService = getService(bookingServiceName);
                		
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

                i++;
            }
        }
        
        return customers;
	}
	
	private Owner initialiseOwner()
	{
		JSONObject jsonUsers = JSONUtils.getJSONObjectFromFile(usersFilepath);
		JSONArray usernames = jsonUsers.names();
		
		// Iterate over each user
        int i = 0;
        if(usernames != null)
        {
            while(!usernames.isNull(i))
            {
            	String username = usernames.getString(i);
            	if (jsonUsers.getJSONObject(username).getBoolean("isOwner")) {
                	String password = jsonUsers.getJSONObject(username).getString("password");
                	
                	// Return the owner
                	return new Owner(username, password);
            	}

                i++;
            }
        }
        
        return null;
    	
	}

	

}
