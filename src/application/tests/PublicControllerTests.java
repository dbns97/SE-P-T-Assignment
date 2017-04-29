package application.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import application.controllers.PublicMenuController;
import application.models.Business;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@RunWith(JFXRunner.class)
public class PublicControllerTests {

	@Test
	public void test() {
		Business business = new Business();
		PublicMenuController controller = new PublicMenuController();
		mock(controller);
		Assert.assertFalse(controller.checkLogin());
	}
	
	//Encapsulates the controller in dummy data
	public void mock(PublicMenuController controller)
	{
		Business business = new Business();
		controller.setBusiness(business);
		controller.setErrorLabel(new Label());
		controller.setUsername(new TextField());
		controller.setPassword(new PasswordField());
	}

}
