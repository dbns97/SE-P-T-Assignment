package application.controllers;
import application.models.*;
import application.views.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Date;

public class ViewBookingsController {
	private OwnerMenu om;
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

		// OLD
		/*
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
		*/
	}
	public void handleBack()
	{
		om.showOwnerMenu();
	}

}
