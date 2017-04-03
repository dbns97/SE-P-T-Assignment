package application;

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
	private Buisness buisness;
	
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
			loader.setLocation(getClass().getResource("RootLayout.fxml"));
			root = (BorderPane) loader.load();
			
			Scene scene = new Scene(root);
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
			loader.setLocation(PublicMenu.class.getResource("CustomerMenu.fxml"));
			AnchorPane CustomerMenu = (AnchorPane) loader.load();
			CustomerMenuController controller = loader.getController();
			
			primaryStage.setWidth(CustomerMenu.getPrefWidth() + 50);
			primaryStage.setHeight(CustomerMenu.getPrefHeight() + 50);
			root.setCenter(CustomerMenu);
			controller.setCustomerMenu(this);
			controller.setMainMenu(pm);
			controller.setBuisness(buisness);
			
		} catch (IOException e) {
            e.printStackTrace();
            
        }
	}
	
	public void showViewBookings()
	{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(PublicMenu.class.getResource("CustomerBookingTimesForm.fxml"));
			AnchorPane CustomerBookingTimesForm = (AnchorPane) loader.load();
			CustomerBookingTimesController controller = loader.getController();
			
			primaryStage.setWidth(CustomerBookingTimesForm.getPrefWidth() + 50);
			primaryStage.setHeight(CustomerBookingTimesForm.getPrefHeight() + 50);
			root.setCenter(CustomerBookingTimesForm);
			controller.setCustomerMenu(this);
			controller.setMainMenu(pm);
			controller.setBuisness(buisness);
			
		} catch (IOException e) {
            e.printStackTrace();
            
        }
	}
	
	public void setBuisness(Buisness buisness)
	{
		this.buisness = buisness;
	}

}
