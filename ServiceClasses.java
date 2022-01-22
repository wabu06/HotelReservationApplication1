package service;

/**
 * @title Service Classes
 *
 *
 * @author W. K.  Burke
 *
 */


import java.util.*;
import model.ModelClasses.*;


public class ServiceClasses
{
	 public static class CustomerService 
	 {
	 	Map<String, Customer> CustomerMap;
		
		final static CustomerService instance = new CustomerService();
		
		public static CustomerService getInstance() { return instance; }
		
		private CustomerService() { CustomerMap = new HashMap<String, Customer>(); } 
		
	 	public void addCustomer(String email, String firstName, String lastName)
			{ CustomerMap.put( email, new Customer(firstName, lastName, email) ); }
		
		public Customer getCustomer(String customerEmail)
			{ return CustomerMap.get(customerEmail); }
		
		public Collection<Customer> getAllCustomers()
		{
			ArrayList<Customer> customers = new ArrayList<Customer>();
			
			for(Customer C: CustomerMap.values() ) { customers.add(C); }
			
			return customers;
		}
	 }
	 
	 public static class ReservationService
	 {
	 	Map<String, IRoom> RoomMap;
		Map< String, ArrayList<Reservation> > ReservationMap;
		
		final static ReservationService instance = new ReservationService();
		
		public static ReservationService getInstance() { return instance; }
		
		private ReservationService()
		{ 
			RoomMap = new HashMap<String, IRoom>();
			ReservationMap = new HashMap< String, ArrayList<Reservation> >();
		}
		
		public void addRoom(IRoom room) { RoomMap.put(room.getRoomNumber(), room); }
		
		public IRoom getARoom(String roomId) { return RoomMap.get(roomId); }
		
		public Collection<IRoom> getRooms()
		{
			ArrayList<IRoom> rooms = new ArrayList<IRoom>();
			
			for( IRoom rm: RoomMap.values() )
				rooms.add(rm);
			
			return rooms;
		}
		
		public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
		{
			// first check to see if room is available for the given dates
			
			Reservation R = new Reservation(customer, room, checkInDate, checkOutDate);
			
			RoomMap.get( room.getRoomNumber() ).addReservation(R);
			
			String email = customer.getEmail();
			
			if( ReservationMap.containsKey(email) )
				ReservationMap.get(email).add(R);
			else
				ReservationMap.put( email, new ArrayList<Reservation>() ).add(R);

			return R;
		}
		
		public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate)
		{ 
			ArrayList<IRoom> rooms = new ArrayList<IRoom>();
			
			Date cid, cod;
			
			boolean vacant;
			
			int i; // amount of reservations checked
			
			for(IRoom rm: RoomMap.values() )
			{
				if ( rm.hasReservations() )
				{
					i = 0;
					
					for( Reservation R: rm.getReservations() )
					{
						cid = R.getCheckInDate();
						cod = R.getCheckOutDate();
						
						vacant = (checkOutDate.compareTo(cid) <= 0) || (checkInDate.compareTo(cod) >= 0);
						
						if( !vacant  )
							break;
						
						i++;
					}
					
					if( i == rm.totalReservations() )
						rooms.add(rm);
				}
			}
			
			return rooms;
		}
		
		public Collection<Reservation> getCustomersReservation(Customer customer)
			{ return ReservationMap.get( customer.getEmail() ); }
		
		public void printAllReservations()
		{
			if (ReservationMap.size() == 0)
				System.out.print("\nThere No Reservations To Display");
			else
			{
				for(ArrayList<Reservation> RA: ReservationMap.values() )
				{
					for(Reservation R: RA)
						System.out.println("\n" + R + "\n");
				}
			}
		}
	 }
}
