package application;

public class CustomerBookingTimesController {

	private CustomerMenu cm;
	private PublicMenu pm;
	private Buisness buisness;

	public void setCustomerMenu(CustomerMenu cm)
	{
		this.cm = cm;
	}

	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}

	public void setBuisness(Buisness buisness)
	{
		this.buisness = buisness;
	}

	public void handleBack()
	{
		cm.showCustomerMenu();
	}
}
