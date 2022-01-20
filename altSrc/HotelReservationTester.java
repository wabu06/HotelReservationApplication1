package model;

/**
 * @title Hotel Reservation Tester
 *
 *
 * @author W. K.  Burke
 *
 */

import java.util.*;
//import model.*;

//enum RoomType { SINGLE, DOUBLE }

public class HotelReservationTester
{
	public static void main(String[] args)
	{
		Customer customer = new Customer("Micheal", "Burnam", "mburnam@discovery.mil");
		System.out.println(customer);
		
		try
			{ customer = new Customer("Micheal", "Burnam", "@discovery.mil"); }

		catch (IllegalArgumentException ex) 
			{ System.out.println( "\n" + ex.getLocalizedMessage() ); }
		
		IRoom room = new FreeRoom("269", RoomType.DOUBLE);
	}
}
