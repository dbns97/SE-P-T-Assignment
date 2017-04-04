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

	@FXML
	private Button loginButton;
	@FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorLabel;


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


	public void handleLogin()
   {
      Boolean userExist = checkLogin();

      if( userExist == true )
      {
         // now load this user from the data base
         JSONObject user = loadUser(username.getText() );

         // find out if owner or customer
         if( user.getBoolean("isOwner") == true )
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
            CustomerMenu menu = new CustomerMenu();
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

	   // loads all users from database
	   JSONObject data = JSONUtils.getJSONObjectFromFile("../../JSONdatabase/users.json");
       // checks the database user names and compares to what was entered in the text field

       String[] dataUsernames = JSONObject.getNames(data);
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


	            errorLabel.setText("password in invalid");
	            return false;
	         }
	      }

	   }
      errorLabel.setText("username enter doesnt exist");
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
