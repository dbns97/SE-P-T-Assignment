package application.views;
import application.models.*;
import application.controllers.*;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OwnerMenu extends Menu {

	private Stage primaryStage;
	public BorderPane root;
	private PublicMenu pm;
	private Business business;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		setRoot();
		showOwnerMenu();
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

	public void showOwnerMenu()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("../views/OwnerMenu.fxml"));
			AnchorPane OwnerMenu = (AnchorPane) loader.load();
			OwnerMenuController controller = loader.getController();

			primaryStage.setWidth(OwnerMenu.getPrefWidth() + 50);
			primaryStage.setHeight(OwnerMenu.getPrefHeight() + 32);
			root.setCenter(OwnerMenu);
			controller.setOwnerMenu(this);
			controller.setMainMenu(pm);
			controller.setBusiness(business);

		} catch (IOException e) {
            e.printStackTrace();

        }
	}

	public void showViewEmployeeAvailability()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("../views/ViewEmployeeAvailability.fxml"));
			AnchorPane ViewEmployeeAvailability = (AnchorPane) loader.load();
			ViewEmployeeAvailabilityController controller = loader.getController();

			primaryStage.setWidth(ViewEmployeeAvailability.getPrefWidth() + 50);
			primaryStage.setHeight(ViewEmployeeAvailability.getPrefHeight() + 50);
			root.setCenter(ViewEmployeeAvailability);
			controller.setOwnerMenu(this);
			controller.setBusiness(business);
			controller.loadRoster();

		} catch (IOException e) {
            e.printStackTrace();

        }
	}
	
	public void showViewBookings()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("../views/ViewBookingsForm.fxml"));
			AnchorPane ViewBookingsForm = (AnchorPane) loader.load();
			ViewBookingsController controller = loader.getController();

			primaryStage.setWidth(ViewBookingsForm.getPrefWidth() + 50);
			primaryStage.setHeight(ViewBookingsForm.getPrefHeight() + 50);
			root.setCenter(ViewBookingsForm);
			controller.setParentMenu(this);
			controller.setBusiness(business);
			controller.setWeekChoiceBox();
			controller.handleView();

		} catch (IOException e) {
            e.printStackTrace();

        }
	}

	public void showAddEmployee()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("../views/AddEmployee.fxml"));
			AnchorPane addEmployee = (AnchorPane) loader.load();
			AddEmployeeController controller = loader.getController();

			primaryStage.setWidth(addEmployee.getPrefWidth() + 50);
			primaryStage.setHeight(addEmployee.getPrefHeight() + 32);
			root.setCenter(addEmployee);
			controller.setMainMenu(this);
			controller.setBusiness(business);

		} catch (IOException e) {
            e.printStackTrace();

        }
	}

	public void showAddShift()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("../views/AddShift.fxml"));
			AnchorPane addShift = (AnchorPane) loader.load();
			AddShiftController controller = loader.getController();

			primaryStage.setWidth(addShift.getPrefWidth() + 50);
			primaryStage.setHeight(addShift.getPrefHeight() + 32);
			root.setCenter(addShift);
			controller.setMainMenu(this);
			controller.setBusiness(business);
			controller.setDaysChoiceBox();
			controller.setEmailsChoiceBox();

		} catch (IOException e) {
            e.printStackTrace();

        }
	}

	public void setBusiness(Business business)
	{
		this.business = business;
	}

	public void showMakeBookings() 
	{
		// TODO Auto-generated method stub
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("../views/OwnerMakeBooking.fxml"));
			AnchorPane OwnerMakeBooking = (AnchorPane) loader.load();
			OwnerMakeBookingController controller = loader.getController();

			primaryStage.setWidth(OwnerMakeBooking.getPrefWidth() + 50);
			primaryStage.setHeight(OwnerMakeBooking.getPrefHeight() + 50);
			root.setCenter(OwnerMakeBooking);
			controller.setMainMenu(this);
			controller.setBusiness(business);
			
			controller.setEmployeeBox();
			controller.setDayBox();
			controller.setTimeBox();
			controller.setConfirmButton();
			
			controller.setCustomerChoiceBox();
			controller.setServiceChoiceBox();
			
			
			
		} catch (IOException e) {
            e.printStackTrace();

        }
	}
	
	

}
