package application;

public class Customer extends User {

	private String username; 
	private String password; 
	private String address;
	private int contactNumber;
	//An array of bookings which are start and finishing times
	private int[][] bookingTimes;
	
	public Customer(String username, String password, String address, int contactNumber)
	{
		this.username = username;
		this.password = password;
		this.address = address;
		this.contactNumber = contactNumber;
	}
	@Override
	public String toJSONString()
	{
		String output = "";
		
		return output;
	}
}
