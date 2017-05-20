package application.models;
import application.views.*;
import application.controllers.*;

import java.util.HashMap;

public class Employee {

	private String email;
	private String name;
	private HashMap<Day,Shift> roster;

	public Employee(String email, String name)
	{
		this.email = email;
		this.name = name;
		this.roster = initialiseRoster(); 
	}
	
	public Employee(String email, String name, HashMap<Day,Shift> roster)
	{
		this.email = email;
		this.name = name;
		this.roster = roster; 
	}
	
	public String getEmail()
	{
		return email;
	}

	public String getName()
	{
		return name;
	}
	
	public Shift getShift(String day)
	{
		return roster.get(Day.valueOf(day));
	}
	
	public Shift getShift(Day day)
	{
		return roster.get(day);
	}

	public HashMap<Day,Shift> getRoster()
	{
		return roster;
	}
	
	public void setRoster(HashMap<Day,Shift> roster)
	{
		this.roster = roster;
	}

	/**
	 * @description set the shift for a day
	 * @param day day of week of the shift
	 * @param start string representing start time of shift
	 * @param end string representing end time of shift
	 * @return boolean whether shift was added to shifts
	 * @author Drew Nuttall-Smith
	 * @since 8/4/2017
	 **/
	public boolean setShift(String day, String start, String end)
	{
		Shift newShift = new Shift(start, end);
		newShift.setEmployee(this);
		
		roster.put(Day.valueOf(day.toUpperCase()), newShift);

		return true;
	}
	
	/*-------------------- Private Methods --------------------*/

	/**
	 * @description create a roster HashMap with all shifts set to null
	 * @return HashMap<Day,Shift> the roster
	 * @author Drew Nuttall-Smith
	 * @since 9/4/2017
	 **/
	private HashMap<Day,Shift> initialiseRoster()
	{
		HashMap<Day,Shift> roster = new HashMap<Day,Shift>(7);
		
		for (Day day : Day.values())
		{
			roster.put(day, null);
		}
		
		return roster;
	}

}
