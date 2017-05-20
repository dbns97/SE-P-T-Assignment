package application.models;
import application.views.*;
import application.controllers.*;

public class Owner extends User {

	private String username;
	private String password;
	
	public Owner(String username, String password)
	{ 
		this.username = username;
		this.password = password; 
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
}
