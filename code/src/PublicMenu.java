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
        Boolean uniqueUsername = false;
        Boolean registerSuccess = false;
        
        // Loading necessary json files
        JSONObject busInfo = JSONUtils.getJSONObjectFromFile("business.json");
        JSONObject custInfo = JSONUtils.getJSONObjectFromFile("customerinfo.json");
		
		// Get username from user
        do
        {
            System.out.println("Please enter your Username:");
            String username = sc.next();
            
            String ownerUsername = busInfo.getString("username");
            JSONArray usernames = custInfo.names();
            
            // Checks if the username is same as the owners
            if(username.equals(ownerUsername))
            {
                System.out.println("Username already exists");
                continue;
            }
            
            int i = 0;
            if(usernames != null)
            {
                uniqueUsername = true;                
            }
            else
            {
                while(!usernames.isNull(i))
                {
                    if(username.equals(usernames.getString(i)))
                    {
                        break;
                    }
                
                    i++;
                }
                
                uniqueUsername = true;
            }
            
            if(uniqueUsername)
            {
                System.out.println("Please enter your Password:");
                String password = sc.next();
                
                System.out.println("Please enter your Name:");
                String name = sc.next();
                
                System.out.println("Please enter your Address:");
                String address = sc.next();
                
                System.out.println("Please enter your Contact Number:");
                String number = sc.next();
                
                
            }
        }
        while (!registerSuccess)
        
        
        
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
    
    public void 
	
}
