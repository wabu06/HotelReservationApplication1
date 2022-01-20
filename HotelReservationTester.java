//package model;
//package service;

/**
 * @title Hotel Reservation Tester
 *
 *
 * @author W. K.  Burke
 *
 */

import java.util.*;
//import model.ModelClasses.Customer;
// import model.ModelClasses.FreeRoom;
// import model.ModelClasses.RoomType;
// import model.ModelClasses.IRoom;
import model.ModelClasses.*;
import service.ServiceClasses.*;

//enum RoomType { SINGLE, DOUBLE }

public class HotelReservationTester
{
	public static void main(String[] args)
	{
		Customer customer = new Customer("Micheal", "Burnam", "mburnam@discovery.mil");
		System.out.println(customer);
		
		try
			{ Customer customer1 = new Customer("Micheal", "Burnam", "@discovery.mil"); }

		catch (IllegalArgumentException ex) 
			{ System.out.println( "\n" + ex.getLocalizedMessage() ); }
		
		IRoom room = new FreeRoom("269", RoomType.DOUBLE);
		
		System.out.println( "\n" + room );
		
		Calendar calendar = Calendar.getInstance();
		
		Date cit = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_MONTH, 7); Date cot = calendar.getTime();
		
		Reservation res = new Reservation(customer, room, cit, cot);
		
		System.out.println("\n" + res);
		
		CustomerService CS = CustomerService.getInstance();
		
		CS.addCustomer("007@MI6.mil.uk", "James", "Bond");
		
		System.out.println( "\n" + CS.getCustomer("007@MI6.mil.uk") );
		
		ReservationService RS = ReservationService.getInstance();
	}
}
