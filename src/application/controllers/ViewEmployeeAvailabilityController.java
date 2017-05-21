package application.controllers;
import application.models.*;
import application.views.*;

import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class ViewEmployeeAvailabilityController {

	final static Logger logger = LogManager.getLogger(ViewEmployeeAvailabilityController.class.getName());

	private OwnerMenu om;
	private Business business;
	private ArrayList<TableView<Shift>> tables = new ArrayList<TableView<Shift>>();
	private ArrayList<TableColumn<Shift,String>> timeColumns = new ArrayList<TableColumn<Shift,String>>();
	private ArrayList<TableColumn<Shift,String>> employeeColumns = new ArrayList<TableColumn<Shift,String>>();

	SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
	SimpleDateFormat convertingFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
	@FXML
	private Label heading;
	@FXML
	private TableView<Shift> shiftTable;
	@FXML
	private TableColumn<Shift,String> timeColumn;
	@FXML
	private TableColumn<Shift,String> employeeColumn;
	@FXML
	private TableView<Shift> shiftTable1;
	@FXML
	private TableColumn<Shift,String> timeColumn1;
	@FXML
	private TableColumn<Shift,String> employeeColumn1;
	@FXML
	private TableView<Shift> shiftTable2;
	@FXML
	private TableColumn<Shift,String> timeColumn2;
	@FXML
	private TableColumn<Shift,String> employeeColumn2;
	@FXML
	private TableView<Shift> shiftTable3;
	@FXML
	private TableColumn<Shift,String> timeColumn3;
	@FXML
	private TableColumn<Shift,String> employeeColumn3;
	@FXML
	private TableView<Shift> shiftTable4;
	@FXML
	private TableColumn<Shift,String> timeColumn4;
	@FXML
	private TableColumn<Shift,String> employeeColumn4;
	@FXML
	private TableView<Shift> shiftTable5;
	@FXML
	private TableColumn<Shift,String> timeColumn5;
	@FXML
	private TableColumn<Shift,String> employeeColumn5;
	@FXML
	private TableView<Shift> shiftTable6;
	@FXML
	private TableColumn<Shift,String> timeColumn6;
	@FXML
	private TableColumn<Shift,String> employeeColumn6;
	@FXML
	private Label errorLabel;

	public void setOwnerMenu(OwnerMenu om)
	{
		this.om = om;
	}
	
	public void setHeading()
	{
		this.heading.setFont(Font.font(business.getFont(), 18));
	}

	public void setBusiness(Business business)
	{
		this.business = business;
	}

	public void loadRoster()
	{
		ArrayList<ObservableList<Shift>> shiftList = new ArrayList<ObservableList<Shift>>();

		for(int i = 0; i < Day.values().length; i++)
		{
			shiftList.add(FXCollections.observableArrayList());
		}

		populateArrays();

		for(Employee e : business.getEmployees())
		{
			int i = 0;
			for (Day day : Day.values())
			{
				if (e.getRoster().containsKey(day) && e.getRoster().get(day) != null)
				{
					shiftList.get(i).add(e.getRoster().get(day));
				}
				i++;
			}
		}

		for(int i = 0; i < Day.values().length; i++)
		{
			employeeColumns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getName()));
	        timeColumns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(displayFormat.format(cellData.getValue().getStart()) + " - " + displayFormat.format(cellData.getValue().getEnd())));
	        tables.get(i).setItems(shiftList.get(i));
		}
	}

	public void handleBack()
	{
		om.showOwnerMenu();
	}

	private void populateArrays()
	{
		tables.add(shiftTable);
		tables.add(shiftTable1);
		tables.add(shiftTable2);
		tables.add(shiftTable3);
		tables.add(shiftTable4);
		tables.add(shiftTable5);
		tables.add(shiftTable6);

		employeeColumns.add(employeeColumn);
		employeeColumns.add(employeeColumn1);
		employeeColumns.add(employeeColumn2);
		employeeColumns.add(employeeColumn3);
		employeeColumns.add(employeeColumn4);
		employeeColumns.add(employeeColumn5);
		employeeColumns.add(employeeColumn6);

		timeColumns.add(timeColumn);
		timeColumns.add(timeColumn1);
		timeColumns.add(timeColumn2);
		timeColumns.add(timeColumn3);
		timeColumns.add(timeColumn4);
		timeColumns.add(timeColumn5);
		timeColumns.add(timeColumn6);
	}

}
