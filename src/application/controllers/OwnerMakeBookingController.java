package application.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import javafx.scene.text.Font;



public class OwnerMakeBookingController {

	final static Logger logger = LogManager.getLogger(OwnerMakeBookingController.class.getName());
	
	private OwnerMenu om;
	private Business business;
	private Customer currentCustomer;
	private Employee currentEmployee;
	private Service currentService;
	private Date StartTime;
	private Date EndTime;	
	@FXML
	private Label heading;
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
	private Button confirm;
	@FXML
	private Label errorLabel;
	
	// setters
	public void setMainMenu(OwnerMenu om)
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
		om.showOwnerMenu();
	}
		
	// finds the customers in the data base and adds to choice box
	public void setCustomerChoiceBox() {
		// TODO Auto-generated method stub
		String[] CustomerList = new String[business.getCustomers().size()];
		logger.debug("business.getCustomers().size() : {} ", business.getCustomers().size());
		//System.out.println(business.getCustomers().size());
		for (int i = 0 ; i < business.getCustomers().size(); i++)
		{
			CustomerList[i] = business.getCustomers().get(i).getUsername();
			logger.debug(" customer username: {}", business.getCustomers().get(i).getUsername());
			//System.out.println(business.getCustomers().get(i).getUsername());
		}
		//logger.debug("CustomerList : {}", CustomerList);
		//System.out.println(CustomerList);
		customer.setItems(FXCollections.observableArrayList( CustomerList ) );
	}
	// finds the services in the data base and adds to choice box
	public void setServiceChoiceBox()
	{				
		String[] serviceList = new String[business.getServices().size()];
		for (int i = 0 ; i < business.getServices().size(); i++)
		{
			serviceList[i] = business.getServices().get(i).getName() + " ( " + business.getServices().get(i).getDuration() + " mins )";
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
		if( service.getValue() != null)
		{
			//System.out.println("service box clicked");
			logger.info("service box clicked");
			errorLabel.setText("");
			
			if(confirm.isVisible())
				confirm.setVisible(false);
			
			
			if(timeBox.isVisible())
			{
				timeBox.setVisible(false);
			}
			if(dayBox.isVisible())
			{
				//day.setItems(null);
				dayBox.setVisible(false);
			}
			
			
			if( employeeBox.isVisible() == false)
				employeeBox.setVisible(true);
			
			setEmployeeChoiceBox();
		}
	}
	public void handleEmployeeChoiceBox()
	{
		if( employee.getValue() != null )
		{
			//System.out.println("employee box clicked");
			logger.info("employee box clicked");
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
	}
	public void handleDayChoiceBox()
	{
		if ( day.getValue() != null)
		{
			//System.out.println("day box clicked");
			logger.info("day box clicked");
			errorLabel.setText("");
			
			if(confirm.isVisible() == false)
				confirm.setVisible(true);
					
			if( timeBox.isVisible() == false)
				timeBox.setVisible(true);	
		}
	}
	public void handleConfirmButton()
	{
		if( service.getValue() == null
		|| employee.getValue() == null
		|| day.getValue() == null 
		|| time.getText().isEmpty() )
		{
			errorLabel.setText("Please enter all information first\n");
		}
		else
		{
			logger.info("confirm button clicked");
			//what will be saved to database
			/*
			System.out.println(   "\n------------------------\n"
								+ "booking details : \n" 
								+ service.getValue() +  "\n"
								+ employee.getValue() +  "\n"
								+ day.getValue() +  "\n"
								+ time.getText() 
								+ "\n------------------------\n"							
								);
			*/
			String errorMessage = checkBooking(customer.getValue(), service.getValue(), employee.getValue(), day.getValue(), time.getText(), business);
			
			if (errorMessage == null)
			{
				Booking newBooking = new Booking(StartTime, EndTime, currentEmployee, currentCustomer, currentService);
				//System.out.println("booking made");
				logger.info("booking made");
				currentCustomer.addBooking(newBooking);
				
				DatabaseHandler.writeBusinessToFile(business);
				om.showOwnerMenu();
			}
			else
			{
				errorLabel.setText(errorMessage);
			}
		}
	}

	public String checkBooking(String customer, String service, String employee, String day, String time, Business business) {
		if (service == null || employee == null || day == null || time.isEmpty()) {
			return "Please complete all fields";
		} else {
			// gets their name, not their username
			currentCustomer = business.getCustomer(customer);
			
			// get entered service
			StringTokenizer st = new StringTokenizer(service);
			String[] serviceBroken = new String[2];
			int i = 0;
			while (st.hasMoreTokens()) {
				serviceBroken[i] = st.nextToken("(");
				// logger.debug("", serviceBroken[i]);
				i++;
			}
			String newString = serviceBroken[0].trim();
			currentService = business.getService(newString);

			// and employee
			for (Employee e : business.getEmployees()) {
				if (e.getName().equals(employee)) {
					currentEmployee = business.getEmployee(e.getEmail());
				}
			}

			// get the entered time, set the dates for this week and calc the
			// end time from that
			StartTime = getTime(time, day);
			EndTime = getTime(StartTime, currentService.getDuration());
			// finds the employee starting & ending time of his shift for
			// entered day
			Date employeeStartTime = getTime(currentEmployee.getShift(day).getStart(), day);
			Date employeeEndTime = getTime(currentEmployee.getShift(day).getEnd(), day);

			logger.debug("{} - Start time of booking", StartTime);
			logger.debug("{} - end time of booking", EndTime);
			logger.debug("{} - Employee Start time of shift", employeeStartTime);
			logger.debug("{} - Employee end time of shift", employeeEndTime);

			logger.info("checking all bookings");
			logger.debug("current chosen employee : {}", currentEmployee.getEmail());
			if ((StartTime.after(employeeStartTime) || StartTime.equals(employeeStartTime))
					&& (EndTime.before(employeeEndTime) || EndTime.equals(employeeEndTime))) {
				// searches each of the customer bookings for that day
				// and checks that the booking is not going into any for their
				// bookings
				ArrayList<Customer> customers = business.getCustomers();

				for (Customer c : customers) {

					logger.debug("current customer bookings we are checking : {}", c.getUsername());
					for (Booking booking : c.getBookings()) {
						// logger.debug("checks if the booking has the same
						// employee as the one user chose");
						if (booking.getEmployee().getEmail().equals(currentEmployee.getEmail())) {
							logger.debug("- They match");

							logger.debug("\tchosen start time : {}", StartTime);
							logger.debug("\tbooking end time : {}", booking.getEnd());

							logger.debug("\tchosen end time : {}", EndTime);
							logger.debug("\tbooking start time : {}", booking.getStart());

							if ((StartTime.getTime() > booking.getStart().getTime()
									&& StartTime.getTime() < booking.getEnd().getTime())
									|| (EndTime.getTime() < booking.getEnd().getTime()
											&& EndTime.getTime() > booking.getStart().getTime())
									|| (StartTime.getTime() >= booking.getStart().getTime()
											&& EndTime.getTime() <= booking.getEnd().getTime())
									|| (StartTime.getTime() <= booking.getStart().getTime()
											&& EndTime.getTime() >= booking.getEnd().getTime())) {
								logger.info("Clashes with another booking");
								return "Your chosen time goes through another booking";
							}
						}
					}
				}
			} else {
				logger.info("Outside working times");
				return "Choose a time when employee is working";
			}
			logger.info("check booking finished");
			return null;
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
