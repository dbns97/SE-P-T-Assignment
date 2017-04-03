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

public class AddShiftController {
	private OwnerMenu om;
	@FXML
    private TextField email;
	@FXML
    private TextField date;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private Button addShiftButton;
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
	
	public boolean handleAddShift()
	{
		//File file = new File("src/JSONdatabase");
		//for(String fileNames : file.list()) System.out.println(fileNames);
		
		//JSONObject employees = JSONUtils.getJSONObjectFromFile("../JSONdatabase/employees.json");
		//System.out.println(employees.toString());
	    
		//errorLabel.setWrapText(true);
		
		if(email.getText().isEmpty() || !(email.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+")))
		{
			errorLabel.setText("Please enter a valid email");
			return false;
		}
        
		if(date.getText().trim().isEmpty() || !(date.getText().matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")))
		{
			errorLabel.setText("Please enter a valid date");
			return false;
		}
		
		if(startTime.getText().trim().isEmpty() || !(startTime.getText().matches("[0-9]{1,2}")))
		{
			errorLabel.setText("Please enter a valid start time");
			return false;
		}
		
		if(endTime.getText().trim().isEmpty() || !(endTime.getText().matches("[0-9]{1,2}")))
		{
			errorLabel.setText("Please enter a valid end time");
			return false;
		}
		
		JSONArray existingEmails = business.getEmployees().names();
		boolean foundEmployee = false;
		
		// Scans the emails JSONArray to check if the employee exists
        int i = 0;
        if(existingEmails != null)
        {
            while(!existingEmails.isNull(i))
            {
            	System.out.println(existingEmails.getString(i));
                if(email.getText().equals(existingEmails.getString(i)))
                {
                	foundEmployee = true;
                }

                i++;
            }
        }
        
        if (!foundEmployee) return false;
        
        
        // CONTINUE HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		errorLabel.setText("Successfully created shift for employee with email: " + email.getText());
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
		//System.out.println(JSONUtils.getJSONObjectFromFile("employees.json").toString());
		return true;
        
	}

}
