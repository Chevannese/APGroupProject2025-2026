package model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Driver")

public class Driver extends User 
{
	public Driver()
	{
		super();
	}
	
	public Driver(String trn, String firstName, String lastName, String password, String contactNum, String email)
	{
		super(trn, firstName, lastName, password,contactNum, email);
	}
	
	public Driver(Driver driver)
	{
		super(driver);
	}
	
	
}
