package code;
import java.util.Scanner;
import org.json.simple.*

public class PublicMenu extends Menu{

	private Scanner sc;
    JSONParser parser = new JSONParser();
	
	public PublicMenu()
	{
		sc = new Scanner(System.in);
	}
	
	public void run()
    {
        int choice;
        do
        {
		  printMainMenu();
        
            choice = sc.nextInt();

            switch(choice)
            {
            case 1:
                Boolean didLogin = login();
                //System.out.println(didLogin);
                break;
            case 2:
                Boolean didRegister = register();
                //System.out.println(didRegister);
                break;
            case 3:
                exit();
                break;
            default:
                System.out.println("Please enter a number from 1 - 3");
            }
        }
        while
	}
	
	public boolean register()
	{
		
	}
	
	public boolean login()
	{
		
	}
    
    public void printMainMenu()
    {
        System.out.println("   Appointment Booking System   ");
        System.out.println("--------------------------------");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Please choose an option (1,2,3)");
    }
	
}
