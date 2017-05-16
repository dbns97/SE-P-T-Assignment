package application.controllers;
import application.models.*;
import application.views.*;

import java.io.IOException;

import org.json.JSONArray;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminMenuController {

	private AdminMenu am;
	private PublicMenu pm;
	
	@FXML
	private Button addBusiness;
	@FXML
	private Button logout;
	@FXML
	private Label errorLabel;
	
	public void setAdminMenu(AdminMenu am)
	{
		this.am = am;
	}
	
	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}
	
	public void showAddBusiness()
	{
		am.showAddBusiness();
	}
	
	public void handleBack()
	{
		am.showAdminMenu();
	}
	
	public void handleLogout()
	{	
        try
        {
    		Stage stage = (Stage) logout.getScene().getWindow();
        	pm.start(stage);
        } catch(Exception e) {
			e.printStackTrace();
		}
	}
}
