package application;

public class Customer extends User {

	private String username; 
	private String password; 
	private String address;
	private int contactNumber;
	//An array of bookings which are start and finishing times
	private int[][] bookingTimes;
	
	@Override
	public String toJSONString()
	{
		String output = "";
		
		return output;
	}
}
