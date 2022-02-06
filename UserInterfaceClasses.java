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
					
				switch(choice)
				{
					case 1:
						reserveRoom();
					break;
				
					case 2:
						displayCustomerReservations();
					break;
				
					case 3:
						createAccount( getEmail() );
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
				}
				// end of switch
			}
			// end of while
		}
		// end mainMenuManager

		void displayCustomerReservations()
		{
			String email = getEmail();
			
			Customer C = HR.getCustomer(email);
			
			if (C == null)
			{
				System.out.println("\nYour Are Not A Current Customer\n");
				return;
			}
			
			Collection<Reservation> reserves = HR.getCustomerReservations(email);
			
			if (reserves == null)
				System.out.println("\nYou Have No Reservations\n");
			else
			{ 
				System.out.println("\nYour Reservations Are:\n");
			
				for(Reservation R:  reserves)
					System.out.println(R + "\n");
			}
		}

		String getEmail()
		{
			String email;
			boolean valid;
			
			do {
				System.out.print("\nPlease enter your email address, as name@domain.com: ");
				email = CLI.nextLine();

				valid = HR.isEmailValid(email);

			} while(!valid);
			
			return email;
		}
		
		public void reserveRoom()
		{
			String email = getEmail();
			
			Customer C = HR.getCustomer(email);

			String ans;
			
			if( C == null )
				C = createAccount(email);
			else
			{
				System.out.print("\nAn account was found for the email entered:\n\n" + C + "\n\nPlease confirm(Y/n) ");
				ans = CLI.nextLine();
				
				if (ans.toLowerCase().compareTo("n") == 0)
				{
					System.out.println("\nOK, let's create a new account!");
					C = createAccount( getEmail() );
					email = HR.getCustomerEmail(C);
				}
			}
			
			String cidStr, codStr; // check IN/OUT dates;
			
			Date cid, cod;
			
			Collection<IRoom> rooms;
			String rm;
			boolean valid = false;
			
			while(true)
			{
				while(true)
				{
					try
					{
						System.out.print("\nPlease enter your check in date as (mm/dd/yyy): ");
						cidStr = CLI.nextLine();
						cid = HR.getDateInstance(cidStr, true);
						break;
					}
					catch(Exception ex)
						{ System.out.println("\n" + ex.getMessage() ); }
				}
			
				while(true)
				{
					try
					{
						System.out.print("\nPlease enter your check out date as (mm/dd/yyy): ");
						codStr = CLI.nextLine();
						cod = HR.getDateInstance(codStr, false);
						break;
					}
					catch(Exception ex)
						{ System.out.println("\n" + ex.getMessage() ); }
				}
			
				if( !cid.before(cod) )
					System.out.println("\nThe check in date must precede the check out date!!");
				else
				{
					rooms = HR.findARoom(cid, cod);
					
					if (rooms.size() == 0)
					{
						System.out.println("\nSorry there are no rooms available, ");
						System.out.print("Would you like try again with different check in/out dates?(Y/n) ");
						ans = CLI.nextLine();
						
						if( (ans.toLowerCase().compareTo("n") == 0) || (ans.toLowerCase().compareTo("no") == 0) )
							{ System.out.println("\n"); break; }
					}					
					else
					{
						do {
				
							System.out.println("\nRooms available are:\n");
				
							for(IRoom R: rooms)
								System.out.println(R + "\n");

							System.out.print("\nPlease select by entering room number from above listing: ");
							rm = CLI.nextLine(); rm = rm.toUpperCase();
				
							for(IRoom R: rooms) // verify that user picked from available rooms
							{
								valid = rm.compareTo( R.getRoomNumber() ) == 0;
					
								if (valid)
									break;
							}
				
						} while(!valid);
				
						System.out.println( "\n" + HR.bookARoom(email, HR.getRoom(rm), cid, cod) + "\n");
						break; 
					}
				}
				// end outer else
			}
			// end of outermost while
		}
		// end of reserveRoom()

		public Customer createAccount(String email)
		{
			String lastName, firstName;
			
			while( HR.getCustomer(email) != null )
			{
				System.out.print("\nThere Is An Existing Account With The Email: <" + email + ">, ");
				System.out.println("You will need to use another email!!");
				email = getEmail();
			}

			System.out.print("\nPlease enter your last name: ");
			lastName = CLI.nextLine();

			System.out.print("Please enter your first name: ");
			firstName = CLI.nextLine();
			
			Customer C = HR.createACustomer(email, firstName, lastName);
				
			System.out.println("\n" +  C + "\n");
			
			return C;
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
			
			int pwHash = -2038048907; // masteryoda, nbtufszpeb, adoyretsam, bepzsfutbn
			
			System.out.print("Please enter the Admin password: ");
			
			pw = CLI.nextLine();
			
			if( pwHash == pw.hashCode() )
				{ System.out.println(); return true; }
			else
				{ System.out.println("\nIncorrect Password!!\n"); return false; }
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

				switch(choice)
				{
					case 1:
						displayAllCustomers();
					break;
				
					case 2:
						displayAllRooms();
					break;
				
					case 3:
						AR.displayAllReservations();
						//System.out.println("\n");
					break;
				
					case 4:
						addRooms();
					break;
				
					case 5:
						System.out.println(); // return to main menu
						return;
				
					default:
						System.out.println("\nInvalid Input!!\n");
				}
				// end switch
			}
			// end of while
		}
		// end of adminMenuManager()

		void displayAllRooms()
		{
			Collection<IRoom> rooms = AR.getAllRooms();
			
			if (rooms.size() == 0)
				System.out.println("\nThere Are No Rooms To Display\n");
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
			HashMap<String, IRoom> rooms = new HashMap<String, IRoom>();
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
					{
						if( HR.isRoomNumValid(roomNumber) )
						{
							roomNumber = roomNumber.toUpperCase();
							
							if( AR.roomExist(roomNumber) || rooms.containsKey(roomNumber) )
								System.out.println("\nThere is already a room: [" + roomNumber + "]\n");
							else
								break;
						}
						else
							System.out.println("\n[" + roomNumber + "] Is Not A Valid Room Number\n");
					}
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
					rooms.put( roomNumber, new FreeRoom(roomNumber, RT) );
				else
					rooms.put( roomNumber, new Room(roomNumber, price, RT) );
				
				System.out.println( "\n" + rooms.get(roomNumber) );

				do {
				
					System.out.println("\nWould you like to add another room?");
					System.out.print("Please enter, Y(y/yes/Yes/YES) or N(n/no/No/NO): ");
					ans = CLI.nextLine();

				} while( (ans.toLowerCase().compareTo("yes") != 0) && (ans.toLowerCase().compareTo("y") != 0) &&
						(ans.toLowerCase().compareTo("no") != 0) && (ans.toLowerCase().compareTo("n") != 0) );
				
				System.out.println();

			} while( (ans.toLowerCase().compareTo("yes") == 0) || (ans.toLowerCase().compareTo("y") == 0) );

			AR.addRooms( new ArrayList<IRoom>( rooms.values() ) );
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
				System.out.println("\nThere Are No Customers To Display\n");
			else
			{
				for(Customer C: customers)
					System.out.println("\n" + C + "\n");
			}
		}
	}
}
