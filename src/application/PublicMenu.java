package application;

import java.io.IOException;

import iMeldaBot.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PublicMenu extends Menu {

	public Stage primaryStage;
	@FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;
    
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Booking System");
		
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("PublicMenu.fxml"));
			Scene scene = new Scene(root,346.0,290.0);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showLoginMenu()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("LoginMenu.fxml"));
			
		} catch (IOException e) {
            e.printStackTrace();
            
        }
		
	}
	
	//Takes in user input for username and password, returns false if either is incorrect
	public boolean checkLogin(String username, String password)
	{
		return false;
	}
	
	//Takes in input for making customer objects, returns false if the input is incorrect or the username is taken
	public boolean registerUser(String username, String password, String address, int contactNumber)
	{
		return false;
	}

}
