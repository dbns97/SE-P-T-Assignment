package application.controllers;
import application.models.*;
import application.views.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OwnerMenuController {

	private OwnerMenu om;
	private PublicMenu pm;
	private Business business;
	
	@FXML
	private Label businessLabel;
	@FXML
	private Button addEmployee;
	@FXML
	private Button logout;
	
	public void setBusinessLabel(String businessName)
	{
		this.businessLabel.setText(businessName);
		this.businessLabel.setFont(Font.font(business.getFont(), 18));
	}
	
	public void setOwnerMenu(OwnerMenu om)
	{
		this.om = om;
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
		om.showOwnerMenu();
	}
	
	public void showAddEmployee()
	{
		om.showAddEmployee();
	}
	
	public void showAddShift()
	{
		om.showAddShift();
	}
	
	public void showAddService()
	{
		om.showAddService();
	}
	
	public void handleViewBookings()
	{
		om.showViewBookings();
	}
	
	public void handleViewEmployeeAvailability()
	{
		om.showViewEmployeeAvailability();
	}
	
	public void handleMakeBooking()
	{
		om.showMakeBookings();
	}
	
	public void handleChangeStyles()
	{
		om.showChangeStyles();
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
