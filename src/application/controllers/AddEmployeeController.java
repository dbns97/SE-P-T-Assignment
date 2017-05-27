package application.controllers;
import application.models.*;
import application.views.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class AddEmployeeController
{
	private static final Logger logger = LogManager.getLogger(AddEmployeeController.class.getName());

	private OwnerMenu om;
	@FXML
	private Label heading;
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
	
	public void setHeading()
	{
		System.out.println(heading);
		heading.setFont(Font.font(business.getFont(), 18));
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
		String errorMessage = checkEmployeeDetails(email.getText(), name.getText(), business);
		if(errorMessage != null)
		{
			errorLabel.setText(errorMessage);
			return false;
		}

		business.addEmployee(email.getText(), name.getText());

		errorLabel.setText("Successfully registered new employee");
		DatabaseHandler.writeBusinessToFile(business);
		om.showOwnerMenu();
		logger.info("employee added");
		return true;  
	}

	//Checks the email and name using regex and iterating through existing employees
	public String checkEmployeeDetails(String email, String name, Business business)
	{
		// Check name format
		if(name.trim().isEmpty() || !(name.matches("[a-zA-Z ,.'-]+")))
		{
			logger.debug("invalid email entered : {}", email);
			return new String("Please enter a valid name");
		}

		// Check email format
		if(email.isEmpty() || !(email).matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+"))
		{
			logger.debug("invalid email entered : {}", email);
			return new String("Please enter a valid email");
		}

		// Check if employee already exists with supplied email
		if (business.getEmployee(email) != null)
		{
			logger.debug("existing employee already has this email : {}", email);
			return new String("Employee already exists with that email");
		}
		return null;
	}

}
