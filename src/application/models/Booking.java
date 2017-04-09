package application.models;
import application.views.*;
import application.controllers.*;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {

	private static SimpleDateFormat sdf = new SimpleDateFormat("EEE dd:MM:yyyy HH:mm");
	private Date start;
	private Date end;
	private Shift shift;

	public Booking(Date start, Date end, Shift shift)
	{
		this.start = start;
		this.end = end;
		this.shift = shift;
	}

	public Booking(String start, String end, Shift shift)
	{
		try
		{
			this.start = sdf.parse(start);
			this.end = sdf.parse(end);
			this.shift = shift;
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setShift(Shift shift)
	{
		this.shift = shift;
	}
	
	public Shift getShift()
	{
		return shift;
	}

	public Employee getEmployee()
	{
		return shift.getEmployee();
	}

	public Date getStart()
	{
		return start;
	}

	public Date getEnd()
	{
		return end;
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
		System.out.println("Writing new shift");
		JSONObject jsonShift = new JSONObject();

		jsonShift.put("start", sdf.format(start));
		jsonShift.put("end", sdf.format(end));

		return jsonShift;
	}
}
