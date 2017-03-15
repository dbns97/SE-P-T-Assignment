package code;
import java.util.Scanner;

public abstract class Menu {
	
	
	//In a loop, prints the menu and runs the other functions
	abstract void run();
	
	//Ends the program
	public void exit()
	{
		System.exit(0);
	}
}
