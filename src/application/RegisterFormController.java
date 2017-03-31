package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

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
		//File file = new File("src/JSONdatabase");
		//for(String fileNames : file.list()) System.out.println(fileNames);
		
		JSONObject users = JSONUtils.getJSONObjectFromFile("../JSONdatabase/users.json");
		System.out.println(users.toString());
		
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
		
		JSONArray usernames = users.names();
		
		// Scans the usernames JSONArray to check if the username already exists
        int i = 0;
        if(usernames != null)
        {
            while(!usernames.isNull(i))
            {
            	System.out.println(usernames.getString(i));
                if(username.getText().equals(usernames.getString(i)))
                {
                	errorLabel.setText("Username already exists");
                    return false;
                }

                i++;
            }
        }
        
		Customer newUser = new Customer(username.getText(), name.getText(), password.getText(), address.getText(), Integer.parseInt(contactNumber.getText()));
		
		users.put(username.getText(), newUser.toJSONObject());
		System.out.println(users.toString());
		
		try
        {
            FileWriter custWriter = new FileWriter("src/JSONdatabase/users.json");
            custWriter.write(users.toString(4));
            custWriter.flush();
            custWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		
		//System.out.println(JSONUtils.getJSONObjectFromFile("users.json").toString());
		return true;		
		
	}
}
