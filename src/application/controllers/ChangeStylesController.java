package application.controllers;
import application.models.*;
import application.views.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ChangeStylesController {
	private OwnerMenu om;
    @FXML
    private TextField backgroundRed;
    @FXML
    private TextField backgroundGreen;
    @FXML
    private TextField backgroundBlue;
    @FXML
    private Button changeStylesButton;
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
	
	public boolean handleChangeStyles()
	{	
		// Check formats
		if(
			!(backgroundRed.getText().matches("[0-9]{1,3}")) || backgroundRed.getText().trim().isEmpty() ||
			!(backgroundGreen.getText().matches("[0-9]{1,3}")) || backgroundGreen.getText().trim().isEmpty() ||
			!(backgroundBlue.getText().matches("[0-9]{1,3}")) || backgroundBlue.getText().trim().isEmpty()
		  )
		{
			errorLabel.setText("Please enter a valid color value (0 - 255)");
			return false;
		}
		
		// Get the RGB values for the background color
		int backgroundRedVal = (int) Double.parseDouble(backgroundRed.getText());
		int backgroundGreenVal = (int) Double.parseDouble(backgroundGreen.getText());
		int backgroundBlueVal = (int) Double.parseDouble(backgroundBlue.getText());
			
		Color backgroundColor = Color.rgb(backgroundRedVal, backgroundGreenVal, backgroundBlueVal);
		business.setBackgroundColor(backgroundColor);
		om.getPrimaryStage().getScene().setFill(backgroundColor);
		
		errorLabel.setText("Successfully changed styles");
		DatabaseHandler.writeBusinessToFile(business);
		om.showOwnerMenu();
		return true;
	}

}
