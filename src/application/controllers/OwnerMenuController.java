package application.controllers;
import application.models.*;
import application.views.*;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONArray;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OwnerMenuController {
	
	final static Logger logger = LogManager.getLogger(OwnerMenuController.class.getName());

	private OwnerMenu om;
	private PublicMenu pm;
	private Business business;
	
	@FXML
	private Button addEmployee;
	@FXML
	private Button logout;
	
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
	
	public void handleLogout()
	{
		Stage stage = (Stage) logout.getScene().getWindow();
        try
        {
        	pm.start(stage, business);
        } catch(Exception e) {
        	logger.warn("problem with logging out of owner menu");
			e.printStackTrace();
		}
	}
}
