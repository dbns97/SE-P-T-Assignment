package application.controllers;
import application.views.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
