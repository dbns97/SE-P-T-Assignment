package application.tests;

import java.util.concurrent.CountDownLatch;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.sun.javafx.application.PlatformImpl;

//Sets up the JavaFX platform to support JavaFX methods
public class JFXRunner extends BlockJUnit4ClassRunner {

	public JFXRunner(final Class<?> c) throws InitializationError
	  {
	    super(c);
	    try
	    {
	    	//Countsdown latch once the platform starts
	    	final CountDownLatch latch = new CountDownLatch(1);

			PlatformImpl.startup(() -> {
			});

			latch.countDown();

			latch.await();
	    }
	    catch (final InterruptedException e)
	    {
	    	e.printStackTrace();
	    }
	  }
}
