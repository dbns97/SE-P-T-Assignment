package application;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class Employee {

	private String email;
	private String name;
	ArrayList<Shift> shifts = new ArrayList();

	public Employee(String email, String name)
	{
		this.email = email;
		this.name = name;
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

		if (shifts.size() == 0) shifts.add(newShift);

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
		newEmployee.put("name", name);

		JSONArray jsonShift = new JSONArray();
		for(Shift shift : shifts) {
			jsonShift.put(shift.toJSONObject());
		}

		return newEmployee;
	}

}