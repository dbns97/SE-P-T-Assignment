package application;

import org.json.JSONObject;
import java.util.Date;

public class Shift {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private Date start;
	private Date end;

	public Shift(Date start, Date end)
	{
		this.start = start;
		this.end = end;
	{

	public Shift(String start, String end)
	{
		this.start = sdf.parse(start);
		this.end = sdf.parse(end);
	{

	/**
	 * @description create a JSONObject to represent the shift
	 * @return JSONObject the shift as a JSONObject
	 * @author Drew Nuttall-Smith
	 * @since 2/4/2017
	 **/
	public JSONObject toJSONObject()
	{
		JSONObject jsonShift = new JSONObject();

		jsonShift.add("start", start.toString());
		jsonShift.add("end", end.toString());

		return jsonShift;
	}
}
