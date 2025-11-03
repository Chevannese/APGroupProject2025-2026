package model;

public class Manager extends User
{
	public Manager()
	{
		super();
	}
	
	public Manager(String trn, String firstName, String lastName, String password, String contactNum, String email, String userType)
	{
		super(trn,firstName, lastName, password, contactNum, email, userType);
	}
	
	public Manager(Manager man)
	{
		super(man);
	}
	
	
}
