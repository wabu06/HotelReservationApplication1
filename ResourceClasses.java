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
		
			// create a date instance with user entered data
		public Date getDateInstance(String date) 
		{ 
			// first check to see if date was entered in the correct format, if not perhaps
			// throw an exception
			
			return null;
		}
		
		public int verifyCheckInOutDates(Date checkInDate, Date CheckOutDate)
		{ 
			// return -1 if checkInDate preceeds CheckOutDate, which is the valid case
			// return 0 if checkInDate = CheckOutDate
			// return 1 if checkInDate comes after CheckOutDate

			return -1;
		}
		
		public Customer getCustomer(String email) { return CS.getCustomer(email); }
		
		public void createACustomer(String  email,  String firstName, String lastName)
			{ CS.addCustomer(email, firstName, lastName); }
		
		public IRoom getRoom(String roomNumber) { return RS.getARoom(roomNumber); }
		
		public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date CheckOutDate)
			{ return RS.reserveARoom( CS.getCustomer(customerEmail), room, checkInDate, CheckOutDate ); }
		
		public Collection<Reservation> getCustomersReservations(String customerEmail)
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
		
		public void displayAllReservations() { RS.printAllReservations(); }
	}
}
