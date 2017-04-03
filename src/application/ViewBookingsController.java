package application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class ViewBookingsController {
	private OwnerMenu om;
	private Business business;
	@FXML
	private TableColumn timeColumn;
	@FXML
	private TableColumn employeeColumn;
	
	public void setOwnerMenu(OwnerMenu om)
	{
		this.om = om;
	}
	
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public void handleView()
	{
		
	}
	public void handleBack()
	{
		om.showOwnerMenu();
	}

}
