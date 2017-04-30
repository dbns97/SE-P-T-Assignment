package application.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import application.models.Booking;
import application.models.Business;
import application.models.Customer;
import application.models.Day;
import application.models.Employee;
import application.models.Service;
import application.models.Shift;
import application.views.OwnerMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class OwnerMakeBookingController {

	
	private OwnerMenu om;
	private Business business;
	
	private Customer currentCustomer;
	private Employee currentEmployee;
	private Service currentService;
	
	private Date StartTime;
	private Date EndTime;
	private Date Duration;
	
	@FXML
	private ChoiceBox<String> customer;
	@FXML
    private ChoiceBox<String> day;
	@FXML
	private TextField time;
	@FXML
    private ChoiceBox<String> service;
	@FXML
    private ChoiceBox<String> employee;
	
	@FXML
	private AnchorPane dayBox;
	@FXML
	private AnchorPane timeBox;
	@FXML
	private AnchorPane serviceBox;
	@FXML
	private AnchorPane employeeBox;
	@FXML
	private Button back;
	@FXML
	private Button confirm;
	@FXML
	private Label errorLabel;
	
	public void setMainMenu(OwnerMenu om)
	{
		this.om = om;
	}
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public void handleBack()
	{
		om.showOwnerMenu();
	}
	
	// these set future choice box to invisible so user cannot enter data until previous information is entered
	public void setEmployeeBox()
	{
		employeeBox.setVisible(false);
	}
	public void setDayBox()
	{
		dayBox.setVisible(false);
	}
	public void setTimeBox()
	{
		timeBox.setVisible(false);
	}
	public void setConfirmButton()
	{
		confirm.setVisible(false);
	}
	
	// finds the customers in the data base and adds to choice box
	public void setCustomerChoiceBox() {
		// TODO Auto-generated method stub
		String[] CustomerList = new String[business.getCustomers().size()];
		System.out.println(business.getCustomers().size());
		for (int i = 0 ; i < business.getCustomers().size(); i++)
		{
			CustomerList[i] = business.getCustomers().get(i).getName();
			System.out.println(business.getCustomers().get(i).getName());
		}		
		System.out.println(CustomerList);
		customer.setItems(FXCollections.observableArrayList( CustomerList ) );
	}
	
	// finds the services in the data base and adds to choice box
	public void setServiceChoiceBox()
	{				
		String[] serviceList = new String[business.getServices().size()];
		for (int i = 0 ; i < business.getServices().size(); i++)
		{
			serviceList[i] = business.getServices().get(i).getName();
		}		
		service.setItems(FXCollections.observableArrayList( serviceList ) );
	}
	
	// finds the employee in the data base and adds to choice box
	public void setEmployeeChoiceBox()
	{
		String[] EmployeeList = new String[business.getEmployees().size()];
		for (int i = 0 ; i < business.getEmployees().size(); i++)
		{
			EmployeeList[i] = business.getEmployees().get(i).getName();
		}		
		employee.setItems(FXCollections.observableArrayList( EmployeeList ) );
	}
	
	// should only shows days that employee is working and does that service
	public void setDayChoiceBox()
	{
		// first get employee from data base
		Employee currentEmployee = null;
		for (int i = 0 ; i < business.getEmployees().size(); i++)
		{
			
			if ( employee.getValue().equals(business.getEmployees().get(i).getName()))
			{
				currentEmployee = business.getEmployees().get(i);
			}
	
		}	
		
		HashMap<Day, Shift> empRoster = currentEmployee.getRoster();
		ObservableList<String> dayList = FXCollections.observableArrayList();
		for (Day key : empRoster.keySet()) 
		{
			if (empRoster.get(key)!= null)
			{
				dayList.add(key.toString());
			}			
		}
		
		day.setItems(FXCollections.observableArrayList( dayList ) );		

	}
	
	// only show available times for that day and wont end in another bookings start time
	public void setTimeChoiceBox()
	{
		time.getText();
	}
	
	public void handleServiceChoiceBox()
	{
		System.out.println("service box clicked");
		
		errorLabel.setText("");
		
		if(confirm.isVisible())
			confirm.setVisible(false);
		
		
		if(timeBox.isVisible())
		{
			timeBox.setVisible(false);
		}
		if(dayBox.isVisible())
		{
			day.setItems(null);
			dayBox.setVisible(false);
		}
		
		
		if( employeeBox.isVisible() == false)
			employeeBox.setVisible(true);
		
		setEmployeeChoiceBox();
	}

	public void handleEmployeeChoiceBox()
	{
		System.out.println("employee box clicked");
		
		errorLabel.setText("");
		
		if(confirm.isVisible())
			confirm.setVisible(false);
		
		
		if(timeBox.isVisible())
		{
			timeBox.setVisible(false);
		}
		
		
		if( dayBox.isVisible() == false)
			dayBox.setVisible(true);	
		
		setDayChoiceBox();
	}
	
	
	public void handleDayChoiceBox()
	{
System.out.println("day box clicked");
		
		errorLabel.setText("");
		
		if(confirm.isVisible() == false)
			confirm.setVisible(true);
				
		if( timeBox.isVisible() == false)
			timeBox.setVisible(true);
		
		setTimeChoiceBox();
		
		
	}
	
	public void handleConfirmButton()
	{
		System.out.println("confirm button clicked");
		
		//what will be saved to database
		System.out.println("booking details : \n"
							+ customer.getValue() + "\n"
							+ service.getValue() +  "\n"
							+ employee.getValue() +  "\n"
							+ day.getValue() +  "\n"
							+ time.getText() +  "\n"							
							);
		
		Boolean madeBooking = checkBooking();
		
		if (madeBooking == true)
		{
			Booking newBooking = new Booking(StartTime, EndTime, currentEmployee, currentCustomer, currentService);
			System.out.println("booking made");

			
			om.showOwnerMenu();
		}
		else
		{
			System.out.println("booking error");
		}
	}

	public Boolean checkBooking()
	{
		if( service.getValue() == null
				|| employee.getValue() == null
				|| day.getValue() == null 
				|| time.getText().isEmpty() )
				{
					return false;
				}
				else
				{
					// gets there name not there username
					currentCustomer = business.getCustomer(customer.getValue() );
					
					
					currentService = business.getService( service.getValue() );
					
					for( Employee e : business.getEmployees() )
					{
						if( e.getName().equals( employee.getValue() ) )
						{
							currentEmployee = business.getEmployee( e.getEmail() );
						}
					}
					
					System.out.println(currentEmployee);
					
					String duration = Integer.toString( currentService.getDuration() );

					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					SimpleDateFormat sdf1 = new SimpleDateFormat("m");
					try
					{
						StartTime = sdf.parse( time.getText() ) ;			
						Duration = sdf1.parse( duration ) ;
										
						Calendar cal = Calendar.getInstance();
						cal.setTime(StartTime);
						cal.add(Calendar.MINUTE,currentService.getDuration() );				
						EndTime = cal.getTime();
						
						System.out.println(StartTime);
						System.out.println(Duration);
						System.out.println(EndTime);
						
						
						/*
						HashMap<Day, Shift> empRoster = currentEmployee.getRoster();
						Shift shift = empRoster.get(day.getValue());
						System.out.println("here");
						System.out.println(shift.getStart());
						
						//these are the problem
						long empStart = currentEmployee.getShift( day.getValue() ).getStart().getTime();
						System.out.println("now here");
						long empEnd = currentEmployee.getShift( day.getValue() ).getEnd().getTime();
						System.out.println(empStart);
						//
						*/
						/**
						if ( StartTime.getTime() >=  empStart && EndTime.getTime() <= empEnd )
						{
							ArrayList<Customer> customers = business.getCustomers();			
							for (Customer customer : customers)
							{
								for (Booking booking : customer.getBookings())
								{
									if ( ( StartTime.getTime() <= booking.getEnd().getTime() )
									   ||( EndTime.getTime() >= booking.getStart().getTime() )
									   )
									{
										System.out.println("your chosen time goes through another booking");
										return false;
									}
								}
							}
						}
						else
						{
							System.out.println("choose a time when employee is working");
						}
						**/
						ArrayList<Customer> customers = business.getCustomers();			
						for (Customer customer : customers)
						{
							for (Booking booking : customer.getBookings())
							{
								if ( ( StartTime.getTime() < booking.getEnd().getTime() )
								   ||( EndTime.getTime() > booking.getStart().getTime() )
								   )
								{
									System.out.println("your chosen time goes through another booking");
									return false;
								}
							}
						}
						
					}
					catch (ParseException e) 
					{
						e.printStackTrace();
					}

					return true;
				}
				

	}

}
