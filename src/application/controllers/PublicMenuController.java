package application.controllers;
import application.models.*;
import application.views.*;

import javafx.scene.control.Button;

import java.util.ArrayList;

import org.json.JSONObject;

import javafx.fxml.FXML;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PublicMenuController {
	private PublicMenu pm;
	private Business business;
	private JSONObject loggedInUser = null;
	private String usersFilepath = "../../JSONdatabase/users.json";

	@FXML
	private Button loginButton;
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

	public void setBusiness(Business business)
	{
		this.business = business;
	}

	public void setLoggedInUser(JSONObject user)
	{
		loggedInUser = user;
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
	
	public void setUsersFilepath(String path)
	{
		this.usersFilepath = path;
	}
	
	public void setPassword(PasswordField password)
	{
		this.password = password;
	}
	
	public Business getBusiness()
	{
		return business;
	}
	
	public void handleLogin()
   {
      Boolean userExist = checkLogin();

      if( userExist == true )
      {
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
      }

   }

	//Checks the Customer array and Owner against the input, to check for login
	public boolean checkLogin()
	{
		errorLabel.setWrapText(true);
		
		ArrayList<Customer> users = business.getCustomers();
		
		//Checking if the owner is has been logged in
		if(business.getOwner() != null && username.getText().equals(business.getOwner().getUsername()))
		{
			if(password.getText().equals(business.getOwner().getPassword()))
			{
				return true;
			}
			else
			{
				errorLabel.setText("Password is invalid");
				return false;
			}
			
		}
		
		//Exiting if the array is empty
		if(users == null || users.isEmpty())
		{
			errorLabel.setText("Username entered doesnt exist");
		}
		
		//Looping through the users and checking if any are logged in
		for(Customer user : users)
		{
			if(username.getText().equals(user.getUsername()))
			{
				if(password.getText().equals(user.getPassword()))
				{
		            return true;
				}
				else
				{
					errorLabel.setText("Password is invalid");
		            return false;
				}
			}
		}
		
		errorLabel.setText("Username entered doesnt exist");
		return false;
	}

	/*
	public JSONObject loadUser(String username)
	{
	   // loads all users from database
      JSONObject data = JSONUtils.getJSONObjectFromFile("../../JSONdatabase/users.json");

      // load that user information from database
      JSONObject user = data.getJSONObject( username );

      return user;

	}
	*/
}
