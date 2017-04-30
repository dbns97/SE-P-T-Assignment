package application.controllers;

import java.util.HashMap;

import application.models.Booking;
import application.models.Business;
import application.models.Day;
import application.models.Employee;
import application.models.Shift;
import application.views.OwnerMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class OwnerMakeBookingController {

	
	private OwnerMenu om;
	private Business business;
	
	@FXML
	private ChoiceBox<String> customer;
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
	private Button back;
	@FXML
	private Button confirm;
	@FXML
	private Label errorLabel;
	
	public void setMainMenu(OwnerMenu om)
	{
		this.om = om;
	}
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public void handleBack()
	{
		om.showOwnerMenu();
	}
	
	// these set future choice box to invisible so user cannot enter data until previous information is entered
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
	
	// finds the customers in the data base and adds to choice box
	public void setCustomerChoiceBox() {
		// TODO Auto-generated method stub
		String[] CustomerList = new String[business.getCustomers().size()];
		System.out.println(business.getCustomers().size());
		for (int i = 0 ; i < business.getCustomers().size(); i++)
		{
			CustomerList[i] = business.getCustomers().get(i).getName();
			System.out.println(business.getCustomers().get(i).getName());
		}		
		System.out.println(CustomerList);
		customer.setItems(FXCollections.observableArrayList( CustomerList ) );
	}
	
	// finds the services in the data base and adds to choice box
	public void setServiceChoiceBox()
	{				
		String[] serviceList = new String[business.getServices().size()];
		for (int i = 0 ; i < business.getServices().size(); i++)
		{
			serviceList[i] = business.getServices().get(i).getName();
		}		
		service.setItems(FXCollections.observableArrayList( serviceList ) );
	}
	
	// finds the employee in the data base and adds to choice box
	public void setEmployeeChoiceBox()
	{
		String[] EmployeeList = new String[business.getEmployees().size()];
		for (int i = 0 ; i < business.getEmployees().size(); i++)
		{
			EmployeeList[i] = business.getEmployees().get(i).getName();
		}		
		employee.setItems(FXCollections.observableArrayList( EmployeeList ) );
	}
	
	// should only shows days that employee is working and does that service
	public void setDayChoiceBox()
	{
		// first get employee from data base
		Employee currentEmployee = null;
		for (int i = 0 ; i < business.getEmployees().size(); i++)
		{
			
			if ( employee.getValue().equals(business.getEmployees().get(i).getName()))
			{
				currentEmployee = business.getEmployees().get(i);
			}
	
		}	
		
		HashMap<Day, Shift> empRoster = currentEmployee.getRoster();
		ObservableList<String> dayList = FXCollections.observableArrayList();
		for (Day key : empRoster.keySet()) 
		{
			if (empRoster.get(key)!= null)
			{
				dayList.add(key.toString());
			}			
		}
		
		day.setItems(FXCollections.observableArrayList( dayList ) );		

	}
	
	// only show available times for that day and wont end in another bookings start time
	public void setTimeChoiceBox()
	{
		time.setItems(FXCollections.observableArrayList( "dummy values", "1" , "2", "3" ) );
	}
	
	public void handleServiceChoiceBox()
	{
		System.out.println("service box clicked");
		
		errorLabel.setText("");
		
		if(confirm.isVisible())
			confirm.setVisible(false);
		
		
		if(timeBox.isVisible())
		{
			time.setItems(null);
			timeBox.setVisible(false);
		}
		if(dayBox.isVisible())
		{
			day.setItems(null);
			dayBox.setVisible(false);
		}
		
		
		if( employeeBox.isVisible() == false)
			employeeBox.setVisible(true);
		
		setEmployeeChoiceBox();
	}

	public void handleEmployeeChoiceBox()
	{
		System.out.println("employee box clicked");
		
		errorLabel.setText("");
		
		if(confirm.isVisible())
			confirm.setVisible(false);
		
		
		if(timeBox.isVisible())
		{
			time.setItems(null);
			timeBox.setVisible(false);
		}
		
		
		if( dayBox.isVisible() == false)
			dayBox.setVisible(true);	
		
		setDayChoiceBox();
	}
	
	
	public void handleDayChoiceBox()
	{
		System.out.println("day box clicked");
		
		errorLabel.setText("");
		
		if(confirm.isVisible())
			confirm.setVisible(false);
				
		if( timeBox.isVisible() == false)
			timeBox.setVisible(true);
		
		setTimeChoiceBox();
	}
	
	public void handleTimeChoiceBox()
	{
		System.out.println("time box clicked");
		
		errorLabel.setText("");
		
		if(confirm.isVisible() == false)
			confirm.setVisible(true);
			
	}
	
	public void handleConfirmButton()
	{
		System.out.println("confirm button clicked");
		
		//what will be saved to database
		System.out.println("booking details : \n" 
							+ service.getValue() +  "\n"
							+ employee.getValue() +  "\n"
							+ day.getValue() +  "\n"
							+ time.getValue() +  "\n"							
							);
		
		Boolean madeBooking = checkBooking();
		
		if (madeBooking == true)
		{
			om.showOwnerMenu();
		}
		else
		{
			errorLabel.setText("Problem making booking");
		}
		
	}

	public Boolean checkBooking()
	{
		
		if(service.getValue() == null
		|| employee.getValue() == null
		|| day.getValue() == null 
		|| time.getValue() == null)
		{
			return false;
		}
		else
		{			
			return true;
		}
		
		
	}

}
