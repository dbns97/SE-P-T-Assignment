package application.controllers;
import application.models.*;
import application.views.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private TableView<Shift> shiftTable;
	@FXML
	private TableColumn<Shift,String> timeColumn;
	@FXML
	private TableColumn<Shift,String> employeeColumn;
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
		ObservableList<Shift> shiftList = FXCollections.observableArrayList();
		ArrayList<Employee> employees = business.getEmployees();
		SimpleDateFormat comparingFormat = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat convertingFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = Shift.getSdf();
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		Date input;

		errorLabel.setText("");
		errorLabel.setWrapText(true);
		try
		{
			if(date.getText().trim().isEmpty() || !(date.getText().matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")))
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

			// Create list of all shifts for the entered date
			for(Employee e : employees) {
				for(Shift s : e.getShifts()) {
					if(comparingFormat.format(s.getStart()).equals(comparingFormat.format(input)))
					{
						shiftList.add(s);
					}
				}
			}

        	employeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getName()));
            timeColumn.setCellValueFactory(cellData -> {
				try {
					return new SimpleStringProperty(displayFormat.format(convertingFormat.parse(cellData.getValue().getStart().toString())) + " - " + displayFormat.format(convertingFormat.parse(cellData.getValue().getEnd().toString())));
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			});
            shiftTable.setItems(shiftList);
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
