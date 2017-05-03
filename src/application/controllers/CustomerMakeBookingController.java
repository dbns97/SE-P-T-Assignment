package application.controllers;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import application.models.Booking;
import application.models.Business;
import application.models.Customer;
import application.models.Day;
import application.models.Employee;
import application.models.Service;
import application.models.Shift;
import application.views.CustomerMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerMakeBookingController 
{
	private CustomerMenu cm;
	private Business business;
	private Customer customer;
	private Employee currentEmployee;
	private Service currentService;
	private Date StartTime;
	private Date EndTime;
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
	private Button confirm;	
	@FXML
	private Label errorLabel;
	
	// setters
	public void setMainMenu(CustomerMenu cm)
	{
		this.cm = cm;
	}
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
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
	
	// handlers
	public void handleBack()
	{
		cm.showCustomerMenu();
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
	}
	public void handleConfirmButton()
	{
		System.out.println("confirm button clicked");
		
		//what will be saved to database
		System.out.println(   "\n------------------------\n"
							+ "booking details : \n" 
							+ service.getValue() +  "\n"
							+ employee.getValue() +  "\n"
							+ day.getValue() +  "\n"
							+ time.getText() 
							+ "\n------------------------\n"							
							);
		
		Boolean madeBooking = checkBooking();
		
		if (madeBooking == true)
		{
			Booking newBooking = new Booking(StartTime, EndTime, currentEmployee, customer, currentService);
			System.out.println("booking made");
			customer.addBooking(newBooking);
					
			cm.showCustomerMenu();
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
			// get entered service and employee
			currentService = business.getService( service.getValue() );			
			for( Employee e : business.getEmployees() )
			{
				if( e.getName().equals( employee.getValue() ) )
				{
					currentEmployee = business.getEmployee( e.getEmail() );
				}
			}

			// get the entered time, set the dates for this week and calc the end time from that
			StartTime = getTime(time.getText(), day.getValue() );
			EndTime = getTime( StartTime, currentService.getDuration() );			
			// finds the employee starting & ending time of his shift for entered day
			Date employeeStartTime = getTime(currentEmployee.getShift( day.getValue() ).getStart(), day.getValue());
			Date employeeEndTime = getTime( currentEmployee.getShift( day.getValue() ).getEnd(), day.getValue() );
					
			// convert these dates to long
			long enteredStartTime = dateToLong(StartTime);	
			long enteredEndTime = dateToLong(EndTime);	
			long empStartTime = dateToLong(employeeStartTime);			
			long empEndTime = dateToLong(employeeEndTime);			
			
			System.out.println(StartTime + " - Start time of booking");
			System.out.println(EndTime + " - end time of booking");	
			System.out.println(employeeStartTime + " - Employee Start time of shift");
			System.out.println(employeeEndTime + "- Employee end time of shift\n");		
			System.out.println();
						
			System.out.println("checking all bookings");
			System.out.println("current chosen employee : " + currentEmployee.getEmail() );
			// checks that the start time is after employee start time
			// and that it ends before employee end time
			if (enteredStartTime >= empStartTime && enteredEndTime <= empEndTime)
			{
				System.out.println();
				// searches each of the customer bookings for that day
				// and checks that the booking is not going into any for their bookings
				ArrayList<Customer> customers = business.getCustomers();
				
				for (Customer customer : customers)
				{
					System.out.println("current customer bookings we are checking : " + customer.getUsername());
					for (Booking booking : customer.getBookings())
					{
						System.out.println("checks if the booking has the same employee as the one user chose");
						if (booking.getEmployee().getEmail().equals(currentEmployee.getEmail()) ) 
						{
							System.out.println("- They match\n");
							
							System.out.println("\tchosen start time : " + StartTime);
							System.out.println("\tbooking end time : " + booking.getEnd());
							
							System.out.println("\tchosen end time : " + EndTime);
							System.out.println("\tbooking start time : " + booking.getStart());
							
							System.out.println();
							
							if ( ( StartTime.getTime() >= booking.getStart().getTime() && StartTime.getTime() <= booking.getEnd().getTime() )
							   ||( EndTime.getTime() <= booking.getEnd().getTime() && EndTime.getTime() >= booking.getStart().getTime() ) )
							{
								errorLabel.setText("Your chosen time goes through another booking");
								return false;
							}
						}
						System.out.println();
					}
					System.out.println();
				}
			}
			else
			{
				errorLabel.setText("Choose a time when employee is working");
				return false;
			}
				
			return true;
		}
		
		
	}
	
	public long dateToLong(Date date)
	{
		return ( date.getTime() % (24 * 60 * 60 * 1000) );
	}
	
	// for setting the entered start date of the booking
	public Date getTime(String time, String day)
	{
		// breaks user input time to hours and mins
		StringTokenizer st = new StringTokenizer(time);
		int[] timeBroken = new int[2];
		int i=0;
		while (st.hasMoreTokens()) 
		{
			timeBroken[i] = Integer.parseInt(st.nextToken(":"));
			i++;
		}
					
		// sets the start of the booking
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, timeBroken[0]); 
		date.clear(Calendar.MINUTE);
		date.set(Calendar.MINUTE, timeBroken[1]);
		date.clear(Calendar.SECOND);
		date.clear(Calendar.MILLISECOND);
		date.setFirstDayOfWeek(Calendar.MONDAY);
		
		if(day.equals("MONDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		
		else if(day.equals("TUESDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		}
		else if(day.equals("WEDNESDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);	
		}
		else if(day.equals("THURSDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		}
		else if(day.equals("FRIDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		}
		else if(day.equals("SATURDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		}
		else if(day.equals("SUNDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}
					
		return date.getTime();
	}
	// for setting the end date of the booking
	public Date getTime(Date start, int duration)
	{
		// sets the end of the booking
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(start);
		endDate.add(Calendar.MINUTE, duration);
		return endDate.getTime();
		
	}
	// for setting the times employee is working that day
	public Date getTime(Date shift, String day)
	{
		// this is the shift from the data base
		Calendar cal = Calendar.getInstance();
		cal.setTime(shift);
		
		// when you get the shift from the data base, the date is set to 1970
		// so need to covert it to equal the same day as the users entered day
		Calendar date = Calendar.getInstance();		
		date.set(Calendar.HOUR_OF_DAY, cal.get( Calendar.HOUR_OF_DAY) ); 		
		date.clear(Calendar.MINUTE);
		date.set(Calendar.MINUTE, cal.get( Calendar.MINUTE) );		
		date.clear(Calendar.SECOND);
		date.clear(Calendar.MILLISECOND);	
		date.setFirstDayOfWeek(Calendar.MONDAY);		
		if(day.equals("MONDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}		
		else if(day.equals("TUESDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		}
		else if(day.equals("WEDNESDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);	
		}
		else if(day.equals("THURSDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		}
		else if(day.equals("FRIDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		}
		else if(day.equals("SATURDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		}
		else if(day.equals("SUNDAY"))
		{
			date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		}		
		return date.getTime();
	}

}
