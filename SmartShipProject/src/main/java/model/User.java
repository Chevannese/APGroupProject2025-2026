package model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class User 
{
	@Id
	@Column(name = "trn")
	
	protected String trn;
	protected String firstName;
	protected String lastName;
	protected String password;
	protected String contactNum;
	protected String email;
	
	public User()
	{
		trn = "";
		firstName = "";
		lastName = "";
		password = "";
		contactNum = "";
		email = "";
	}

	public User(String trn, String firstName, String lastName, String password, String contactNum, String email) {
		
		this.trn = trn;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.contactNum = contactNum;
		this.email = email;
	}

	public User(User user) 
	{
		this.trn = user.trn;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.password = user.password;
		this.contactNum = user.contactNum;
		this.email = user.email;
	}

	public String getTrn() {
		return trn;
	}

	public void setTrn(String trn) {
		this.trn = trn;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	
	
	
}
