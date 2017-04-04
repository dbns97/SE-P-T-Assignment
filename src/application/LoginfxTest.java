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


public class LoginfxTest 
{
	@Before
	public void loginClass() throws Exception
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
	//successful login
	public void validInput_loginSuccessful()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("PublicMenu.fxml"));
		AnchorPane publicMenu = (AnchorPane) loader.load();
		PublicMenuController controller = loader.getController();
		
		PublicMenuController.click("#username").type("steve");
		PublicMenucontroller.click("#password").type("1234");
		PublicMenuController.click("#loginbutton");
		
		verifyThat("users.json", contains("#username"));
		verifyThat("users.json", contains("#password"));
	}
	
	@Test
	//password doesn't match username
	public void invalidInput_loginFail()
	{
		String usersFilepath = "../JSONdatabase/users.json";
		//private JSONObject[] loadedUsers;
		JSONObject users; 
		
		//public Business()
		{
			users = JSONUtils.getJSONObjectFromFile(usersFilepath); 
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("PublicMenu.fxml"));
		AnchorPane publicMenu = (AnchorPane) loader.load();
		PublicMenuController controller = loader.getController();
		
		PublicMenuController.click("#username").type("steve");
		PublicMenuController.click("#password").type("1234");
		PublicMenuController.click("#loginbutton");
		
		while(PublicMenuController.click("#username") == users 
				      && PublicMenuController.click("#password") != users)
		{
			assertFalse("Login Failed! Incorrect Password!", true);
		}
	}

	@Test
	//username or password contains wrong format
	public void characters_loginFail()
	{
		String characters = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+";
		
		PublicMenuController.click("#username").type("steve");
		PublicMenuController.click("#password").type("1234");
		PublicMenuController.click("#loginbutton");
		
		while(PublicMenuController.click("#username") != characters
			      || PublicMenuController.click("#password") != characters)
	{
		assertFalse("Login Failed! Incorrect Password!", true);
	}
		
	}
	
	@Test
	//user doesn't exist
	public void exist_loginFail()
	{
		String usersFilepath = "../JSONdatabase/users.json";
		//private JSONObject[] loadedUsers;
		JSONObject users; 
		
		//public Buisness()
		{
			users = JSONUtils.getJSONObjectFromFile(usersFilepath); 
		}
		
		PublicMenuController.click("#username").type("steve");
		PublicMenuController.click("#password").type("1234");
		PublicMenuController.click("#loginbutton");
		
		while( PublicMenuController.click("#username") != users 
			      && PublicMenuController.click("#password") != users)
		{
			assertFalse("User doesn't exist! Please try again!", true);
		}
		
	}

}
