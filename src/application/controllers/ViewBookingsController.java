package application.controllers;
import application.models.*;
import application.views.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ViewBookingsController {
	private Menu parentMenu;
	private Business business;
	@FXML
	private TableView<Booking> bookingsTable;
	@FXML
	private TableColumn<Booking,String> dayColumn;
	@FXML
	private TableColumn<Booking,String> timeColumn;
	@FXML
	private TableColumn<Booking,String> employeeColumn;
	@FXML
	private TableColumn<Booking,String> customerColumn;
	@FXML
	private TableColumn<Booking,String> serviceColumn;
	@FXML
    private ChoiceBox<String> week;
	@FXML
	private Label errorLabel;

	public void setParentMenu(Menu menu)
	{
		this.parentMenu = menu;
	}

	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public void setBookingsTable(TableView<Booking> table)
	{
		this.bookingsTable = table;
	}
	
	public TableView<Booking> getBookingsTable()
	{
		return bookingsTable;
	}
	
	public void setDayColumn(TableColumn<Booking,String> column)
	{
		this.dayColumn = column;
	}
	
	public void setTimeColumn(TableColumn<Booking,String> column)
	{
		this.timeColumn = column;
	}
	
	public void setEmployeeColumn(TableColumn<Booking,String> column)
	{
		this.employeeColumn = column;
	}
	
	public void setCustomerColumn(TableColumn<Booking,String> column)
	{
		this.customerColumn = column;
	}
	
	public void setServiceColumn(TableColumn<Booking,String> column)
	{
		this.serviceColumn = column;
	}
	
	public void setWeekChoiceBox(ChoiceBox<String> choiceBox)
	{
		this.week = choiceBox;
	}
	
	public void setWeekChoiceBox()
	{
		week.setItems(FXCollections.observableArrayList("Last week", "This week", "Next week"));
		week.setValue("This week");
		week.getSelectionModel()
	    .selectedItemProperty()
	    .addListener(
	    	(ObservableValue<? extends String> observable, String oldValue, String newValue) -> handleView()
	    );
	}
	
	public void handleView()
	{	
		ObservableList<Booking> bookings = FXCollections.observableArrayList();
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat displayDayFormat = new SimpleDateFormat("EEE");
		
		Calendar startOfWeek = Calendar.getInstance();
		Calendar endOfWeek = Calendar.getInstance();
		startOfWeek.setFirstDayOfWeek(Calendar.MONDAY);
		
		// Set startOfWeek calendar value to beginning of day
		startOfWeek.set(Calendar.HOUR_OF_DAY, 0); // Clear doesn't reset the hour of day
		startOfWeek.clear(Calendar.MINUTE);
		startOfWeek.clear(Calendar.SECOND);
		startOfWeek.clear(Calendar.MILLISECOND);
		
		// Set startOfWeek calendar to match the selected week
		if (week.getValue().equals("Last week")) {
			// Get start of last week
			startOfWeek.set(Calendar.DAY_OF_WEEK, startOfWeek.getFirstDayOfWeek());
			startOfWeek.add(Calendar.WEEK_OF_YEAR, -1);
		} else if (week.getValue().equals("This week")) {
			// Get start of this week
			startOfWeek.set(Calendar.DAY_OF_WEEK, startOfWeek.getFirstDayOfWeek());
		} else if (week.getValue().equals("Next week")) {
			// Get start of next week
			startOfWeek.set(Calendar.DAY_OF_WEEK, startOfWeek.getFirstDayOfWeek());
			startOfWeek.add(Calendar.WEEK_OF_YEAR, 1);
		}
		
		// Set endOfWeek calendar based on startOfWeek calendar
		endOfWeek.setTimeInMillis(startOfWeek.getTimeInMillis());
		endOfWeek.add(Calendar.WEEK_OF_YEAR, 1);
		
		ArrayList<Customer> customers = business.getCustomers();
		
		for (Customer customer : customers)
		{
			for (Booking booking : customer.getBookings())
			{
				// Check if booking is in the selected week
				if (booking.getStart().getTime() >= startOfWeek.getTimeInMillis() && booking.getEnd().getTime() <= endOfWeek.getTimeInMillis()){
					bookings.add(booking);
				}
			}
		}
		
		bookings.sort((a,b) -> a.getStart().getTime() < b.getStart().getTime() ? -1 : a.getStart().getTime() == b.getStart().getTime() ? 0 : 1);
		
    	employeeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getName()));
    	customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
    	serviceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getService().getName()));
    	dayColumn.setCellValueFactory(cellData -> {
			return new SimpleStringProperty(displayDayFormat.format(cellData.getValue().getStart()));
		});
        timeColumn.setCellValueFactory(cellData -> {
			return new SimpleStringProperty(displayFormat.format(cellData.getValue().getStart()) + "-" + displayFormat.format(cellData.getValue().getEnd()));
		});
        
        bookingsTable.setItems(bookings);
		
	}
	
	public void handleBack()
	{
		if (parentMenu instanceof OwnerMenu) {
			OwnerMenu om = (OwnerMenu) parentMenu;
			om.showOwnerMenu();
		} else {
			CustomerMenu cm = (CustomerMenu) parentMenu;
			cm.showCustomerMenu();
		}
	}

}
