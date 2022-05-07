package model;

/**
 * @title Model Classes
 *
 *
 * @author W. K.  Burke
 *
 */


import java.util.*;
import java.util.regex.*;

public class ModelClasses
{
	public enum RoomType { SINGLE, DOUBLE } 

	public static interface IRoom
	{
		public String getRoomNumber();
		public Double getRoomPrice();
		public RoomType getRoomType();
		public boolean isFree();
		
		public ArrayList<Reservation> getReservations();
		public void addReservation(Reservation R);
		public boolean hasReservations();
		public int totalReservations();
	}


	public static class Room implements IRoom
	{
		/* protected */ final String roomNumber;
		/* protected */ Double price;
		/* protected */ final RoomType type;
		
		ArrayList<Reservation> RoomReservations; // Reservations for the room
	
		public Room(String N, Double P, RoomType T)
		{
			roomNumber = N;
			price = P;
			type = T;
			
			RoomReservations = new ArrayList<Reservation>();
		}
	
		public String getRoomNumber() { return roomNumber; }
		public Double getRoomPrice() { return price; }
		public RoomType getRoomType() { return type; }
		
		public ArrayList<Reservation> getReservations() { return RoomReservations; }
		public void addReservation(Reservation R) { RoomReservations.add(R); }
		public boolean hasReservations() { return !RoomReservations.isEmpty(); }
		public int totalReservations() { return RoomReservations.size(); }
	
		public boolean isFree()
		{
			if (price == 0.0)
				return true;
			else
				return false;
		}
	
		@Override 
		public String toString()
		{
			String RmStr = "Room Number: " + roomNumber + "\nPrice: $" + price + "\nRoom Type: ";
		
			if (type ==  RoomType.SINGLE)
				RmStr += "SINGLE";
			else
				RmStr += "DOUBLE";
		
			return RmStr;
		}
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof Room) )
				return false;
		
			Room R = (Room) O;
			
			return roomNumber.equals(R.roomNumber) && price.equals(R.price) && (type == R.type) && RoomReservations.equals(R.RoomReservations);
		}
		
		@Override
		public int hashCode()
			{ return Objects.hash(roomNumber, price, type, RoomReservations); }
	}
	// end Room Class

	public static class FreeRoom extends Room
	{
		public FreeRoom(String N, RoomType T) { super(N, 0.0, T); }
	
		@Override 
		public String toString()
		{
			String RmStr = "Room Number: " + super.roomNumber + "\nPrice: $" + super.price + "\nRoom Type: ";
		
			if (super.type ==  RoomType.SINGLE)
				RmStr += "SINGLE";
			else
				RmStr += "DOUBLE";
		
			return RmStr;
		}
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof FreeRoom) )
				return false;
		
			FreeRoom R = (FreeRoom) O;
			
			return roomNumber.equals(R.roomNumber) && price.equals(R.price) && (type == R.type) && RoomReservations.equals(R.RoomReservations);
		}
		
		@Override
		public int hashCode()
			{ return Objects.hash(roomNumber, price, type, RoomReservations); }
	}
	// end FreeRoom Class

	public static class Customer
	{
		private String firstName;
		private String lastName;
		private String email;
	
		public Customer(String F, String L, String E)
		{
			String eRegX = "^(\\w+)@(\\w+)\\.(\\w+)$";
			Pattern pattern = Pattern.compile(eRegX);
			boolean match = pattern.matcher(E).matches();
		
			if (!match)
			{
				eRegX = "^(\\w+)@(\\w+)\\.(\\w+)\\.(\\w+)$";
				pattern = Pattern.compile(eRegX);
				match = pattern.matcher(E).matches();
				
				if(match)
				{
					email = E;
					firstName = F;
					lastName = L;
				}
				else
					throw new IllegalArgumentException("Invalid Email Format!!");
			}
			else
			{
				email = E;
				firstName = F;
				lastName = L;
			}
		}
		
		public String getEmail() { return email; }
	
		@Override 
		public String toString() { return "Customer Name: " + firstName + " " + lastName + "\nCustomer Email: " + email; }
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof Customer) )
				return false;
		
			Customer C = (Customer) O;
			
			return firstName.equals(C.firstName) && lastName.equals(C.lastName) &&  email.equals(C.email);
		}
		
		@Override
		public int hashCode()
			{ return Objects.hash(firstName, lastName, email); }
	}
	// end of Customer Class

	public static class Reservation
	{
		private Customer customer;
		private IRoom room;
		private Date checkInDate;
		private Date checkOutDate;
	
		public Reservation(Customer C, IRoom R, Date cid, Date cod)
		{
			customer = C;
			room = R;
			checkInDate = cid;
			checkOutDate = cod;
		}
		
		public Date getCheckInDate() { return checkInDate; }
		public Date getCheckOutDate() { return checkOutDate; }
	
		@Override 
		public String toString()
		{ 
			String resStr = customer.toString() + "\n" + room.toString() + "\nCheck In Date: ";
			return resStr + checkInDate.toString() + "\nCheck Out Date: " + checkOutDate.toString();
		}
		
		@Override 
		public boolean equals(Object O)
		{
			if (O == this)
				return true;
			
			if( !(O instanceof Reservation) )
				return false;
		
			Reservation R = (Reservation) O;
			
			return customer.equals(R.customer) && room.equals(R.room) && checkInDate.equals(R.checkInDate) && checkOutDate.equals(R.checkOutDate);
		}
		
		@Override
		public int hashCode()
			{ return Objects.hash(customer, room, checkInDate, checkOutDate); }
	}
	// end of Reservation Class
}
