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

	public boolean addEmployee()
	{
		String name;
		String email;
		JSONObject newEmployee;
		JSONArray employees = busInfo.getJSONArray("employees");

		do
		{
			// Get employee name
			System.out.println("Please enter name of employee:");
			name = sc.nextLine();

			// If user doesn't enter anything, cancel this operation
			if (name.length() == 0) return false;

			// If invalid format, print error message
	        if(!name.matches("[a-zA-Z ,.'-]+")) {
				System.out.println("Invalid name");
				continue;
			} else {
				break;
			}

		} while (!name.matches("[a-zA-Z ,.'-]+"));

		do
		{
			// Get employee email
			System.out.println("Please enter email of employee:");
			email = sc.nextLine();

			// If user doesn't enter anything, cancel this operation
			if (email.length() == 0) return false;

			// If invalid format, print error message
	        if(!email.matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]")) {
				System.out.println("Invalid email");
				continue;
			} else {
				break;
			}
		} while (!email.matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]"));

		// Populate JSON object with employee info
        newEmployee = new JSONObject();
        newEmployee.put("name", name);
        newEmployee.put("email", email);
        newEmployee.put("workingTime", new JSONArray());
        newEmployee.put("workingDates", new JSONArray());

		// Write employee JSON object to business data JSON object
		employess.put(newEmployee);
		busInfo.put("employees", employees);

		return false;
	}

	public boolean addShift()
	{
		return false;
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
		// Write to business data file
        try
        {
            FileWriter busFile = new FileWriter("business.json");
            busFile.write(busInfo.toJSONString());
        }
        catch (FileNotFoundException e)
        {
			System.out.println("Error writing to business data file");
        }
		finally
		{
            busFile.flush();
            busFile.close();
		}

		// Write to customer data file
        try
        {
            FileWriter custFile = new FileWriter("customerinfo.json");
            custFile.write(custInfo.toJSONString());
        }
        catch (FileNotFoundException e)
        {
			System.out.println("Error writing to customer data file");
        }
		finally
		{
            custFile.flush();
            custFile.close();
		}
	}

}
