package application;

public class CustomerBookingTimesController {

	private CustomerMenu cm;
	private PublicMenu pm;
	private Business business;
	
	public void setCustomerMenu(CustomerMenu cm)
	{
		this.cm = cm;
	}
	
	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}
	
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public void handleBack()
	{
		cm.showCustomerMenu();
	}
}
