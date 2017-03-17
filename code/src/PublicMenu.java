package code;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
        String username;
		String password;
        JSONArray array = (JSONArray) parser.parse(new FileReader("customerinfo.json"));
        
        for (Object object:array)
        {
            JSONObject  
        }
		
		// Get username from user
		System.out.println("Enter the Username:");
		username = sc.next();
        
        
        
        
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
