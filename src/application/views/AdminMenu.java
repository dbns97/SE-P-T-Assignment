package application.views;
import application.models.*;
import application.controllers.*;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminMenu extends Menu {

	private Stage primaryStage;
	public BorderPane root;
	private PublicMenu pm;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		setRoot();
		showAdminMenu();
	}

	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
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

	public void showAdminMenu()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("../views/AdminMenu.fxml"));
			AnchorPane AdminMenu = (AnchorPane) loader.load();
			AdminMenuController controller = loader.getController();

			primaryStage.setWidth(AdminMenu.getPrefWidth() + 50);
			primaryStage.setHeight(AdminMenu.getPrefHeight() + 32);
			root.setCenter(AdminMenu);
			controller.setAdminMenu(this);
			controller.setMainMenu(pm);

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
	public void showAddBusiness()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("../views/AddBusiness.fxml"));
			AnchorPane addBusiness = (AnchorPane) loader.load();
			AddBusinessController controller = loader.getController();

			primaryStage.setWidth(addBusiness.getPrefWidth() + 50);
			primaryStage.setHeight(addBusiness.getPrefHeight() + 32);
			root.setCenter(addBusiness);
			controller.setMainMenu(this);
		} catch (IOException e) {
            e.printStackTrace();

        }
	}
	
}
