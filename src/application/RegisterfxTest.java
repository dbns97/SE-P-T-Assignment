package Application;

import static org.junit.Assert.*;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterfxTest 
{
	@Before
	public void showRegister() throws Exception
	{
		Menu.launch(PublicMenu.class);
	}
	
	//shows the menu
	public void start(Stage primaryStage) throws Exception
	{
		//primaryStage.showPublicMenu();
	}
	
	@After
	public void afterEachTest() throws TimeoutException
	{
		FxToolkit.hideStage();
		//stops from freezing keys and mouse clicks to avoid crash
		release(new KeyCode[]{});
		release(new MouseButton[]{});
	}
	
	private void release(KeyCode[] keyCodes) {
		// TODO Auto-generated method stub
		
	}
	private void release(MouseButton[] keyCodes) {
		// TODO Auto-generated method stub
		
	}
	
	@Test
	//registration successful
	public void valid_Registration()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(PublicMenu.class.getResource("RegisterForm.fxml"));
		AnchorPane registerForm = (AnchorPane) loader.load();
		RegisterFormController controller = loader.getController();
		
		RegisterFormController.click("#name").type("steve");
		RegisterFormController.click("#password").type("1234");
		RegisterFormController.click("#address").type("12 Station Street,");
		RegisterFormController.click("#contactNumber").type("0123456789");
		RegisterFormController.click("#registerButton");
		
		String usersFilepath = "../JSONdatabase/users.json";
		//private JSONObject[] loadedUsers;
		JSONObject users; 
		
		//public Business()
		{
			users = JSONUtils.getJSONObjectFromFile(usersFilepath); 
		}
		
		while (RegisterFormController.click("#name") != users)
		{
			assertTrue("Registration Successful", true);
		}		
	}
	
	@Test
	//user already exists. Registration fail
	public void invalid_Registration()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(PublicMenu.class.getResource("RegisterForm.fxml"));
		AnchorPane registerForm = (AnchorPane) loader.load();
		RegisterFormController controller = loader.getController();
		
		RegisterFormController.click("#name").type("steve");
		RegisterFormController.click("#password").type("1234");
		RegisterFormController.click("#address").type("12 Station Street,");
		RegisterFormController.click("#contactNumber").type("0123456789");
		RegisterFormController.click("#registerButton");
		
		String usersFilepath = "../JSONdatabase/users.json";
		//private JSONObject[] loadedUsers;
		JSONObject users; 
		
		//public Business()
		{
			users = JSONUtils.getJSONObjectFromFile(usersFilepath); 
		}
		
		while (RegisterFormController.click("#contactNumber") = users)
		{
			assertFalse("User already exists", false);
		}		
	}
	
}
