package application.controllers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.models.*;
import application.views.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomerMenuController {

	final static Logger logger = LogManager.getLogger(CustomerMenuController.class.getName());
	
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
		Stage stage = (Stage) logout.getScene().getWindow();
        try
        {
        	pm.start(stage, business);
        } catch(Exception e) {
        	logger.warn("problem logging out from customer menu");
			e.printStackTrace();
		}
	}
}
