package model;

public class Clerk extends User
{
	public Clerk()
	{
		super();
	}
	
	public Clerk(String trn, String firstName, String lastName, String password, String contactNum, String email, String userType)
	{
		super(trn, firstName, lastName, password, contactNum, email, userType);
	}
	
	public Clerk(Clerk clerk)
	{
		super(clerk);
	}
	
	
}
