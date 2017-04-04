package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerBookingTimesController {

	private CustomerMenu cm;
	private PublicMenu pm;
	private Business business;
	
	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee,String> timeColumn;
	@FXML
	private TableColumn<Employee,String> employeeColumn;
	@FXML
    private TextField date;
	@FXML
	private Label errorLabel;
	
	public void setCustomerMenu(CustomerMenu cm)
	{
		this.cm = cm;
	}

	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}
	
	public void handleView()
	{
		ObservableList<Employee> employeeList = FXCollections.observableArrayList();
		JSONObject employees = business.getEmployees();
		JSONObject employeeJSON;
		Employee employee;
		JSONArray emails = employees.names();
		SimpleDateFormat comparingFormat = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat convertingFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		Date input;
		
		errorLabel.setText("");
		errorLabel.setWrapText(true);
		try
		{
			if(date.getText().trim().isEmpty() || !(date.getText().matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]")))
			{
				errorLabel.setText("Please enter a date in the dd/mm/yyyy format");
				return;
			}
			
			try
			{
				input = inputFormat.parse(date.getText());
			}
			catch(Exception e)
			{
				errorLabel.setText("Invalid date");
				return;
			}
		
			// Scans the database for any shifts
	    	int i = 0;
	    	if(emails != null)
	    	{
	        	while(!emails.isNull(i))
	        	{
	        		
	        		System.out.println(emails.getString(i));
	        		JSONArray shifts = business.getEmployee(emails.getString(i)).getJSONArray("shifts");
	        		employeeJSON = business.getEmployee(emails.getString(i));
	        		
	        		for(int j = 0; j < shifts.length(); j++)
	        		{
						if(comparingFormat.format(convertingFormat.parse(shifts.getJSONObject(j).getString("start"))).equals(comparingFormat.format(input)))
						{
							employee = new Employee(employeeJSON.getString("email"), employeeJSON.getString("name"));
							employee.addShift(sdf.format(convertingFormat.parse(shifts.getJSONObject(j).getString("start"))), sdf.format(convertingFormat.parse(shifts.getJSONObject(j).getString("end"))));
							System.out.println(employee.toJSONObject().toString());
							employeeList.add(employee);
						}
	        		}
	            	i++;
	        	}
	    	}
	    	
	    	employeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
	        timeColumn.setCellValueFactory(cellData -> {
				try {
					return new SimpleStringProperty(displayFormat.format(convertingFormat.parse(cellData.getValue().getShift(0).getStart().toString())) + " - " + displayFormat.format(convertingFormat.parse(cellData.getValue().getShift(0).getEnd().toString())));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			});
	        employeeTable.setItems(employeeList);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setBusiness(Business business)
	{
		this.business = business;
	}

	public void handleBack()
	{
		cm.showCustomerMenu();
	}
}
