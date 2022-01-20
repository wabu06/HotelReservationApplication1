package model;

/**
 * @title FreeRoom Class
 *
 *
 * @author W. K.  Burke
 *
 */


public class FreeRoom extends Room
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
