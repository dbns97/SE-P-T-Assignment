import java.util.Scanner;
import java.io.*;
import org.json.*;

public class OwnerMenu extends Menu{

	private Scanner sc;
    private JSONObject busInfo;
    private JSONObject custInfo;

	public OwnerMenu(Scanner scanner)
	{
		this.sc  = scanner;
        busInfo  = JSONUtils.getJSONObjectFromFile("business.json");
        custInfo = JSONUtils.getJSONObjectFromFile("customerinfo.json");
	}

	public void run()
	{
		int choice;

		do
		{
			printOwnerMenu();

	        choice = sc.nextInt();
	        // Removes newline character, clearing buffer
	        if(sc.hasNextLine()) sc.nextLine();

			// Handle choice and call appropriate function
            switch(choice)
            {
                case 1:
					addEmployee();
                    break;
                case 2:
					addShift();
                    break;
                case 3:
					viewBookings();
                    break;
                case 4:
					addBooking();
                    break;
                case 5:
					printAvailability();
                    break;
                case 6:
					logout();
                    break;
                case 7:
					exit();
                    break;
                default:
                    System.out.println("Please enter a number from 1 - 7");
            }

		} while(choice != 6 && choice != 7);
	}

	/**
	* Displays the menu with the list of options
	*
	* @param none
	* @return void
	* @author Drew Nuttall-Smith
	* @since 26/03/2017
	*/
    public void printOwnerMenu()
    {
        System.out.println("   Appointment Booking System   ");
        System.out.println("--------------------------------");
		System.out.println("1. Add employee");
		System.out.println("2. Add working times");
		System.out.println("3. View bookings summary");
		System.out.println("4. Add new booking");
		System.out.println("5. View workers' availability");
		System.out.println("6. Logout");
		System.out.println("7. Exit");
		System.out.println("please choose an option (1 - 7)");
    }

	/**
	* Add an employee to the list
	*
	* @param none
	* @return boolean - whether the new employee was added
	* @author Drew Nuttall-Smith
	* @since 27/03/2017
	*/
	public boolean addEmployee()
	{
		String name;
		String email;
		boolean uniqueEmail;
		JSONObject newEmployee;
		JSONObject employees = busInfo.getJSONObject("employees");
		String nameRegex = "[a-zA-Z ,.'-]+";
		String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+";

		do
		{
			// Get employee name
			System.out.println("Please enter name of employee:");
			name = sc.nextLine();

			// If user doesn't enter anything, cancel this operation
			if (name.length() == 0) return false;

			// If invalid format, print error message
	        if(!name.matches(nameRegex)) {
				System.out.println("Invalid name");
				continue;
			} else {
				break;
			}

		} while (!name.matches(nameRegex));

		do
		{
			// Get employee email
			System.out.println("Please enter email of employee:");
			email = sc.nextLine();
			uniqueEmail = true;

			// If user doesn't enter anything, cancel this operation
			if (email.length() == 0) return false;

			// If invalid format, print error message
	        if(!email.matches(emailRegex)) {
				System.out.println("Invalid email");
				continue;
			} else {
				// Check if email already exists in system
	            String[] existingEmails = JSONObject.getNames(employees);
				for (String e : existingEmails) {
					if (e.equals(email)) {
						System.out.println("An employee already exists with that email");
						uniqueEmail = false;
						continue;
					}
				}
				break;
			}
		} while (!email.matches(emailRegex) || !uniqueEmail);

		// Populate JSON object with employee info
        newEmployee = new JSONObject();
        newEmployee.put("name", name);
        newEmployee.put("shifts", new JSONObject());

		// Write employee JSON object to business data JSON object
		employees.put(email, newEmployee);
		busInfo.put("employees", employees);

		System.out.println("\nA new employee was added:");
		System.out.println("Name: " + name);
		System.out.println("Name: " + email + "\n");

		// TESTING: this log is for testing purposes
		System.out.println(newEmployee.toString());

		return true;
	}

	/**
	* Add a shift for an employee
	*
	* @param none
	* @return boolean - whether the new shift was successfully added
	* @author Drew Nuttall-Smith
	* @since 27/03/2017
	*/
	public boolean addShift()
	{
		JSONObject employees = busInfo.getJSONObject("employees");
		boolean employeeExists = false;
		String email;
		String date;
		int startTime;
		int endTime;
		String dateRegex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";

		do
		{
			// Get employee to add shift for
			System.out.println("Enter employee email:");
			email = sc.nextLine();

			// If user doesn't enter anything, cancel this operation
			if (email.length() == 0) return false;

			// Check if email matches an existing employee
	        String[] emails = JSONObject.getNames(employees);
			for (String e : emails) {
				if (e.equals(email)) {
					employeeExists = true;
					break;
				}
			}
			// If no employee exists with entered email address, prompt user to re-enter email
			if (!employeeExists) {
				System.out.println("No employee exists with that email");
				continue;
			}
		} while (!employeeExists);

		do
		{
			// Get date for shift
			System.out.println("Enter date of shift:");
			date = sc.nextLine();

			// If user doesn't enter anything, cancel this operation
			if (date.length() == 0) return false;

			// If invalid format, print error message
	        if(!date.matches(dateRegex)) {
				System.out.println("Invalid date");
				continue;
			} else {
				break;
			}
		} while (!date.matches(dateRegex));

		// Get start time for shift
		System.out.println("Enter start time of shift (as integer in 24hr time. e.g. 9):");
		startTime = sc.nextInt();
		// Get start time for shift
		System.out.println("Enter end time of shift (as integer in 24hr time. e.g. 17):");
		endTime = sc.nextInt();

		// Remove slashes from date
		date = date.replace("/", "");

		// Add shift to JSON
		JSONObject employee = employees.getJSONObject(email);
		JSONObject shifts = employee.getJSONObject("shifts");
		JSONArray shift = new JSONArray();
		shift.put(startTime);
		shift.put(endTime);
		shifts.put(date, shift);
		employee.put("shifts", shifts);
		employees.put(email, employee);
		busInfo.put("employees", employees);
		
		// TESTING: this log is for testing purposes
		System.out.println(shifts.toString());

		return true;
	}

	public void viewBookings()
	{

	}

	public boolean addBooking()
	{
		return false;
	}

	public void printAvailability()
	{

	}

	public void logout()
	{
		writeToFiles();
	}

	public void exit()
	{
		writeToFiles();
		System.exit(0);
	}

	public void writeToFiles() {
		/*
		File busFile;
		FileWriter busWriter;
		File custFile;
		FileWriter custWriter;

		// Write to business data file
        try
        {
			busFile = new File("business.json");
			busWriter = new FileWriter(busFile);
            busWriter.write(busInfo.toString());
        }
        catch (IOException e)
        {
			System.out.println("Error writing to business data file");
        }
		finally
		{
            busWriter.flush();
            busWriter.close();
		}

		// Write to customer data file
        try
        {
			custFile = new File("customerinfo.json");
			custWriter = new FileWriter(custFile);
            custWriter.write(custInfo.toString());
        }
        catch (IOException e)
        {
			System.out.println("Error writing to customer data file");
        }
		finally
		{
            custWriter.flush();
            custWriter.close();
		}
		*/
	}

}
