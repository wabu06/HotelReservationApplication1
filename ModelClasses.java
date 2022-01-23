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
		protected String roomNumber;
		protected Double price;
		protected RoomType type;
		
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
		public boolean hasReservations() { return RoomReservations.isEmpty(); }
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
	}


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
	}


	public static class Customer
	{
		String firstName;
		String lastName;
		String email;
	
		public Customer(String F, String L, String E)
		{
			String emailRegex = "^(.+)@(.+).(.+)$";
			Pattern pattern = Pattern.compile(emailRegex);
			boolean match = pattern.matcher(E).matches();
		
			if (match)
			{
				email = E;
				firstName = F;
				lastName = L;
			}
			else
				throw new IllegalArgumentException("Invalid Email Format!!");
		}
		
		public String getEmail() { return email; }
	
		@Override 
		public String toString() { return "Customer Name: " + firstName + " " + lastName + "\nCustomer Email: " + email; }
	}


	public static class Reservation
	{
		Customer customer;
		IRoom room;
		Date checkInDate;
		Date checkOutDate;
	
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
			String resStr = "Reservation For:\n" + customer.toString() + "\n" + room.toString() + "\nCheck In Date: ";
			return resStr + checkInDate.toString() + "\nCheck Out Date: " + checkOutDate.toString();
		}
	}
}
