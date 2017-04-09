package Application;

import static org.junit.Assert.*;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class shiftfxTest 
{
	private static final String Date = null;

	@Before
	public void AddShiftController() throws Exception
	{
		Menu.launch(OwnerMenu.class);
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
	//successful shift add
	public void valid_shiftSuccessful()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("OwnerMenu.fxml"));
		AnchorPane publicMenu = (AnchorPane) loader.load();
		OwnerMenuController controller = loader.getController();
		
		OwnerMenuController.click("#startTime").type(1pm);
		OwnerMenuController.click("#endTime").type(5pm);
		OwnerMenuController.click("#addShiftButton");
		OwnerMenuController.click("#employee");
		
		JSONObject jsonShift = new JSONObject();
		
		while (OwnerMenuController.click("employee") && OwnerMenuController.click("#startTime") != jsonShift)
		{
			assertTrue("Shift successfully added", true);
		}	
	}
	
	@Test
	//employee field empty or employee doesn't exist
	public void null_shiftFail()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("OwnerMenu.fxml"));
		AnchorPane publicMenu = (AnchorPane) loader.load();
		OwnerMenuController controller = loader.getController();
		
		OwnerMenuController.click("#startTime").type(1pm);
		OwnerMenuController.click("#endTime").type(5pm);
		OwnerMenuController.click("#addShiftButton");
		OwnerMenuController.click("#employee");
		
		JSONObject jsonShift = new JSONObject();
		
		while (OwnerMenuController.click("employee") != jsonShift || OwnerMenuController.click("employee") == null)
		{
			assertFalse("Shift not added!", true);
		}	
	}
	
	@Test
	//shift already exists
	public void shiftExist()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("OwnerMenu.fxml"));
		AnchorPane publicMenu = (AnchorPane) loader.load();
		OwnerMenuController controller = loader.getController();
		
		OwnerMenuController.click("#startTime").type(1pm);
		OwnerMenuController.click("#endTime").type(1pm);
		OwnerMenuController.click("#addShiftButton");
		OwnerMenuController.click("#employee");
		
		JSONObject jsonShift = new JSONObject();
		
		while (OwnerMenuController.click("#startTime") == jsonShift && OwnerMenuController.click("#endTime") == jsonShift)
		{
			assertFalse("Shift already exists!", true);
		}	
	}
	
	@Test
	//shift too long (assuming a shift goes for 5h)
	public void shiftLong()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("OwnerMenu.fxml"));
		AnchorPane publicMenu = (AnchorPane) loader.load();
		OwnerMenuController controller = loader.getController();
		
		OwnerMenuController.click("#startTime").type(1pm);
		OwnerMenuController.click("#endTime").type(5pm);
		OwnerMenuController.click("#addShiftButton");
		OwnerMenuController.click("#employee");
		
		JSONObject jsonShift = new JSONObject();
		
		while (OwnerMenuController.click("#startTime") + OwnerMenuController.click("#endTime") != 5)
		{
			assertFalse("Shift already exists!", true);
		}	
	}	

}
