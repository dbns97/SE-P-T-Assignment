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
	private String businessFilepath  = "../../JSONdatabase/businesses.json";
	
	private String name;
	private Owner owner;
	private ArrayList<Customer> customers;
	private ArrayList<Employee> employees;
	private ArrayList<Service> services;
	
	public Business(String name)
	{
		this.name = name;
		owner = initialiseOwner();
		employees = initialiseEmployees();
		services = initialiseServices();
		customers = initialiseCustomers();
	}
	
	public Business(String name, Owner owner, ArrayList<Employee> employees, ArrayList<Service> services, ArrayList<Customer> customers)
	{
		this.name = name;
		this.owner = owner;
		this.employees = employees;
		this.services = services;
		this.customers = customers;
	}
	
	// This needs to be changed now that the main constructor takes a String parameter
	/*
	//Used in testing to change where the Business reads in the users
	public Business(String usersFilepath)
	{
		setUsersFilepath(usersFilepath);
		owner = initialiseOwner();
		employees = initialiseEmployees();
		customers = initialiseCustomers();
	}
	*/
	
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
	
	public void addCustomer(Customer customer)
	{
		customers.add(customer);
	}
	
	public void addEmployee(String email, String name)
	{	
		Employee newEmployee = new Employee(email, name);
		employees.add(newEmployee);
	}
	
	/**
	 * @description write all objects to file
	 * @return void
	 * @author Drew Nuttall-Smith
	 * @since 8/5/2017
	 **/
	public void updateFile()
	{
		try
        {	
			// Get all users for this business
			JSONObject jsonUsers = new JSONObject();
			for (Customer c : customers)
			{
				jsonUsers.put(c.getUsername(), c.toJSONObject());
			}
			jsonUsers.put(owner.getUsername(), owner.toJSONObject());
			
			// Write users to file
			JSONObject allJsonUsers = JSONUtils.getJSONObjectFromFile(usersFilepath);
			allJsonUsers.put(this.name, jsonUsers);
            FileWriter custWriter = new FileWriter("src/JSONdatabase/users.json");
            custWriter.write(allJsonUsers.toString(4));
            custWriter.flush();
            custWriter.close();
            

            // Get all employees for this business
            JSONObject jsonEmployees = new JSONObject();
            for (Employee e : employees)
            {
            	jsonEmployees.put(e.getEmail(), e.toJSONObject());
            }
            
            // Write employees to file
			JSONObject allJsonEmployees = JSONUtils.getJSONObjectFromFile(employeesFilepath);
			allJsonEmployees.put(this.name, jsonEmployees);
            FileWriter employeeWriter = new FileWriter("src/JSONdatabase/employees.json");
            employeeWriter.write(allJsonEmployees.toString(4));
            employeeWriter.flush();
            employeeWriter.close();
            
            // Get the details for this business
            JSONObject jsonBusiness = new JSONObject();
            JSONArray jsonServices = new JSONArray();
            for (Service s : services)
            {
            	jsonServices.put(s.toJSONObject());
            }
            jsonBusiness.put("services", jsonServices);
           
            // Write business to file
			JSONObject allJsonBusinesses = JSONUtils.getJSONObjectFromFile(businessFilepath);
			allJsonBusinesses.put(this.name, jsonBusiness);
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

	/*-------------------- Private Methods --------------------*/
	
	/**
	 * @description create an ArrayList containing all employees from the employees JSON file
	 * @return ArrayList<Employee> the list of all employees
	 * @author Drew Nuttall-Smith
	 * @since 8/5/2017
	 **/
	private ArrayList<Employee> initialiseEmployees()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		JSONObject jsonEmployees = JSONUtils.getJSONObjectFromFile(employeesFilepath).getJSONObject(this.name);
		String[] emails = JSONObject.getNames(jsonEmployees);
		
		if (emails == null) return employees;
		
		// Iterate over each employee
		for(int i = 0; i < emails.length; i++) {
        	String email = emails[i];
        	String name = jsonEmployees.getJSONObject(email).getString("name");
        	JSONObject jsonRoster = jsonEmployees.getJSONObject(email).getJSONObject("roster");
        	
        	employees.add(new Employee(email, name, jsonRoster));
		}
        
        return employees;
	}
	
	/**
	 * @description create an ArrayList containing all services offered from the business JSON file
	 * @return ArrayList<Service> the list of all services offered
	 * @author Drew Nuttall-Smith
	 * @since 8/5/2017
	 **/
	private ArrayList<Service> initialiseServices()
	{
		ArrayList<Service> services = new ArrayList<Service>();
		JSONArray jsonServices = JSONUtils.getJSONObjectFromFile(businessFilepath).getJSONObject(this.name).getJSONArray("services");
		
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
	 * @description create an ArrayList containing all customers of the business
	 * @return ArrayList<Customer> the list of all customers
	 * @author Drew Nuttall-Smith
	 * @since 8/5/2017
	 **/
	private ArrayList<Customer> initialiseCustomers()
	{
		ArrayList<Customer> customers = new ArrayList<Customer>();
		JSONObject jsonUsers = JSONUtils.getJSONObjectFromFile(usersFilepath).getJSONObject(this.name);
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
		}
        
        return customers;
	}
	
	/**
	 * @description initialise the owner object
	 * @return Owner the owner object
	 * @author Drew Nuttall-Smith
	 * @since 8/5/2017
	 **/
	private Owner initialiseOwner()
	{
		// Get usernames of all users in selected business business
		JSONObject jsonUsers = JSONUtils.getJSONObjectFromFile(usersFilepath).getJSONObject(this.name);
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

}
