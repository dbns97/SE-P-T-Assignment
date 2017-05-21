package application.controllers;
import application.models.*;
import application.views.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ChangeStylesController {
	private OwnerMenu om;
	@FXML
	private Label heading;
    @FXML
    private TextField backgroundRed;
    @FXML
    private TextField backgroundGreen;
    @FXML
    private TextField backgroundBlue;
    @FXML
    private ChoiceBox<String> font;
    @FXML
    private Button changeStylesButton;
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
	
	public void setFontChoiceBox()
	{
		font.setItems(FXCollections.observableArrayList(Font.getFamilies()));
		font.setValue(business.getFont());
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
		
		business.setFont(font.getValue());
		om.getPrimaryStage().getScene().getRoot().setStyle("-fx-font-family:\"" + font.getValue() + "\"; -fx-background-color: transparent;");
		
		errorLabel.setText("Successfully changed styles");
		DatabaseHandler.writeBusinessToFile(business);
		om.showOwnerMenu();
		return true;
	}

}
