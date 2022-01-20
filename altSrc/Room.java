package model;

/**
 * @title Room Class
 *
 *
 * @author W. K.  Burke
 *
 */


public class Room implements IRoom
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
