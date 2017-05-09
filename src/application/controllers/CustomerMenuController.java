package application.controllers;
import application.models.*;
import application.views.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomerMenuController {

	private CustomerMenu cm;
	private PublicMenu pm;
	private Business business;
	
	@FXML
	private Label businessLabel;
	@FXML
	private Button logout;

	public void setBusinessLabel(String businessName)
	{
		this.businessLabel.setText(businessName);
	}
	
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

	public void handleViewAvailableTimes()
	{
		cm.viewAvailableTimes();
	}
	
	public void handleMakeBookings()
	{
		cm.showMakeBookings();
	}

	public void handleLogout()
	{
		business.updateFile();

        try
        {
    		Stage stage = (Stage) logout.getScene().getWindow();
        	pm.start(stage);
        } catch(Exception e) {
			e.printStackTrace();
		}
	}
}
