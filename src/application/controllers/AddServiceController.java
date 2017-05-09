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
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddServiceController {
	private OwnerMenu om;
    @FXML
    private TextField name;
    @FXML
    private TextField duration;
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
			return true;
		} else {
			errorLabel.setText("Failed to create service. Name: " + name.getText() + " already in use");
			return false;
		} 
	}

}
