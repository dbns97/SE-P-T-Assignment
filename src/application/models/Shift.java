package application.models;
import application.views.*;
import application.controllers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Shift {

	public static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	private Date start;
	private Date end;
	private Employee employee;

	public Shift(Date start, Date end)
	{
		this.start = start;
		this.end = end;
		this.employee = null;
	}

	public Shift(String start, String end)
	{
		try
		{
			this.start = sdf.parse(start);
			this.end = sdf.parse(end);
			this.employee = null;
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}

	public Employee getEmployee()
	{
		return employee;
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

}
