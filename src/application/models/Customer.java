package application.models;
import application.views.*;
import application.controllers.*;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Customer extends User {

	private String username;
	private String name;
	private String password; 
	private ArrayList<Booking> bookings;
	
	public Customer(String username, String name, String password)
	{
		this.username = username;
		this.name = name;
		this.password = password;
		bookings = new ArrayList<Booking>();
	}
	
	public Customer(String username, String name, String password, ArrayList<Booking> bookings)
	{
		this.username = username;
		this.name = name;
		this.password = password;
		this.bookings = bookings;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Booking> getBookings()
	{
		return bookings;
	}
	
	public void addBooking(Booking booking)
	{
		bookings.add(booking);
	}

	public JSONObject toJSONObject()
	{
		JSONObject newUser = new JSONObject();
		newUser.put("password", password);
		newUser.put("name", name);
		newUser.put("isOwner", false);
		
		JSONArray jsonBookings = new JSONArray();
		for (Booking b : bookings)
		{
			jsonBookings.put(b.toJSONObject());
		}
		newUser.put("bookings", jsonBookings);
		
		return newUser;
	}
}
