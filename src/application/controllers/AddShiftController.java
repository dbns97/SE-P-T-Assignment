package application.controllers;
import application.models.*;
import application.views.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddShiftController {
	private OwnerMenu om;
	@FXML
    private TextField email;
	@FXML
    private TextField date;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private Button addShiftButton;
    @FXML
    private Label errorLabel;
    private Business business;
    
	public void setMainMenu(OwnerMenu om)
	{
		this.om = om;
	}
	
	public void handleBack()
	{
		om.showOwnerMenu();
	}
	
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public boolean handleAddShift()
	{	
		SimpleDateFormat inputSdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		// Check email format
		if(email.getText().isEmpty() || !(email.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+")))
		{
			errorLabel.setText("Please enter a valid email");
			return false;
		}
        
		// Check date format
		if(date.getText().trim().isEmpty() || !(date.getText().matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}")))
		{
			errorLabel.setText("Please enter a valid date");
			return false;
		}
		
		// Check start time format
		if(startTime.getText().trim().isEmpty() || !(startTime.getText().matches("[0-9]{1,2}")))
		{
			errorLabel.setText("Please enter a valid start time");
			return false;
		}
		
		// Check end time format
		if(endTime.getText().trim().isEmpty() || !(endTime.getText().matches("[0-9]{1,2}")))
		{
			errorLabel.setText("Please enter a valid end time");
			return false;
		}
		
		Employee employee = business.getEmployee(email.getText());
		
		// Check that employee exists
		if (employee == null)
		{
			errorLabel.setText("No employee exists with that email");
			return false;
		}
        
		// Add the shift to the employee
		String startStr = String.format("%s %02d:00:00", date.getText(), Integer.parseInt(startTime.getText()));
		String endStr = String.format("%s %02d:00:00", date.getText(), Integer.parseInt(endTime.getText()));
		
		try {
			startStr = Shift.getSdf().format(inputSdf.parse(startStr));
			endStr = Shift.getSdf().format(inputSdf.parse(endStr));
		} catch (ParseException e) {
			e.printStackTrace();
        	return false;
		}
		
        if (employee.addShift(startStr, endStr) == false)
        {
        	errorLabel.setText("Could not add shift to employee");
        	return false;
        }
		
		errorLabel.setText("Successfully created shift for employee with email: " + email.getText());
		/*
		try
        {
            FileWriter employeeWriter = new FileWriter("src/JSONdatabase/employees.json");
            employeeWriter.write(employees.toString(4));
            employeeWriter.flush();
            employeeWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		*/
		//System.out.println(JSONUtils.getJSONObjectFromFile("employees.json").toString());
		return true;
        
	}

}
