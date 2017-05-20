package application.models;

import java.util.Calendar;

public class Service {
	
	private String name;
	private int duration; // Length of time of a service in minutes

	public Service(String name, int duration)
	{
		this.name = name;
		this.duration = duration;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	/**
	 * @description return the end time for a booking of this service given a specific start time
	 * @param startTime the start time of the booking
	 * @return Calendar the end time of the booking
	 * @author Drew Nuttall-Smith
	 * @since 27/4/2017
	 **/
	public Calendar getEndTime(Calendar startTime)
	{
		Calendar endTime = Calendar.getInstance();
		// Convert duration to milliseconds
		int timeToAdd = duration * 60 * 1000;
		
		// Set endTime calendar based on startTime calendar
		endTime.setTimeInMillis(startTime.getTimeInMillis() + timeToAdd);
		
		return endTime;
	}
	
	/**
	 * @description return the start time for a booking of this service given a specific end time
	 * @param endTime the end time of the booking
	 * @return Calendar the start time of the booking
	 * @author Drew Nuttall-Smith
	 * @since 27/4/2017
	 **/
	public Calendar getStartTime(Calendar endTime)
	{
		Calendar startTime = Calendar.getInstance();
		// Convert duration to milliseconds
		int timeToSubtract = duration * 60 * 1000;
		
		// Set startTime calendar based on endTime calendar
		startTime.setTimeInMillis(endTime.getTimeInMillis() - timeToSubtract);
		
		return startTime;
	}

}
