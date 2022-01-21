// HotelApplication.java

/**
 * @title Hotel Reservation Application
 *
 *
 * @author W. K.  Burke
 *
 */


import cui.UserInterfaceClasses.*;


public class HotelApplication
{
	public static void main(String[] args) //throws Exception
	{
		MainMenu MM = MainMenu.getInstance();
		//AdminMenu AM = AdminMenu.getInstance();
		
		System.out.println("Welcome to the Hotel Reservation Application\n");
		
		MM.mainMenuManager();
	}
}
