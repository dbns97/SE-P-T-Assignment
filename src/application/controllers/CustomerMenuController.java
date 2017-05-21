package application.controllers;
import application.models.*;
import application.views.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
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
		this.businessLabel.setFont(Font.font(business.getFont(), 18));
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
		DatabaseHandler.writeBusinessToFile(business);

        try
        {
    		Stage stage = (Stage) logout.getScene().getWindow();
        	pm.start(stage);
        } catch(Exception e) {
			e.printStackTrace();
		}
	}
}
