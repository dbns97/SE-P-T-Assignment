package application.controllers;
import application.models.*;
import application.views.*;

import javafx.scene.control.Button;

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
	private String businessesFilepath = "../../JSONdatabase/businesses.json";

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

	public void setLoggedInUser(JSONObject user)
	{
		loggedInUser = user;
	}

	public void handleRegister()
	{
		pm.showRegister();
	}
	
	public void handleCreateBusiness()
	{
		pm.showCreateBusiness();
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

	public void handleLogin()
   {
      Boolean validLogin = checkLogin();

      if( validLogin == true )
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

	/*
	 * if this returns true we want to load the user information
	 * then find out if the user is the owner then call for the
	 * right menu to display
	 */
	public boolean checkLogin()
   {
	   errorLabel.setWrapText(true);
	   
	   // Check if business name entered is valid
	   JSONObject businesses = JSONUtils.getJSONObjectFromFile(businessesFilepath);
	   String[] businessNames = JSONObject.getNames(businesses);
	   if (businessNames == null) {
    	   errorLabel.setText("Business entered doesn't exist");
    	   return false;
	   }
	   
	   for(int i = 0; i < businessNames.length; i++) {
		   if(businessName.getText().equals(businessNames[i])) {
			   business = new Business(businessName.getText());
			   break;
		   }
	   }

	   // loads all users from database
	   JSONObject data = JSONUtils.getJSONObjectFromFile(usersFilepath).getJSONObject(businessName.getText());
	   
       // checks the database user names and compares to what was entered in the text field
       String[] dataUsernames = JSONObject.getNames(data);
       if(dataUsernames == null)
       {
    	   errorLabel.setText("Username entered doesn't exist");
    	   return false;
       }

       for ( int i=0; i < dataUsernames.length; i++ )
	   {

	      if ( username.getText().equals( dataUsernames[i] ) )
	      {
	         // if here they entered the right user name
	         // load that user information from database
	         JSONObject user = data.getJSONObject( username.getText() );

	         // checks password
	         if ( password.getText().equals( user.getString( "password" ) ) )
	         {
	            errorLabel.setText("");
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
      System.out.println("Username");
      return false;
   }

	public JSONObject loadUser(String username)
	{
	   // loads all users from database
      JSONObject data = JSONUtils.getJSONObjectFromFile("../../JSONdatabase/users.json");

      // load that user information from database
      JSONObject user = data.getJSONObject( username );

      return user;

	}
}
