package application;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Shift {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private Date start;
	private Date end;

	public Date getStart()
	{
		return start;
	}
	
	public Date getEnd()
	{
		return end;
	}
	
	public Shift(Date start, Date end)
	{
		this.start = start;
		this.end = end;
	}

	public Shift(String start, String end)
	{
		try
		{
		this.start = sdf.parse(start);
		this.end = sdf.parse(end);
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @description create a JSONObject to represent the shift
	 * @return JSONObject the shift as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 2/4/2017
	 **/
	public JSONObject toJSONObject()
	{
		JSONObject jsonShift = new JSONObject();

		jsonShift.put("start", start.toString());
		jsonShift.put("end", end.toString());

		return jsonShift;
	}
}
