package application.controllers;
import application.models.*;
import application.views.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class AddServiceController {
	private OwnerMenu om;
	@FXML
	private Label heading;
    @FXML
    private TextField name;
    @FXML
    private TextField duration;
    @FXML
    private Button addShiftButton;
    @FXML
    private Label errorLabel;
    private Business business;
    
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
	
	public boolean handleAddService()
	{	
		// Check name format
		if(name.getText().trim().isEmpty() || !(name.getText().matches("[a-zA-z0-9 ,.'-]+")))
		{
			errorLabel.setText("Please enter a valid name");
			return false;
		}
		
		// Check duration format
		if(duration.getText().trim().isEmpty() || !(duration.getText().matches("[0-9]{1,3}")))
		{
			errorLabel.setText("Please enter a valid duration in minutes (maximum 3 digits)");
			return false;
		}
		
		boolean success = business.addService(name.getText(), Integer.parseInt(duration.getText()));
		
		if (success) {
			errorLabel.setText("Successfully created service: " + name.getText());
			DatabaseHandler.writeBusinessToFile(business);
			om.showOwnerMenu();
			return true;
		} else {
			errorLabel.setText("Failed to create service. Name: " + name.getText() + " already in use");
			return false;
		} 
	}

}
