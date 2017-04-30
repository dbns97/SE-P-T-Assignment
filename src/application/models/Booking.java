package application.models;
import application.views.*;
import application.controllers.*;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {

	private static SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
	private Date start;
	private Date end;
	private Employee employee;
	private Customer customer;
	private Service service;

	public Booking(Date start, Date end, Employee employee, Customer customer, Service service)
	{
		this.start = start;
		this.end = end;
		this.employee = employee;
		this.customer = customer;
		this.service = service;
	}

	public Booking(String start, String end, Employee employee, Service service)
	{
		try
		{
			this.start = sdf.parse(start);
			this.end = sdf.parse(end);
			this.employee = employee;
			this.service = service;
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Employee getEmployee()
	{
		return employee;
	}
	
	public Customer getCustomer()
	{
		return customer;
	}
	
	public Service getService()
	{
		return service;
	}

	public Date getStart()
	{
		return start;
	}

	public Date getEnd()
	{
		return end;
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}
	
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	
	public void setService(Service service)
	{
		this.service = service;
	}

	public static SimpleDateFormat getSdf()
	{
		return sdf;
	}

	/**
	 * @description create a JSONObject to represent the shift
	 * @return JSONObject the shift as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 2/4/2017
	 **/
	public JSONObject toJSONObject()
	{
		JSONObject jsonBooking = new JSONObject();

		jsonBooking.put("start", sdf.format(start));
		jsonBooking.put("end", sdf.format(end));
		jsonBooking.put("customer", customer.getUsername());
		jsonBooking.put("employee", employee.getEmail());
		jsonBooking.put("service", service.getName());

		return jsonBooking;
	}
}
