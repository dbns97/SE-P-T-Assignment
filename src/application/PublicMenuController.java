package application;

public class PublicMenuController {
	private PublicMenu pm;
	
	public void setMainMenu(PublicMenu pm)
	{
		this.pm = pm;
	}
	
	public void handleLoginMenu()
	{
		
		//pm.showLoginMenu();
	}
	
	public void handleRegister()
	{
		pm.showRegister();
	}
	
}
