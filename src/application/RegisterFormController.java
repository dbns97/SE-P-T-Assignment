package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterFormController {
	private PublicMenu pm;
	@FXML
    private TextField username;
    @FXML
    private PasswordField password;
	@FXML
    private TextField name;
	@FXML
    private TextField address;
	@FXML
    private TextField contactNumber;
    @FXML
    private Button registerButton;
    @FXML
    private Label errorLabel;
    
	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}
	
	public void handleBack()
	{
		pm.showPublicMenu();
	}
	
	public boolean handleRegister()
	{
		errorLabel.setWrapText(true);
		if(username.getText().trim().isEmpty() || !(username.getText().matches("[a-zA-z0-9]+")))
		{
			errorLabel.setText("Please enter a username without symbols or whitespace");
			return false;
		}
		
		if(password.getText().trim().isEmpty())
		{
			errorLabel.setText("Please enter a password");
			return false;
		}
		
		if(!(name.getText().matches("[a-zA-z ,.'-]+")) || name.getText().trim().isEmpty())
		{
			errorLabel.setText("Please enter a name without numbers or symbols");
			return false;
		}
		
		if(!(address.getText().matches("[a-zA-z0-9 ,.'-]+")) || address.getText().trim().isEmpty())
		{
			errorLabel.setText("Please enter an address without symbols");
			return false;
		}
		
		if(!(contactNumber.getText().matches("[0-9]+")) || contactNumber.getText().trim().isEmpty())
		{
			errorLabel.setText("Please enter a contact number with only numbers");
			return false;
		}
		
		return true;		
		
	}
}
