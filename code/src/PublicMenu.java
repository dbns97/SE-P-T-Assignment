package code;
import java.util.Scanner;

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
                      System.out.println("you have successfully logged in.");
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
		
	}
	
	public boolean login()
   {
      Boolean loginSuccess = false;
      String username, password;
      
      // reads in the two databases
      JSONObject busInfo = JSONUtils.getJSONObjectFromFile("business.json");
      JSONObject custInfo = JSONUtils.getJSONObjectFromFile("customerinfo.json");
      
      System.out.println("Please enter your username:");
      
      do
      {
         
         username = sc.next();
         
         // gets all the user names (keys) and adds them to an array, "key" : "values"
         String[] custUsernames = JSONObject.getNames(custInfo);
         
         // searches customers database
         for(int i=0; i<custUsernames.length; i++)
         {
            String jsonKey = custUsernames[i];
            if(jsonKey.equals(username))
            {
               JSONObject customer = custInfo.getJSONObject(username);
               
               
               System.out.println("Please enter your password:");
               password = sc.next();

               System.out.println( customer.getString("password") );
               System.out.println( password );
               
               if ( password.equals( customer.getString("password") ) )
               {
                  System.out.println("You have successfully logged in.");
                  return true;
               }
               else
               {
                  System.out.println("your password doesn't match the one for " + jsonKey );
               }
               
               
               break;
            }         
         }
         // searches business database
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
