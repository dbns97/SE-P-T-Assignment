package application;

import org.json.JSONArray;
import org.json.JSONObject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PublicMenuController {
	private PublicMenu pm;

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

	public void handleLoginMenu()
	{

		//pm.showLoginMenu();
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
            // display owner menu
            //OwnerMenu om = new OwnerMenu();
            //om.showCustomerMenu();
            //pm.showLoginMenu();
         }
         else
         {
            // display Customer menu
            CustomerMenu cm = new CustomerMenu();
            cm.showCustomerMenu();
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

	   //debug System.out.println("clicked login");

	   // loads all users from database
	   JSONObject data = JSONUtils.getJSONObjectFromFile("../JSONdatabase/users.json");

	   //debug System.out.println(data);

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
	            // change this to show in gui
	            System.out.println("password in invalid");

	            errorLabel.setText("password in invalid");
	            return false;
	         }
	      }
	      else
	      {
	         // change this to show in gui
	         System.out.println("username enter doesnt exist");

	         errorLabel.setText("username enter doesnt exist");
	         return false;
	      }
	   }
      return false;
   }

	public JSONObject loadUser(String username)
	{
	   // loads all users from database
      JSONObject data = JSONUtils.getJSONObjectFromFile("../JSONdatabase/users.json");

      // load that user information from database
      JSONObject user = data.getJSONObject( username );

      return user;

	}
}
