package application.views;
import application.models.*;
import application.controllers.*;

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
	private TextField businessName;
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

		//business.loadUsers();
		setRoot();
		showPublicMenu();

	}

    public void showPublicMenu()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/PublicMenu.fxml"));
			AnchorPane publicMenu = (AnchorPane) loader.load();
			PublicMenuController controller = loader.getController();

			primaryStage.setWidth(publicMenu.getPrefWidth() + 50);
			primaryStage.setHeight(publicMenu.getPrefHeight() + 32);
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
			loader.setLocation(getClass().getResource("../views/RootLayout.fxml"));
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
			loader.setLocation(PublicMenu.class.getResource("../views/RegisterForm.fxml"));
			AnchorPane registerForm = (AnchorPane) loader.load();
			RegisterFormController controller = loader.getController();

			primaryStage.setWidth(registerForm.getPrefWidth() + 50);
			primaryStage.setHeight(registerForm.getPrefHeight() + 32);
			root.setCenter(registerForm);
			controller.setMainMenu(this);
		} catch (IOException e) {
            e.printStackTrace();

        }
	}
	
	/**
	 * @description load the view for creating a business
	 * @return void
	 * @author Drew Nuttall-Smith
	 * @since 8/5/2017
	 **/
	public void showCreateBusiness()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("../views/CreateBusinessForm.fxml"));
			AnchorPane createBusinessForm = (AnchorPane) loader.load();
			CreateBusinessFormController controller = loader.getController();

			primaryStage.setWidth(createBusinessForm.getPrefWidth() + 50);
			primaryStage.setHeight(createBusinessForm.getPrefHeight() + 32);
			root.setCenter(createBusinessForm);
			controller.setMainMenu(this);
		} catch (IOException e) {
            e.printStackTrace();

        }
	}
}
