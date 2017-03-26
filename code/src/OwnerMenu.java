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

	}

	public void exit()
	{
		System.exit(0);
	}

}
