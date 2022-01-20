package model;

/**
 * @title IRoom Interface
 *
 *
 * @author W. K.  Burke
 *
 */


//enum RoomType { SINGLE, DOUBLE }

public interface IRoom
{
	public String getRoomNumber();
	public Double getRoomPrice();
	public RoomType getRoomType();
	public boolean isFree();
}
