package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PublicMenu extends Menu {

	public Stage primaryStage;
	public BorderPane root;
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
		
		setRoot();
		showPublicMenu();
		
	}
	
	public void showPublicMenu()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("PublicMenu.fxml"));
			AnchorPane publicMenu = (AnchorPane) loader.load();
			PublicMenuController controller = loader.getController();
			
			//primaryStage.setWidth(publicMenu.getPrefWidth());
			//primaryStage.setHeight(publicMenu.getPrefHeight());
			root.setCenter(publicMenu);
			controller.setMainMenu(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setRoot()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("RootLayout.fxml"));
			root = (BorderPane) loader.load();
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void showLoginMenu()
	{
		/*
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("CustomerMenu.fxml"));
			loader.setLocation(PublicMenu.class.getResource("OwnerMenu.fxml"));
			
		} catch (IOException e) {
            e.printStackTrace();
            
        }
		*/
	}
	
	public void showRegister()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("RegisterForm.fxml"));
			AnchorPane registerForm = (AnchorPane) loader.load();
			RegisterFormController controller = loader.getController();
			
			//primaryStage.setWidth(registerForm.getPrefWidth());
			//primaryStage.setHeight(registerForm.getPrefHeight());
			root.setCenter(registerForm);
			controller.setMainMenu(this);
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
