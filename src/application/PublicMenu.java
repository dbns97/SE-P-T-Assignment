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
    private Business business;
    
	@Override
	public void start(Stage primaryStage) {
		business = new Business();
		this.primaryStage = primaryStage;
		primaryStage.setOnCloseRequest(e -> {business.updateFile();});
		this.primaryStage.setTitle("Booking System");
		
		//business.loadUsers();
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
			
			primaryStage.setWidth(publicMenu.getPrefWidth() + 50);
			primaryStage.setHeight(publicMenu.getPrefHeight() + 32);
			root.setCenter(publicMenu);
			controller.setMainMenu(this);
			controller.setBusiness(business);
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
		
	public void showRegister()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("RegisterForm.fxml"));
			AnchorPane registerForm = (AnchorPane) loader.load();
			RegisterFormController controller = loader.getController();
			
			primaryStage.setWidth(registerForm.getPrefWidth() + 50);
			primaryStage.setHeight(registerForm.getPrefHeight() + 32);
			root.setCenter(registerForm);
			controller.setMainMenu(this);
			controller.setBusiness(business);
		} catch (IOException e) {
            e.printStackTrace();
            
        }
	}
}
