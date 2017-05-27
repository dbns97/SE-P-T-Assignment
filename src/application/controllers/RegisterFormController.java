package application.controllers;
import application.models.*;
import application.views.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterFormController {

	final static Logger logger = LogManager.getLogger(RegisterFormController.class.getName());

	private PublicMenu pm;
	@FXML
	private TextField businessName;
	@FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField reenter;
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
    private Business business;

	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}

	public void handleBack()
	{
		pm.showPublicMenu();
	}

	public void setBusiness(Business business)
	{
		this.business = business;
	}

	public Business getBusiness()
	{
		return business;
	}

	public void setErrorLabel(Label l)
	{
		this.errorLabel = l;
	}

	public Label getErrorLabel()
	{
		return this.errorLabel;
	}

	public void setUsername(TextField username)
	{
		this.username = username;
	}

	public void setUsername(String username)
	{
		this.username.setText(username);
	}

	public TextField getUsername()
	{
		return this.username;
	}

	public void setPassword(PasswordField password)
	{
		this.password = password;
	}

	public void setPassword(String password)
	{
		this.password.setText(password);
	}

	public void setReenteredPassword(PasswordField password)
	{
		this.reenter = password;
	}

	public void setReenteredPassword(String password)
	{
		this.reenter.setText(password);
	}

	public void setName(TextField name)
	{
		this.name = name;
	}

	public void setName(String name)
	{
		this.name.setText(name);
	}

	public void setAddress(TextField address)
	{
		this.address = address;
	}

	public void setAddress(String address)
	{
		this.address.setText(address);
	}

	public void setContactNumber(TextField contactNumber)
	{
		this.contactNumber = contactNumber;
	}

	public void setContactNumber(String contactNumber)
	{
		this.contactNumber.setText(contactNumber);
	}

	public void setRegisterButton(Button registerButton)
	{
		this.registerButton = registerButton;
	}

	public boolean handleRegister()
	{
		errorLabel.setWrapText(true);

		// Check if business name entered is valid
		if (DatabaseHandler.businessExists(businessName.getText())) {
			business = new Business(businessName.getText());
		} else {
			errorLabel.setText("Business entered doesn't exist");
			return false;
		}

		if(username.getText().trim().isEmpty() || !(username.getText().matches("[a-zA-z0-9]+")))
		{
			errorLabel.setText("Please enter a username without symbols or whitespace");
			logger.debug("incorrect syntax with username");
			return false;
		}

		if(password.getText().isEmpty())
		{
			errorLabel.setText("Please enter a password");
			return false;
		}

		if(reenter.getText().isEmpty() || !reenter.getText().equals(password.getText()))
		{
			errorLabel.setText("Please re-enter your password");
			return false;
		}

		if(!(name.getText().matches("[a-zA-z ,.'-]+")) || name.getText().trim().isEmpty())
		{
			errorLabel.setText("Please enter a name without numbers or symbols");
			logger.debug("incorrect syntax with entered name");
			return false;
		}

		if(!(address.getText().matches("[a-zA-z0-9 ,.'-]+")) || address.getText().trim().isEmpty())
		{
			errorLabel.setText("Please enter an address without symbols");
			logger.debug("incorrect syntax with address");
			return false;
		}

		if(!(contactNumber.getText().matches("[0-9]+")) || contactNumber.getText().trim().isEmpty())
		{
			errorLabel.setText("Please enter a contact number with only numbers");
			logger.debug("incorrect syntax with contact number");
			return false;
		}

		// Checks the business's customers to see if the username already exists
		for (Customer customer : business.getCustomers())
		{
			if (customer.getUsername().equals(username.getText()))
			{
				errorLabel.setText("Username already exists");
				return false;
			}
		}

		Customer newUser = new Customer(username.getText(), name.getText(), password.getText());

		business.addCustomer(newUser);

		errorLabel.setText("Successfully registered " + username.getText());
		DatabaseHandler.writeBusinessToFile(business);
		login(newUser);
		logger.info("new user added to system : {}", username.getText());
		return true;

	}

	private void login(Customer customer) {
		CustomerMenu menu = new CustomerMenu();
		menu.setBusiness(business);
		menu.setMainMenu(pm);
		menu.setCustomer(customer);
		Stage stage = (Stage) registerButton.getScene().getWindow();
		try
		{
			menu.start(stage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkBusinessUsers(String username, Business business)
	{
		for (Customer customer : business.getCustomers())
		{
			if (customer.getUsername().equals(username))
			{
				return false;
			}
		}
		return true;
	}
}
