package application.models;
import application.views.*;
import javafx.scene.paint.Color;
import application.controllers.*;

import java.util.ArrayList;

public class Business {
	private String name;
	private Owner owner;
	private ArrayList<Customer> customers;
	private ArrayList<Employee> employees;
	private ArrayList<Service> services;
	private Color backgroundColor;
	private Color fontColor;
	
	public Business(String name)
	{
		this.name = name;
		owner = DatabaseHandler.getOwner(name);
		employees = DatabaseHandler.getEmployees(name);
		services = DatabaseHandler.getServices(name);
		customers = DatabaseHandler.getCustomers(this);
		backgroundColor = DatabaseHandler.getBackgroundColor(name);
		fontColor = DatabaseHandler.getFontColor(name);
	}
	
	public Business(String name, Owner owner, ArrayList<Employee> employees, ArrayList<Service> services, ArrayList<Customer> customers)
	{
		this.name = name;
		this.owner = owner;
		this.employees = employees;
		this.services = services;
		this.customers = customers;
		this.backgroundColor = Color.rgb(244, 244, 244);
		this.fontColor = Color.rgb(50, 50, 50);
	}
	
	public String getName()
	{
		return name;
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
	
	public Color getBackgroundColor()
	{
		return backgroundColor;
	}
	
	public Color getFontColor()
	{
		return fontColor;
	}
	
	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}
	
	public void setFontColor(Color fontColor)
	{
		this.fontColor = fontColor;
	}
	
	/**
	 * @description add a service to the business
	 * @param name the name of the service
	 * @param duration the duration of the service in minutes
	 * @return boolean whether the operation was successful
	 * @author Drew Nuttall-Smith
	 * @since 9/5/2017
	 **/
	public boolean addService(String serviceName, int serviceDuration)
	{
		// Check if service name is already in use
		for (int i = 0; i < services.size(); i++) {
			if (services.get(i).getName().equals(serviceName)) {
				return false;
			}
		}
		services.add(new Service(serviceName, serviceDuration));
		return true;
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

}
