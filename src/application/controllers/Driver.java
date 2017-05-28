package application.controllers;
import application.views.*;
	
import javafx.application.Application;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Driver {
	
	private static final Logger logger = LogManager.getLogger(Driver.class.getName());
	final static Level PROGRAM = Level.forName("PROGRAM", 0);
	
	public static void main(String[] args) {
		logger.printf(PROGRAM, "Starting Program");
		Application.launch(PublicMenu.class, args);
		logger.printf(PROGRAM, "Ending Program");
	}

}
