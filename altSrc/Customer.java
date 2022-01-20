package model;

/**
 * @title Customer Classes
 *
 *
 * @author W. K.  Burke
 *
 */

import java.util.regex.*;


public class Customer
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
