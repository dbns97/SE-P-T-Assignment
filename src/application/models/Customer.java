package application.models;
import application.views.*;
import application.controllers.*;

import org.json.JSONObject;

public class Customer extends User {

	private String username;
	private String name;
	private String password; 
	private String address;
	private int contactNumber;
	//An array of bookings which are start and finishing times
	//private int[][] bookingTimes;
	private boolean isOwner;
	private int bookingTime;
	private String bookingStatus;
	
	public Customer(String username, String name, String password, String address, int contactNumber)
	{
		this.username = username;
		this.name = name;
		this.password = password;
		this.address = address;
		this.contactNumber = contactNumber;
		//int[][] bookingTimes = {};
		this.isOwner = false;
		this.bookingStatus = "not booked";
		this.bookingTime = -1;
	}
	
	//
	public JSONObject toJSONObject()
	{
		
		JSONObject newUser = new JSONObject();
		newUser.put("password", password);
		newUser.put("name", name);
		newUser.put("address", address);
		newUser.put("contact number", contactNumber);
		newUser.put("booking status", bookingStatus);
		newUser.put("booking time", bookingTime);
		newUser.put("isOwner", isOwner);
		
		return newUser;
	}
}
