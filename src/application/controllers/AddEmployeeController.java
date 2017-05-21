package application.controllers;
import application.models.*;
import application.views.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class AddEmployeeController {
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
		DatabaseHandler.writeBusinessToFile(business);
		om.showOwnerMenu();
		return true;
        
	}

}
