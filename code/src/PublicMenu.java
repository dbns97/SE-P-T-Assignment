package code;
import java.util.Scanner;
import org.json.simple.*

import org.json.JSONObject;
import org.json.simple.*;
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
                   if (didLogin == false)
                   {
                      System.out.println("login fail");
                   }
                   else
                   {
                      /*
                       *  call customer or owner menu? 
                       *  or should we do this from the login function?
                       */
                   }
                   
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
        while( choice != 3 );
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
      Boolean loginSuccess = false;
      Boolean userNameExist = false;
      Boolean passCorrect = false;
      String username, password;
      
      // reads in the two databases
      JSONObject busInfo = JSONUtils.getJSONObjectFromFile("business.json");
      JSONObject custInfo = JSONUtils.getJSONObjectFromFile("customerinfo.json");
      
      System.out.println("Please enter your username:");
      
      do
      {
         
         username = sc.next();
         
         // gets the business database username
         String BusDBUsername = busInfo.getString("username");
         
         //checks user input with database
         if( username.equals(BusDBUsername) )
         {
            userNameExist = true;
            System.out.println("\nYou have successfully logged in.\n");
            return true;
         }
         else
         {
            // gets all the user names (keys) and adds them to an array, "key" : "values"
            String[] custUsernames = JSONObject.getNames(custInfo);
            
            // searches customers database
            for(int i=0; i<custUsernames.length; i++)
            {
               String jsonKey = custUsernames[i];
               if(jsonKey.equals(username))
               {
                  userNameExist = true;
                  JSONObject customer = custInfo.getJSONObject(username);
                  
                  System.out.println("\nPlease enter your password:");
                  
                  do
                  {
                     
                     password = sc.next();
                    
                     if ( password.equals( customer.getString("password") ) )
                     {
                        System.out.println("\nYou have successfully logged in.\n");
                        passCorrect = true;
                        return true;
                     }
                     else
                     {
                        System.out.println("\nYour password doesn't match the one for: \"" + jsonKey + "\"");
                        System.out.println("Please re-enter your password: (Ctrl-D to return to main menu)");
                     }
                  }
                  while(passCorrect != true);
                  
                                    
                  break;
               }         
            }
         }
         
         if(userNameExist == false)
         {
            System.out.println("This username doesn't exist: \"" + username + "\"");
            
         }
         
         userNameExist = false;
         System.out.println("Please re-enter your username: (Ctrl-D to return to main menu)");
         
      }
      while(loginSuccess != true);
      
      return false;

    }
	
    public void printMainMenu()
   `{
        System.out.println("   Appointment Booking System   ");
        System.out.println("--------------------------------");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Please choose an option (1,2,3)");
    }
}
