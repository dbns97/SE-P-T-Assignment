package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomerMenuController {

	private CustomerMenu cm;
	private PublicMenu pm;
	private Business business;
	
	@FXML
	private Button logout;

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

	public void handleViewBookings()
	{
		cm.showViewBookings();
	}

	public void handleLogout()
	{
		Stage stage = (Stage) logout.getScene().getWindow();
        try
        {
        	pm.start(stage);
        } catch(Exception e) {
			e.printStackTrace();
		}
	}
}
