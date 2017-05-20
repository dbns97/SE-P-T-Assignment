package application.controllers;
import application.models.*;
import application.views.*;

import javafx.scene.control.Button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PublicMenuController {
	private PublicMenu pm;
	private Business business;

	@FXML
	private Button loginButton;
	@FXML
	private TextField businessName;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label errorLabel;


	public void setUsername(String username)
	{
		this.username.setText(username);
	}

	public void setPassword(String password)
	{
		this.password.setText(password);
	}

	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}

	public void handleRegister()
	{
		pm.showRegister();
	}

	public void setUsername(TextField username)
	{
		this.username = username;
	}

	public void setErrorLabel(Label errorLabel)
	{
		this.errorLabel = errorLabel;
	}

	public void setPassword(PasswordField password)
	{
		this.password = password;
	}

	public void handleLogin()
	{
		Boolean isAdmin = false;
		Boolean validLogin = false;
		if (businessName.getText().equals("admin")) {
			isAdmin = DatabaseHandler.checkAdminLogin(username.getText(), password.getText());
		} else {
			validLogin = DatabaseHandler.checkLogin(businessName.getText(), username.getText(), password.getText());
		}

		if (isAdmin)
		{
			AdminMenu menu = new AdminMenu();
			menu.setMainMenu(pm);
			Stage stage = (Stage) loginButton.getScene().getWindow();
			try
			{
				menu.start(stage);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if (validLogin)
		{
			business = new Business(businessName.getText());
			// find out if owner or customer
			if(business.getOwner().getUsername().equals(username.getText()))
			{
				OwnerMenu menu = new OwnerMenu();
				menu.setBusiness(business);
				menu.setMainMenu(pm);
				Stage stage = (Stage) loginButton.getScene().getWindow();
				try
				{
					menu.start(stage);
				} catch(Exception e) {
					e.printStackTrace();
				}

			}
			else
			{
				Customer customer = business.getCustomer(username.getText());
				CustomerMenu menu = new CustomerMenu();
				menu.setBusiness(business);
				menu.setMainMenu(pm);
				menu.setCustomer(customer);
				Stage stage = (Stage) loginButton.getScene().getWindow();
				try
				{
					menu.start(stage);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Invalid login details!");
			errorLabel.setText("Invalid login details!");
		}

	}

}
