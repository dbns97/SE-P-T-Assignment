package application.models;
import application.views.*;
import application.controllers.*;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;

public class Employee {

	private String email;
	private String name;
	private HashMap<String,Shift> roster;

	public Employee(String email, String name)
	{
		this.email = email;
		this.name = name;
		this.roster = new HashMap<String,Shift>(7);
	}
	
	public Employee(String email, String name, JSONObject roster)
	{
		this.email = email;
		this.name = name;
		this.roster = initialiseRoster(roster);
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
		return roster.get(day);
	}

	public HashMap<String,Shift> getRoster()
	{
		return roster;
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
		
		roster.put(day, newShift);

		return true;
	}

	/**
	 * @description create a JSONObject to represent the employee
	 * @return JSONObject the employee as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 2/4/2017
	 **/
	public JSONObject toJSONObject()
	{
		JSONObject newEmployee = new JSONObject();
		
		newEmployee.put("email", email);
		newEmployee.put("name", name);
		
		JSONObject jsonRoster = new JSONObject();
		for (HashMap.Entry<String,Shift> entry : roster.entrySet()) {
		    jsonRoster.put(entry.getKey(), entry.getValue().toJSONObject());
		}
		newEmployee.put("roster", jsonRoster);

		return newEmployee;
	}
	
	/*-------------------- Private Methods --------------------*/
	
	/**
	 * @description create an ArrayList containing all shifts from a JSONArray of shifts
	 * @param jsonShifts A JSONArray of shifts
	 * @return ArrayList<Shift> the list of all shifts
	 * @author Drew Nuttall-Smith
	 * @since 3/4/2017
	 **/
	/*
	private ArrayList<Shift> initialiseShifts(JSONArray jsonShifts)
	{
    	ArrayList<Shift> shifts = new ArrayList<Shift>();
    	
		// Iterate over each shift
        int i = 0;
        if(jsonShifts != null)
        {
            while(!jsonShifts.isNull(i))
            {
            	String start = jsonShifts.getJSONObject(i).getString("start");
            	String end = jsonShifts.getJSONObject(i).getString("end");
            	Shift newShift = new Shift(start, end);
            	newShift.setEmployee(this);
            	shifts.add(newShift);
                i++;
            }
        }
        
        return shifts;
	}
	*/

	/**
	 * @description create a HashMap of a roster from a JSONObject of a roster
	 * @param jsonRoster A JSONObjects of a roster
	 * @return HashMap<String,Shift> the roster
	 * @author Drew Nuttall-Smith
	 * @since 8/4/2017
	 **/
	private HashMap<String,Shift> initialiseRoster(JSONObject jsonRoster)
	{
		HashMap<String,Shift> roster = new HashMap<String,Shift>();
        
		JSONArray days = jsonRoster.names();
		
        int i = 0;
        if(days != null)
        {
            while(!days.isNull(i))
            {
            	JSONObject jsonShift = jsonRoster.getJSONObject(days.getString(i));
            	String start = jsonShift.getString("start");
            	String end = jsonShift.getString("end");
            	Shift newShift = new Shift(start, end);
            	newShift.setEmployee(this);
            	
            	roster.put(days.getString(i), newShift);
                i++;
            }
        }
        
        return roster;
	}
}
