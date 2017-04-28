package application.controllers;

import java.util.ArrayList;

import application.models.Business;
import application.views.CustomerMenu;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CustomerMakeBookingController 
{
	private CustomerMenu cm;
	private Business business;
	
	@FXML
    private ChoiceBox<String> day;
	@FXML
    private ChoiceBox<String> time;
	@FXML
    private ChoiceBox<String> service;
	@FXML
    private ChoiceBox<String> employee;
	
	@FXML
	private AnchorPane dayBox;
	@FXML
	private AnchorPane timeBox;
	@FXML
	private AnchorPane serviceBox;
	@FXML
	private AnchorPane employeeBox;
	
	@FXML
	private Button confirm;
	
	@FXML
	private Label errorLabel;
	
	public void setMainMenu(CustomerMenu cm)
	{
		this.cm = cm;
	}
	
	public void handleBack()
	{
		cm.showCustomerMenu();
	}
	
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	/*
	 * these set future choice box to invisable so user cannot enter data yet
	 */
	public void setEmployeeBox()
	{
		employeeBox.setVisible(false);
	}
	public void setDayBox()
	{
		dayBox.setVisible(false);
	}
	public void setTimeBox()
	{
		timeBox.setVisible(false);
	}
	public void setConfirmButton()
	{
		confirm.setVisible(false);
	}
	
	// finds the services in the data base and adds to choice box
	public void setServiceChoiceBox()
	{
		Service services = business.getServices();
		ArrayList<String> BusServices = new ArrayList();
		BusServices.add("Choose Service");
		
		for( Services s : services )
		{
			BusServices.add( s.getNames() );
		}
		
		service.setItems( FXCollections.observableArrayList( BusServices ) );
		service.setValue("Choose Service");

	}
	
	// finds the employee in the data base and adds to choice box
	public void setEmployeeChoiceBox()
	{
		setEmployeeChoiceBox();
		employeeBox.setVisible(true);
		
		employee.setItems(FXCollections.observableArrayList("Choose Employee", "", ""));
		employee.setValue("Choose Employee");
		
		setDayChoiceBox();
		
	}
	
	// should only shows days that employee is working and does that service
	public void setDayChoiceBox()
	{
		day.setItems(FXCollections.observableArrayList( ));
		day.setValue("Choose Day");
		
		setTimeChoiceBox();
		
	}
	
	// only show available times for that day and wont end in another bookings start time
	public void setTimeChoiceBox()
	{
		time.setItems(FXCollections.observableArrayList( ));
		time.setValue("Choose Time");
		
		
	}
	
	
	
	public void handleMakeBooking()
	{
		
	}

}
