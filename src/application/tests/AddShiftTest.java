package application.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.sun.javafx.application.PlatformImpl;

import application.controllers.AddShiftController;
import org.junit.Assert;
import org.junit.BeforeClass;

@RunWith(Parameterized.class)
public class AddShiftTest {

	@Parameters
    public static Collection<Object[]> data() 
    {
    	//Test Structure: {starting time of shift, ending time of shift, expected error message}
    	return Arrays.asList(new Object[][] {     
            {"1:30", "2:30", null},
            {"3:30", "2:30", "End time is before start time"},
            {"12:30", "14:30", null},
            {"19:30", "14:30", "End time is before start time"},
            {"Word", "2:30", "Please enter a valid start time"},
            {"1:30", "Word", "Please enter a valid end time"},
            {"130", "2:30", "Please enter a valid start time"},
            {"", "2:30", "Please enter a valid start time"},
            {"1:30", " ", "Please enter a valid end time"},
            {"1:30!", "2:30", "Please enter a valid start time"},
            {"1:30", "24:30", "Please enter a valid end time"},
            {"-1:30", "2:30", "Please enter a valid start time"}
            });
    }
    
    @Parameter(0)
    public String startTime;
    @Parameter(1)
    public String endTime;
    @Parameter(2)
    public String expected;
    
	@Test
	public void checkShiftTimesTest() {
		AddShiftController controller = new AddShiftController();
		String result = controller.checkShiftTimes(startTime, endTime);
		Assert.assertEquals(expected, result);
		
	}

}
