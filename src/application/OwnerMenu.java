package application;

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
	private Buisness buisness;
	
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
			loader.setLocation(getClass().getResource("RootLayout.fxml"));
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
			loader.setLocation(PublicMenu.class.getResource("OwnerMenu.fxml"));
			AnchorPane OwnerMenu = (AnchorPane) loader.load();
			OwnerMenuController controller = loader.getController();
			
			primaryStage.setWidth(OwnerMenu.getPrefWidth() + 50);
			primaryStage.setHeight(OwnerMenu.getPrefHeight() + 32);
			root.setCenter(OwnerMenu);
			controller.setOwnerMenu(this);
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
