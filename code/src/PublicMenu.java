
import java.util.Scanner;
import java.io.*;
import org.json.*;

public class PublicMenu extends Menu{

	private Scanner sc;
    private JSONObject busInfo;
    private JSONObject custInfo;
   
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
        
        JSONObject busInfo = JSONUtils.getJSONObjectFromFile("business.json");
        JSONObject custInfo = JSONUtils.getJSONObjectFromFile("customerinfo.json");
        		
		// Get username from user
        do
        {
        	String username;
        	
        	// Clearing \n character from before
        	sc.nextLine();
        	
        	while(true)
        	{
        		System.out.println("Please enter your Username:");
        		username = sc.nextLine();
        		if(!username.trim().isEmpty()) break;
        	}
            
            String ownerUsername = busInfo.getString("username");
            JSONArray usernames = custInfo.names();
            
            // Checks if the username is same as the owners
            if(username.equals(ownerUsername))
            {
                System.out.println("Username already exists (Ctrl-D to return to main menu)");
                continue;
            }
            
            // Scans the usernames JSONArray to check if the username already exists
            int i = 0;
            if(usernames != null)
            {
            	uniqueUsername = true;
            	while(!usernames.isNull(i))
                {
                    if(username.equals(usernames.getString(i)))
                    {
                    	System.out.println("Username already exists (Ctrl-D to return to main menu)");
                    	uniqueUsername = false;
                        break;
                    }
                    
                    i++;
                }            
            }
            else
            {                
                uniqueUsername = true;
            }
            
            if(uniqueUsername)
            {
            	// Holds all the user information to be written on file
            	JSONObject newUser = new JSONObject();
            	
            	String password;
            	while(true)
            	{
            		System.out.println("Please enter your Password:");
            		 password = sc.nextLine();
            		if(!password.trim().isEmpty()) break;
            	}
            	newUser.put("password", password);
            	
            	String name;
            	while(true)
            	{
            		System.out.println("Please enter your Name:");
            		name = sc.nextLine();
            		if(name.matches("[a-zA-z ,.'-]+") && !name.trim().isEmpty()) break;
            	}
                newUser.put("name", name);
                
                String address;
            	while(true)
            	{
            		System.out.println("Please enter your Address (One line):");
            		address = sc.nextLine();
            		if(address.matches("[a-zA-z0-9 ,.'-]+") && !address.trim().isEmpty()) break;
            	}
            	newUser.put("address", address);
                
            	String number;
            	while(true)
            	{
            		System.out.println("Please enter your Contact Number (Only digits):");
            		number = sc.nextLine();
            		if(number.matches("[0-9]+") && !number.trim().isEmpty()) break;
            	}
                newUser.put("contact number", number);
                
                newUser.put("booking status", "not booked");
                newUser.put("booking time", -1);
                custInfo.put(username, newUser);
                
                try
                {
                	PrintWriter custWriter = new PrintWriter("code/src/customerinfo.json");
                	custWriter.print(custInfo.toString(4));
                	custWriter.close();
                }
                catch (FileNotFoundException e)
                {
                	e.printStackTrace();
                }
                
                registerSuccess = true;
            }
        }
        while (!registerSuccess);
        
        return true;
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
    {
        System.out.println("   Appointment Booking System   ");
        System.out.println("--------------------------------");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("Please choose an option (1,2,3)");
    }
}
