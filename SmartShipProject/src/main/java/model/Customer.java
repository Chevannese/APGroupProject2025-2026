package model;


import jakarta.persistence.*;



@Entity
@DiscriminatorValue("Customer")

public class Customer extends User
{

	public Customer()
	{
		super();
	}
	
	public Customer(String trn, String firstName, String lastName, String password, String contactNum, String email)
	{
		super(trn, firstName, lastName, password, contactNum, email);
	}
	
	public Customer(Customer cust)
	{
		super(cust);
	}
	
	
	
	
}
