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
		
	 	public Customer addCustomer(String email, String firstName, String lastName)
		{
			
			Customer C = new Customer(firstName, lastName, email);
			CustomerMap.put(email, C);
			return C;
		}
		
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
		
		public boolean roomExist(String roomId) { return RoomMap.containsKey(roomId); }
		
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
			
			//RoomMap.get( room.getRoomNumber() ).addReservation(R);
			
			room.addReservation(R);
			
			String email = customer.getEmail();
			
			if( ReservationMap.containsKey(email) )
				{ ReservationMap.get(email).add(R); }
			else
			{
				ReservationMap.put( email, new ArrayList<Reservation>() );
				ReservationMap.get(email).add(R);
			}

			return R;
		}
		
		public HashMap<String, IRoom> findRooms(Date checkInDate, Date checkOutDate)
		{ 
			HashMap<String, IRoom> rooms = new HashMap<String, IRoom>();
			
			Date cid, cod;
			
			boolean vacant = false;
			
			for(IRoom rm: RoomMap.values() )
			{
				if ( rm.hasReservations() )
				{
					for( Reservation R: rm.getReservations() )
					{
						cid = R.getCheckInDate();
						cod = R.getCheckOutDate();
						
						vacant = checkOutDate.before(cid) || checkInDate.after(cod);
						
						if( !vacant  )
							break;
					}
					
					if(vacant)
						rooms.put( rm.getRoomNumber(), rm );
				}
				else
					rooms.put( rm.getRoomNumber(), rm );
			}
			
			return rooms;
		}
		
		public Collection<Reservation> getCustomersReservation(Customer customer)
			{ return ReservationMap.get( customer.getEmail() ); }
		
		Collection<Reservation> getRoomReservations(IRoom room)
			{ return room.getReservations(); }
		
		public  void printAllReservations()
		{
			if (ReservationMap.size() == 0)
				System.out.println("\nThere Are No Reservations To Display\n");
			else
			{
				for(ArrayList<Reservation> RA: ReservationMap.values() )
				{
					for(Reservation R: RA)
						System.out.println("\n" + R + "\n");
				}
			}
			
			System.out.println();
		}
	 }
}
