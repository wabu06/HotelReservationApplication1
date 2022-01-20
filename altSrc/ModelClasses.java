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

enum RoomType { SINGLE, DOUBLE } 

interface IRoom
{
	public String getRoomNumber();
	public Double getRoomPrice();
	public RoomType getRoomType();
	public boolean isFree();
}


class Room implements IRoom
{
	protected String roomNumber;
	protected Double price;
	protected RoomType type;
	
	public Room(String N, Double P, RoomType T)
	{
		roomNumber = N;
		price = P;
		type = T;
	}
	
	public String getRoomNumber() { return roomNumber; }
	public Double getRoomPrice() { return price; }
	public RoomType getRoomType() { return type; }
	
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


class FreeRoom extends Room
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


class Customer
{
	String firstName;
	String lastName;
	String email;
	
	public Customer(String F, String L, String E)
	{
		firstName = F;
		lastName = L;
		
		String emailRegex = "^(.+)@(.+).(.+)$";
		Pattern pattern = Pattern.compile(emailRegex);
		boolean match = pattern.matcher(E).matches();
		
		if (match)
			email = E;
		else
			throw new IllegalArgumentException("Invalid Email Format!!");
	}
	
	@Override 
	public String toString() { return "Customer Name: " + firstName + " " + lastName + "\nCustomer Email: " + email; }
}


class Reservation
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
	
	@Override 
	public String toString()
	{ 
		return customer.toString() + "\n" + room.toString() + "\nCheck In Date: " + checkInDate.toString() + "\nCheck Out Date: " + checkOutDate.toString();
	}
}
