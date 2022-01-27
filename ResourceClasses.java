package api;

/**
 * @title Resource Classes
 *
 *
 * @author W. K.  Burke
 *
 */


import java.util.*;
import java.util.regex.*;
import model.ModelClasses.*;
import service.ServiceClasses.*;


public class ResourceClasses
{
	public static class HotelResource
	{
		CustomerService CS;
		ReservationService RS;
		
		final static HotelResource hrInstance = new HotelResource();
		
		public static HotelResource getInstance() { return hrInstance; }
		
		private HotelResource()
		{
			CS = CustomerService.getInstance();
			RS = ReservationService.getInstance();
		}
		
		public boolean isEmailValid(String email)
		{ 
			String emailRegex = "^(.+)@(.+).(.+)$";
			Pattern pattern = Pattern.compile(emailRegex);
			//boolean match = pattern.matcher(E).matches();
			
			return pattern.matcher(email).matches();
		}
		
		boolean isLeapYear(int year)
		{
			if( (year % 4) == 0 )
			{
			 	if( (year % 100) == 0 )
				{
					if( (year % 400) == 0 )
						return true;
					else
						return false;
				}
				else
					return true;
			}
			else
				return false;
		}
		
			// create a date instance with user entered data
		public Date getDateInstance(String date, boolean checkInDate) 
		{ 
			// first check to see if date was entered in the correct format, if not throw an exception
			
			String dateRegX = "^(\\d{2})/(\\d{2})/(\\d{4})$";
			Pattern pattern = Pattern.compile(dateRegX);
			boolean match = pattern.matcher(date).matches();
			
			if (match)
			{
				String[] MDY = date.split("/");
				
				int month = Integer.parseInt( MDY[0] ) - 1;
				
				if( (month < 0) || (month > 11) )
					throw new IllegalArgumentException("Month Out Of Range!!");
				
				int day = Integer.parseInt( MDY[1] );
				
				int year = Integer.parseInt( MDY[2] );
				
					// check for leap year, if so then february can have at most 29 days
				if (month == 1)
				{
					if( isLeapYear(year) )
					{
						if( (day < 1) || (day > 29) )
							throw new IllegalArgumentException("Date Out Of Range!!");
					}
					else
					{
						if( (day < 1) || (day > 28) )
							throw new IllegalArgumentException("Date Out Of Range!!");
					}
				}
				else
				{
					if( (month == 8) || (month == 3) || (month == 5) || (month == 10) )
					{
						if( (day < 1) || (day > 30) )
							throw new IllegalArgumentException("Date Out Of Range!!");
					}
					else
					{
						if( (day < 1) || (day > 31) )
							throw new IllegalArgumentException("Date Out Of Range!!");
					}
				}
				
				int hour;
				
				if (checkInDate)
					hour = 15;
				else
					hour = 11;
			
				Calendar cal = Calendar.getInstance();
			
				cal.clear(); cal.set(year, month, day, hour, 00);
			
				Date D = cal.getTime();
				
				if( D.after( Calendar.getInstance().getTime() ) )
					return D;
				else 
					throw new IllegalArgumentException("Check In/Out Dates, Cannot Precede Current Date");
			}
			else
				throw new IllegalArgumentException("Invalid Date Format!!");
		}
		// end getDateInstance
		
		public String getCustomerEmail(Customer C) { return C.getEmail(); }
		
		public Customer getCustomer(String email) { return CS.getCustomer(email); }
		
		public Customer createACustomer(String  email,  String firstName, String lastName)
			{ return CS.addCustomer(email, firstName, lastName); }
		
		public IRoom getRoom(String roomNumber) { return RS.getARoom(roomNumber); }
		
		public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate)
			{ return RS.reserveARoom( CS.getCustomer(customerEmail), room, checkInDate, CheckOutDate ); }
		
		public Collection<Reservation> getCustomerReservations(String customerEmail)
			{ return RS.getCustomersReservation( CS.getCustomer(customerEmail) ); }
		
		public Collection<IRoom> findARoom(Date checkIn, Date checkOut)
			{ return RS.findRooms(checkIn, checkOut); }
	}
	
	public static class AdminResource
	{
		CustomerService CS;
		ReservationService RS;
		
		final static AdminResource arInstance = new AdminResource();
		
		public static AdminResource getInstance() { return arInstance; }
		
		private AdminResource()
		{
			CS = CustomerService.getInstance();
			RS = ReservationService.getInstance();
		}
		
		public Customer getCustomer(String email) { return CS.getCustomer(email); }
		
		public void addRooms(List<IRoom> rooms)
		{
			for(IRoom rm: rooms)
				RS.addRoom(rm);
		}
		
		public Collection<IRoom> getAllRooms() { return RS.getRooms(); }
		
		public Collection<Customer> getAllCustomers() { return CS.getAllCustomers(); }
		
		public IRoom getRoom(String roomId) { return RS.getARoom(roomId); }
		
		public void displayAllReservations() { RS.printAllReservations(); }
	}
}
