package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddEmployeeController {
	private OwnerMenu om;
	@FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Label errorLabel;
    private Business business;
    
	public void setMainMenu(OwnerMenu om)
	{
		this.om = om;
	}
	
	public void handleBack()
	{
		om.showOwnerMenu();
	}
	
	public void setBusiness(Business business)
	{
		this.business = business;
	}
	
	public boolean handleAddEmployee()
	{
		//File file = new File("src/JSONdatabase");
		//for(String fileNames : file.list()) System.out.println(fileNames);
		
		//JSONObject employees = JSONUtils.getJSONObjectFromFile("../JSONdatabase/employees.json");
		//System.out.println(employees.toString());
	    
		//errorLabel.setWrapText(true);
		if(name.getText().trim().isEmpty() || !(name.getText().matches("[a-zA-Z ,.'-]+")))
		{
			errorLabel.setText("Please enter a valid name");
			return false;
		}
		
		if(email.getText().isEmpty() || !(email.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+")))
		{
			errorLabel.setText("Please enter a valid email");
			return false;
		}
		
		JSONArray existingEmails = business.getEmployees().names();
		
		// Scans the emails JSONArray to check if the employee already exists
        int i = 0;
        if(existingEmails != null)
        {
            while(!existingEmails.isNull(i))
            {
            	System.out.println(existingEmails.getString(i));
                if(email.getText().equals(existingEmails.getString(i)))
                {
                	errorLabel.setText("Employee already exists with that email");
                    return false;
                }

                i++;
            }
        }
        
		Employee newEmployee = new Employee(email.getText(), name.getText());
		
		business.addEmployee(email.getText(), newEmployee.toJSONObject());
		
		errorLabel.setText("Successfully registered new employee");

		/*
		try
        {
            FileWriter employeeWriter = new FileWriter("src/JSONdatabase/employees.json");
            employeeWriter.write(employees.toString(4));
            employeeWriter.flush();
            employeeWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        */

		return true;
        
	}

}
