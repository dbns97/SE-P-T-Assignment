package application.controllers;

import application.models.*;
import application.views.*;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddBusinessController {
	private AdminMenu am;
	@FXML
	private TextField businessName;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField reenter;
	@FXML
	private TextField ownerName;
	@FXML
	private TextField address;
	@FXML
	private TextField contactNumber;
	@FXML
	private Button addBusinessButton;
	@FXML
	private Label errorLabel;
	private Business business;

	public void setMainMenu(AdminMenu am) {
		this.am = am;
	}

	public void handleBack() {
		am.showAdminMenu();
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Business getBusiness() {
		return business;
	}

	public void setErrorLabel(Label l) {
		this.errorLabel = l;
	}

	public Label getErrorLabel() {
		return this.errorLabel;
	}

	public void setBusinessName(TextField businessName) {
		this.businessName = businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName.setText(businessName);
	}

	public void setUsername(TextField username) {
		this.username = username;
	}

	public void setUsername(String username) {
		this.username.setText(username);
	}

	public TextField getUsername() {
		return this.username;
	}

	public void setPassword(PasswordField password) {
		this.password = password;
	}

	public void setPassword(String password) {
		this.password.setText(password);
	}

	public void setReenteredPassword(PasswordField password) {
		this.reenter = password;
	}

	public void setReenteredPassword(String password) {
		this.reenter.setText(password);
	}

	public void setOwnerName(TextField ownerName) {
		this.ownerName = ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName.setText(ownerName);
	}

	public void setAddress(TextField address) {
		this.address = address;
	}

	public void setAddress(String address) {
		this.address.setText(address);
	}

	public void setContactNumber(TextField contactNumber) {
		this.contactNumber = contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber.setText(contactNumber);
	}

	public void setAddBusinessButton(Button addBusinessButton) {
		this.addBusinessButton = addBusinessButton;
	}

	public boolean handleAddBusiness() {
		errorLabel.setWrapText(true);

		String errorMessage = checkBusinessDetails(businessName.getText(), username.getText(), password.getText(),
				reenter.getText(), ownerName.getText(), address.getText(), contactNumber.getText());
		
		if(!errorMessage.equals(("Successfully registered " + username.getText())))
		{
			errorLabel.setText(errorMessage);
			return false;
		}
		/*
		if (businessName.getText().isEmpty()) {
			errorLabel.setText("Please enter a business name");
			return false;
		}

		if (username.getText().trim().isEmpty() || !(username.getText().matches("[a-zA-z0-9]+"))) {
			errorLabel.setText("Please enter a username without symbols or whitespace");
			return false;
		}

		if (password.getText().isEmpty()) {
			errorLabel.setText("Please enter a password");
			return false;
		}

		if (reenter.getText().isEmpty() || !reenter.getText().equals(password.getText())) {
			errorLabel.setText("Please re-enter your password");
			return false;
		}

		if (!(ownerName.getText().matches("[a-zA-z ,.'-]+")) || ownerName.getText().trim().isEmpty()) {
			errorLabel.setText("Please enter a name without numbers or symbols");
			return false;
		}

		if (!(address.getText().matches("[a-zA-z0-9 ,.'-]+")) || address.getText().trim().isEmpty()) {
			errorLabel.setText("Please enter an address without symbols");
			return false;
		}

		if (!(contactNumber.getText().matches("[0-9]+")) || contactNumber.getText().trim().isEmpty()) {
			errorLabel.setText("Please enter a contact number with only numbers");
			return false;
		}
		*/

		// Check if business name already exists
		if (DatabaseHandler.businessExists(businessName.getText())) {
			errorLabel.setText("Business name already exists");
			return false;
		}

		// Initialise business owner object
		Owner businessOwner = new Owner(username.getText(), password.getText());

		// Initialise empty ArrayLists for employees, services and customers
		ArrayList<Employee> employeesList = new ArrayList<Employee>();
		ArrayList<Service> servicesList = new ArrayList<Service>();
		ArrayList<Customer> customersList = new ArrayList<Customer>();

		// Initialise the business object
		Business newBusiness = new Business(businessName.getText(), businessOwner, employeesList, servicesList,
				customersList);

		errorLabel.setText("Successfully registered " + username.getText());

		// Write the new business to file
		DatabaseHandler.writeBusinessToFile(newBusiness);
		am.showAdminMenu();
		return true;
	}

	public String checkBusinessDetails(String businessName, String username, String password, String reenter,
			String ownerName, String address, String contactNumber) {
		if (businessName.isEmpty()) {
			return "Please enter a business name";
		}

		if (username.trim().isEmpty() || !(username.matches("[a-zA-z0-9]+"))) {
			return "Please enter a username without symbols or whitespace";
		}

		if (password.isEmpty()) {
			return "Please enter a password";
		}

		if (reenter.isEmpty() || !reenter.equals(password)) {
			return "Please re-enter your password";
		}

		if (!(ownerName.matches("[a-zA-z ,.'-]+")) || ownerName.trim().isEmpty()) {
			return "Please enter a name without numbers or symbols";
		}

		if (!(address.matches("[a-zA-z0-9 ,.'-]+")) || address.trim().isEmpty()) {
			return "Please enter an address without symbols";
		}

		if (!(contactNumber.matches("[0-9]+")) || contactNumber.trim().isEmpty()) {
			return "Please enter a contact number with only numbers";
		}

		return ("Successfully registered " + username);
	}

}
