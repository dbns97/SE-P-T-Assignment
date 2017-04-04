package application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;

public class Employee {

	private String email;
	private String name;
	ArrayList<Shift> shifts;

	public Employee(String email, String name)
	{
		this.email = email;
		this.name = name;
		this.shifts = new ArrayList<Shift>();
	}
	
	public Employee(String email, String name, JSONArray shifts)
	{
		this.email = email;
		this.name = name;
		this.shifts = initialiseShifts(shifts);
	}
	
	public String getEmail()
	{
		return email;
	}

	public String getName()
	{
		return name;
	}

	public Shift getShift(int i)
	{
		return shifts.get(i);
	}

	public ArrayList<Shift> getShifts()
	{
		return shifts;
	}

	/**
	 * @description add shift in order into shifts
	 * @param start string representing start time of shift
	 * @param end string representing end time of shift
	 * @return boolean whether shift was added to shifts
	 * @author Drew Nuttall-Smith
	 * @since 2/4/2017
	 **/
	public boolean addShift(String start, String end)
	{
		Shift newShift = new Shift(start, end);
		newShift.setEmployee(this);

		if (shifts.size() == 0) {
			shifts.add(newShift);
			return true;
		}

		// Compare new shift to all existing shifts
		for(int i = 0; i < shifts.size(); i++) {
			// Check if the newShift starts before the current one being checked
			if (shifts.get(i).getStart().after(newShift.getStart()))
			{
				// Check that the newShift also ends before the current one starts
				if (shifts.get(i).getStart().after(newShift.getEnd()))
				{
					shifts.add(i, newShift);
					return true;
				} else {
					System.out.println("Shift clashes with an existing shift!");
					return false;
				}
			} else if (i == (shifts.size() - 1))
			{
				// It starts after the last existing shift starts. Check they don't overlap
				if (shifts.get(i).getEnd().before(newShift.getStart()))
				{
					shifts.add(newShift);
					return true;
				}
			}
		}

		return false;
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

		JSONArray jsonShifts = new JSONArray();
		for(Shift shift : shifts) {
			jsonShifts.put(shift.toJSONObject());
		}
		newEmployee.put("shifts", jsonShifts);

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

}
