package application.models;
import application.views.*;
import application.controllers.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;

public class Business {
	private String usersFilepath = "../../JSONdatabase/users.json";
	private String employeesFilepath = "../../JSONdatabase/employees.json";
	//private JSONObject[] loadedUsers;
	private JSONObject users;
	private ArrayList<Employee> employees;
	
	public Business()
	{
		users = JSONUtils.getJSONObjectFromFile(usersFilepath);
		employees = initialiseEmployees();
	}
	
	public JSONObject getUsers()
	{
		return users;
	}
	
	public ArrayList<Employee> getEmployees()
	{
		return employees;
	}
	
	/**
	 * @description return an employee with a specific email
	 * @param email the email of the employee to be returned
	 * @return Employee the employee
	 * @author Drew Nuttall-Smith
	 * @since 3/4/2017
	 **/
	public Employee getEmployee(String email)
	{
		for (Employee emp : employees)
		{
			if (emp.getEmail().equals(email))
			{
				return emp;
			}
		}
		
		return null;
	}
	
	/*
	public void loadUsers()
	{
		System.out.println("LOADING");
		
		ArrayList<JSONObject> loadedUsersList = new ArrayList<JSONObject>();
		JSONArray usernames = users.names();
		System.out.println(usernames.toString());
		
		int i = 0;
        if(usernames != null)
        {
            while(!usernames.isNull(i))
            {
            	loadedUsersList.add(users.getJSONObject(usernames.getString(i)));
                i++;
            }
        }
        
        loadedUsers = loadedUsersList.toArray(new JSONObject[0]);
        
	}
	*/
	
	public void addUser(String username, JSONObject newUserData)
	{
		users.put(username, newUserData);
		/*
		JSONObject[] newArray = new JSONObject[loadedUsers.length + 1];
		for (int i = 0; i < loadedUsers.length; i++){
	        newArray[i] = loadedUsers[i];
	    }
		newArray[newArray.length - 1] = newUser;
		loadedUsers = newArray;
		*/
	}
	
	public void addEmployee(String email, String name)
	{
		Employee newEmployee = new Employee(email, name);
		employees.add(newEmployee);
	}
	
	public void updateFile()
	{
		try
        {
            FileWriter custWriter = new FileWriter("src/JSONdatabase/users.json");
            custWriter.write(users.toString(4));
            custWriter.flush();
            custWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}

	/*-------------------- Private Methods --------------------*/
	
	/**
	 * @description create an ArrayList containing all employees from the employees JSON file
	 * @return ArrayList<Employee> the list of all employees
	 * @author Drew Nuttall-Smith
	 * @since 3/4/2017
	 **/
	private ArrayList<Employee> initialiseEmployees()
	{
		ArrayList<Employee> employees = new ArrayList<Employee>();
		JSONObject jsonEmployees = JSONUtils.getJSONObjectFromFile(employeesFilepath);
		JSONArray emails = jsonEmployees.names();
		
		// Iterate over each employee
        int i = 0;
        if(emails != null)
        {
            while(!emails.isNull(i))
            {
            	String email = emails.getString(i);
            	String name = jsonEmployees.getJSONObject(email).getString("name");
            	JSONArray jsonShifts = jsonEmployees.getJSONObject(email).getJSONArray("shifts");
            	
            	employees.add(new Employee(email, name, jsonShifts));

                i++;
            }
        }
        
        return employees;
		
	}

}
