package application.controllers;
import application.models.*;
import application.views.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class AddShiftController {
	private OwnerMenu om;
	@FXML
	private Label heading;
	@FXML
    private ChoiceBox<String> email;
	@FXML
    private ChoiceBox<String> day;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private Button addShiftButton;
    @FXML
    private Label errorLabel;
    private Business business;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    
	public void setMainMenu(OwnerMenu om)
	{
		this.om = om;
	}
	
	public void setHeading()
	{
		this.heading.setFont(Font.font(business.getFont(), 18));
	}
	
	public void handleBack()
	{
		om.showOwnerMenu();
	}
	
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public void setDaysChoiceBox()
	{
		day.setItems(FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"));
		day.setValue("Monday");
	}
	
	public void setEmailsChoiceBox()
	{
		ArrayList<Employee> employeeList = business.getEmployees();
		ObservableList<String> emailList = FXCollections.observableArrayList();
		
		for (Employee emp : employeeList)
		{
			emailList.add(emp.getEmail());
		}
		email.setItems(emailList);
		if (employeeList.size() > 0) {
			email.setValue(employeeList.get(0).getEmail());
		}
		
	}
	
	public boolean handleAddShift()
	{	
		// Check start time format
		if(startTime.getText().trim().isEmpty() || !(startTime.getText().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")))
		{
			errorLabel.setText("Please enter a valid start time");
			return false;
		}
		
		// Check end time format
		if(endTime.getText().trim().isEmpty() || !(endTime.getText().matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")))
		{
			errorLabel.setText("Please enter a valid end time");
			return false;
		}
		
		try
		{
			if(sdf.parse(endTime.getText()).before(sdf.parse(startTime.getText())))
			{
				errorLabel.setText("End time is before start time");
				return false;
			}
		}
		catch(ParseException e)
		{
			return false;
		}
		
		Employee employee = business.getEmployee(email.getValue());
		
		// Check that employee exists
		if (employee == null)
		{
			errorLabel.setText("No employee exists with that email");
			return false;
		}
		
        employee.setShift(day.getValue(), startTime.getText(), endTime.getText());
		
		errorLabel.setText("Successfully created shift for employee with email: " + email.getValue());
		DatabaseHandler.writeBusinessToFile(business);
		om.showOwnerMenu();
		return true;
        
	}

}
