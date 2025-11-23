package model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Manager")


public class Manager extends User
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Manager()
	{
		super();
	}
	
	public Manager(String trn, String firstName, String lastName, String password, String contactNum, String email)
	{
		super(trn,firstName, lastName, password, contactNum, email);
	}
	
	public Manager(Manager man)
	{
		super(man);
	}
	
	
}
