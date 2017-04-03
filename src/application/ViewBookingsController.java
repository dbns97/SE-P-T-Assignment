package application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class ViewBookingsController {
	private OwnerMenu om;
	private Buisness buisness;
	@FXML
	private TableColumn timeColumn;
	@FXML
	private TableColumn employeeColumn;
	
	public void setOwnerMenu(OwnerMenu om)
	{
		this.om = om;
	}
	
	public void setBuisness(Buisness buisness)
	{
		this.buisness = buisness;
	}
	
	public void handleView()
	{
		
	}
	public void handleBack()
	{
		om.showOwnerMenu();
	}

}
