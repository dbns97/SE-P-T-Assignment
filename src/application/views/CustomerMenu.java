package application.views;
import application.models.*;
import application.controllers.*;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CustomerMenu extends Menu {

	private Stage primaryStage;
	public BorderPane root;
	private PublicMenu pm;
	private Business business;
	private Customer customer;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		setRoot();
		showCustomerMenu();
	}

	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}

	public void setRoot()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/views/RootLayout.fxml"));
			root = (BorderPane) loader.load();
			
			root.setStyle("-fx-font-family:\"" + business.getFont() + "\"; -fx-background-color: transparent;");
			Scene scene = new Scene(root, business.getBackgroundColor());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void showCustomerMenu()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("/application/views/CustomerMenu.fxml"));
			AnchorPane CustomerMenu = (AnchorPane) loader.load();
			CustomerMenuController controller = loader.getController();

			primaryStage.setWidth(CustomerMenu.getPrefWidth() + 50);
			primaryStage.setHeight(CustomerMenu.getPrefHeight() + 50);
			root.setCenter(CustomerMenu);
			controller.setCustomerMenu(this);
			controller.setMainMenu(pm);
			controller.setBusiness(business);
			controller.setBusinessLabel(business.getName());

		} catch (IOException e) {
            e.printStackTrace();

        }
	}

	public void viewAvailableTimes()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("/application/views/ViewAvailableTimesForm.fxml"));
			AnchorPane CustomerAvailableTimesForm = (AnchorPane) loader.load();
			ViewAvailableTimesController controller = loader.getController();

			primaryStage.setWidth(CustomerAvailableTimesForm.getPrefWidth() + 50);
			primaryStage.setHeight(CustomerAvailableTimesForm.getPrefHeight() + 50);
			root.setCenter(CustomerAvailableTimesForm);
			controller.setParentMenu(this);
			controller.setBusiness(business);
			controller.setWeekChoiceBox();
			controller.setHeading();
			controller.handleView();

		} catch (IOException e) {
            e.printStackTrace();

        }
	}

	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	
	public void showMakeBookings()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("/application/views/CustomerMakeBooking.fxml"));
			AnchorPane CustomerMakeBooking = (AnchorPane) loader.load();
			CustomerMakeBookingController controller = loader.getController();

			primaryStage.setWidth(CustomerMakeBooking.getPrefWidth() + 50);
			primaryStage.setHeight(CustomerMakeBooking.getPrefHeight() + 50);
			root.setCenter(CustomerMakeBooking);
			controller.setMainMenu(this);
			controller.setBusiness(business);
			controller.setCustomer(customer);
			controller.setHeading();
			
			controller.setEmployeeBox();
			controller.setDayBox();
			controller.setTimeBox();
			controller.setConfirmButton();
			
			controller.setServiceChoiceBox();
			
		} catch (IOException e) {
            e.printStackTrace();

        }
	}

}
