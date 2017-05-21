package application.controllers;
import application.models.*;
import application.views.*;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;

import java.util.Calendar;
import java.util.Date;

public class ViewAvailableTimesController {
	private Menu parentMenu;
	private Business business;
	@FXML
	private Label heading;
	@FXML
	private TableView<Shift> freeSlotTable;
	@FXML
	private TableColumn<Shift,String> dayColumn;
	@FXML
	private TableColumn<Shift,String> timeColumn;
	@FXML
	private TableColumn<Shift,String> employeeColumn;
	@FXML
    private ChoiceBox<String> week;
	@FXML
	private Label errorLabel;

	public void setParentMenu(Menu menu)
	{
		this.parentMenu = menu;
	}
	
	public void setHeading()
	{
		System.out.println(heading);
		heading.setFont(Font.font(business.getFont(), 18));
	}

	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public void setWeekChoiceBox()
	{
		week.setItems(FXCollections.observableArrayList("This week", "Next week"));
		week.setValue("This week");
		week.getSelectionModel()
	    .selectedItemProperty()
	    .addListener(
	    	(ObservableValue<? extends String> observable, String oldValue, String newValue) -> handleView()
	    );
	}
	
	public void handleView()
	{	
		// Use Shift objects to represent free time slots
		ObservableList<Shift> freeSlotList = FXCollections.observableArrayList();
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
		if (week.getValue().equals("This week")) {
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
		
		// Create initial list of free times based on employee shifts
		// ------------------
		
		// Iterate over all employees
		for (Employee employee : business.getEmployees())
		{
			// Iterate over all shifts and add to employeeFreeSlots
			for (Day dow : employee.getRoster().keySet())
			{
				Shift shift = employee.getRoster().get(dow);
				if (shift == null) {
					continue;
				}
				// Set the shift date properly (only time is correct because it came from roster)
				SimpleDateFormat hourSdf = new SimpleDateFormat("HH");
				SimpleDateFormat minuteSdf = new SimpleDateFormat("mm");
				String shiftStartHour = hourSdf.format(shift.getStart());
				String shiftStartMinute = minuteSdf.format(shift.getStart());
				String shiftEndHour = hourSdf.format(shift.getEnd());
				String shiftEndMinute = minuteSdf.format(shift.getEnd());
				
				Calendar shiftStartCal = Calendar.getInstance();
				shiftStartCal.setTimeInMillis(startOfWeek.getTimeInMillis());
				shiftStartCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(shiftStartHour));
				shiftStartCal.set(Calendar.MINUTE, Integer.parseInt(shiftStartMinute));
				switch(dow) {
					case MONDAY:
						shiftStartCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
						break;
					case TUESDAY:
						shiftStartCal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
						break;
					case WEDNESDAY:
						shiftStartCal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
						break;
					case THURSDAY:
						shiftStartCal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
						break;
					case FRIDAY:
						shiftStartCal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
						break;
					case SATURDAY:
						shiftStartCal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
						break;
					case SUNDAY:
						shiftStartCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
						break;
				}
				
				Calendar shiftEndCal = Calendar.getInstance();
				shiftEndCal.setTimeInMillis(startOfWeek.getTimeInMillis());
				shiftEndCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(shiftEndHour));
				shiftEndCal.set(Calendar.MINUTE, Integer.parseInt(shiftEndMinute));
				switch(dow) {
					case MONDAY:
						shiftEndCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
						break;
					case TUESDAY:
						shiftEndCal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
						break;
					case WEDNESDAY:
						shiftEndCal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
						break;
					case THURSDAY:
						shiftEndCal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
						break;
					case FRIDAY:
						shiftEndCal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
						break;
					case SATURDAY:
						shiftEndCal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
						break;
					case SUNDAY:
						shiftEndCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
						break;
				}
				
				Shift newFreeSlot = new Shift(shiftStartCal.getTime(), shiftEndCal.getTime());
				newFreeSlot.setEmployee(shift.getEmployee());
				freeSlotList.add(newFreeSlot);
			}
		}
		
		// Adjust free time slots based on existing bookings
		//-------------------
		
		// Iterate over all customers
		for (Customer customer : business.getCustomers())
		{
			// Iterate over all bookings
			for (Booking booking : customer.getBookings())
			{
				// Check if booking is in the selected week
				if (booking.getStart().getTime() >= startOfWeek.getTimeInMillis() && booking.getEnd().getTime() <= endOfWeek.getTimeInMillis()) {
					// Remove booking time from free time slot
					for (int i = freeSlotList.size() - 1; i >= 0; i--)
					{
						Shift slot = freeSlotList.get(i);
						// Check if the employees match
						if (booking.getEmployee().equals(slot.getEmployee())) {
							// Check if booking is inside the free time slot
							if (booking.getStart().getTime() >= slot.getStart().getTime() && booking.getEnd().getTime() <= slot.getEnd().getTime()) {
								// Add new slot before the booking
								Shift newFreeSlot = new Shift(slot.getStart(), booking.getStart());
								newFreeSlot.setEmployee(slot.getEmployee());
								freeSlotList.add(newFreeSlot);
								// Add new slot after the booking
								newFreeSlot = new Shift(booking.getEnd(), slot.getEnd());
								newFreeSlot.setEmployee(slot.getEmployee());
								freeSlotList.add(newFreeSlot);
								// Remove the old slot
								freeSlotList.remove(slot);
							}
						}
					}
				}
			}
		}
		
		freeSlotList.sort((a,b) -> a.getStart().getTime() < b.getStart().getTime() ? -1 : a.getStart().getTime() == b.getStart().getTime() ? 0 : 1);
		
    	dayColumn.setCellValueFactory(cellData -> {
			return new SimpleStringProperty(displayDayFormat.format(cellData.getValue().getStart()));
		});
        timeColumn.setCellValueFactory(cellData -> {
			return new SimpleStringProperty(displayFormat.format(cellData.getValue().getStart()) + "-" + displayFormat.format(cellData.getValue().getEnd()));
		});
        employeeColumn.setCellValueFactory(cellData -> {
			return new SimpleStringProperty(cellData.getValue().getEmployee().getName());
		});
        
        freeSlotTable.setItems(freeSlotList);
		
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
