package application;

import javafx.scene.control.Button;

import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PublicMenuController {
	private PublicMenu pm;
	private Business business;
	private JSONObject loggedInUser = null;
	@FXML
	private Button loginButton;
	
	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}
	
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public void setLoggedInUser(JSONObject user)
	{
		loggedInUser = user;
	}
	
	public void handleLogin()
	{
		/*
		CustomerMenu menu = new CustomerMenu();
		menu.setMainMenu(pm);
		Stage stage = (Stage) loginButton.getScene().getWindow();
        try
        {
        	menu.start(stage);
        } catch(Exception e) {
			e.printStackTrace();
		}
		*/
		OwnerMenu menu = new OwnerMenu();
		menu.setBusiness(business);
		menu.setMainMenu(pm);
		Stage stage = (Stage) loginButton.getScene().getWindow();
        try
        {
        	menu.start(stage);
        } catch(Exception e) {
			e.printStackTrace();
		}
        
	}
	
	public void handleRegister()
	{
		pm.showRegister();
	}
}
