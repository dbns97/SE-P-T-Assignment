package application.controllers;
import application.models.*;
import application.views.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
//jkgasdjhags
public class AddEmployeeController {
	private OwnerMenu om;
	@FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private Button addEmployeeButton;
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
	
	public boolean handleAddEmployee()
	{
		// Check name format
		if(name.getText().trim().isEmpty() || !(name.getText().matches("[a-zA-Z ,.'-]+")))
		{
			errorLabel.setText("Please enter a valid name");
			return false;
		}
		
		// Check email format
		if(email.getText().isEmpty() || !(email.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+")))
		{
			errorLabel.setText("Please enter a valid email");
			return false;
		}
		
		// Check if employee already exists with supplied email
		if (business.getEmployee(email.getText()) != null)
		{
        	errorLabel.setText("Employee already exists with that email");
            return false;
		}
		
		business.addEmployee(email.getText(), name.getText());
		
		errorLabel.setText("Successfully registered new employee");

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

		return true;
        
	}

}
