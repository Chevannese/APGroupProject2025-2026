package model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Clerk")
public class Clerk extends User
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Clerk()
	{
		super();
	}
	
	public Clerk(String trn, String firstName, String lastName, String password, String contactNum, String email)
	{
		super(trn, firstName, lastName, password, contactNum, email);
	}
	
	public Clerk(Clerk clerk)
	{
		super(clerk);
	}
	
	
}
