package cui; // command line/console user interface


/**
 * @title User Interface Classes
 *
 *
 * @author W. K.  Burke
 *
 */


import java.util.*;
import java.util.regex.*;

import model.ModelClasses.*;
import service.ServiceClasses.*;
import api.ResourceClasses.*;

public class UserInterfaceClasses
{
	static Scanner CLI = new Scanner(System.in);

	public static class MainMenu
	{
		HotelResource HR;
		AdminResource AR;

		final static MainMenu mmInstance = new MainMenu();
		public static MainMenu getInstance() { return mmInstance; }
		
		public static MainMenu launch()
		{
			System.out.println("Welcome to the Hotel Reservation Application\n");
			return mmInstance;
		}

		private MainMenu()
		{
			HR = HotelResource.getInstance();
			AR = AdminResource.getInstance();
		}
		
		public void mainMenuManager()
		{
			AdminMenu AM = AdminMenu.getInstance();
			
			int choice;
		
			while(true)
			{
				System.out.print(this); // print main menu

					//get menu selection from user
				try 
					{ choice = Integer.parseInt( CLI.nextLine() ); }
			
				catch(Exception ex)
					{ System.out.println("\nInvalid Input!!\n"); continue; }

				//System.out.println();
					
				switch(choice)
				{
					case 1:
						System.out.println(choice + "\n"); // reserve a room
					break;
				
					case 2:
						System.out.println(choice + "\n"); // see my reservations
					break;
				
					case 3:
						createAccount();
						System.out.println();
					break;
				
					case 4:
						System.out.println();
						AM.adminMenuManager();
					break;
				
					case 5:
						CLI.close();
					return;

					default:
						System.out.println("\nInvalid Input!!\n");
				} // end of switch
			} // end of while
		} // end mainMenuManager


		public void createAccount()
		{
			String email, lastName, firstName;

			boolean valid;

			System.out.println("\n");

			do {
				System.out.print("Please enter your email address, as name@domain.com: ");
				email = CLI.nextLine();

				valid = HR.isEmailValid(email);

			} while(!valid);

			System.out.print("Please enter your last name: ");
			lastName = CLI.nextLine();

			System.out.print("Please enter your first name: ");
			firstName = CLI.nextLine();

			HR.createACustomer(email, firstName, lastName);
		}

		@Override 
		public String toString()
		{
			String line = "", items, prompt;

			for(int i = 0; i < 35; i++)
		 		line += "#";

			items = "\n1.\tFind and reserve a room\n2.\tSee my reservations\n3.\tCreate an account\n4.\tAdmin\n5.\tExit\n";

			prompt = "\n\nPlease enter the number corresponding to the menu option: ";

			return "Main Menu\n" + line + items + line + prompt;
		}
	}

	public static class AdminMenu
	{
		HotelResource HR;
		AdminResource AR;

		final static AdminMenu amInstance = new AdminMenu();
		public static AdminMenu getInstance() { return amInstance; }

		private AdminMenu()
		{
			HR = HotelResource.getInstance();
			AR = AdminResource.getInstance();
		}
		
		boolean isAuthentic()
		{
			String pw; // admin password
			
			int pwHash = -2038048907; // masteryoda, nbtufszpeb, adoyretsam
			
			System.out.print("Please enter the Admin password: ");
			
			pw = CLI.nextLine();
			
			if( pwHash == pw.hashCode() )
				{ System.out.println(); return true; }
			else
				{ System.out.println("\nIncorrect password!!\n"); return false; }
		}
		
		public void adminMenuManager()
		{
			if( !isAuthentic() )
				return;
			
			int choice;

			while(true)
			{
				System.out.print(this); // print admin menu
			
					//get menu selection from user
				try 
					{ choice = Integer.parseInt( CLI.nextLine() ); }
			
				catch(Exception ex)
					{ System.out.println("\nInvalid Input!!\n"); continue; }

				//System.out.println();

				switch(choice)
				{
					case 1:
						displayAllCustomers();
						//System.out.println(choice + "\n");
					break;
				
					case 2:
						displayAllRooms();
						//System.out.println(choice + "\n"); //rooms
					break;
				
					case 3:
						AR.displayAllReservations();
						System.out.println("\n");
					break;
				
					case 4:
						addRooms();
						//System.out.println(choice + "\n");
					break;
				
					case 5:
						System.out.println(); // return to main menu
						return;
				
					default:
						System.out.println("\nInvalid Input!!\n");
				}
			}
		}

		void displayAllRooms()
		{
			Collection<IRoom> rooms = AR.getAllRooms();
			
			if (rooms.size() == 0)
				System.out.println("\nThere No Rooms To Display\n");
			else
			{
				System.out.println("\nHotel Rooms Listing");

				for(IRoom R: rooms)
					System.out.println("\n" + R + "\n");
			
				System.out.println();
			}
		}

		public void addRooms()
		{
			ArrayList<IRoom> rooms = new ArrayList<IRoom>();
			String ans, roomNumber, type;
			Double price;

			RoomType RT;

			System.out.println();

			do {

				while(true)
				{
					System.out.print("Enter room number: ");
					roomNumber = CLI.nextLine();

					if( roomNumber.length() > 0 )
						break;
				}

				while(true)
				{
					try
					{	
						System.out.print("Enter price per night: ");
						price = Double.valueOf( CLI.nextLine() );
						break;
					}

					catch(Exception ex)
					{
						System.out.println("\nInvalid Input!!\n");
						continue;
					}
				}

				while(true)
				{
					System.out.print("Enter room type: 1 for single bed, 2 for double bed: ");
					type = CLI.nextLine();

					if( type.length() == 0 )
						continue;

					if( "1 2".contains(type) )
						break;
				}

				if( type.compareTo("1") == 0 )
					RT = RoomType.SINGLE;
				else
					RT = RoomType.DOUBLE;

				if (price == 0.0)
					rooms.add( new FreeRoom(roomNumber, RT) );
				else
					rooms.add( new Room(roomNumber, price, RT) );

				do {
				
					System.out.println("\nWould you like to add another room?");
					System.out.print("Please enter, Y(y/yes/Yes/YES) or N(n/no/No/NO): ");
					ans = CLI.nextLine();

				} while( (ans.toLowerCase().compareTo("yes") != 0) && (ans.toLowerCase().compareTo("y") != 0) &&
						(ans.toLowerCase().compareTo("no") != 0) && (ans.toLowerCase().compareTo("n") != 0) );
				
				System.out.println();

			} while( (ans.toLowerCase().compareTo("yes") == 0) || (ans.toLowerCase().compareTo("y") == 0) );

			AR.addRooms(rooms); System.out.println("\n");
		}
		// end addRooms()
		
		
		@Override 
		public String toString()
		{
			String line = "", items, prompt;

			 for(int i = 0; i < 35; i++)
		 		line += "*";

			items = "\n1.\tSee all Customers\n2.\tSee all Rooms\n3.\tSee all Reservations\n4.\tAdd a Room\n5.\tBack to Main Menu\n";

			prompt = "\n\nPlease enter the number corresponding to the menu option: ";

			return "Admin Menu\n" + line + items + line + prompt;
		}

		public void displayAllCustomers()
		{
			Collection<Customer> customers = AR.getAllCustomers();

			if (customers.size() == 0)
				System.out.println("\nThere No Customers To Display\n");
			else
			{
				for(Customer C: customers)
					System.out.println("\n" + C + "\n");
			}
		}
	}
}
