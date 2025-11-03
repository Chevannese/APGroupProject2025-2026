package model;

import java.sql.Connection;
import java.sql.Statement;

public class User 
{
	protected String trn;
	protected String firstName;
	protected String lastName;
	protected String password;
	protected String contactNum;
	protected String email;
	protected String userType;
	private static final Connection conn = Database.getDatabaseConnection();
	
	public User()
	{
		trn = "";
		firstName = "";
		lastName = "";
		password = "";
		contactNum = "";
		email = "";
		userType = "";
	}

	public User(String trn, String firstName, String lastName, String password, String contactNum, String email, String userType) {
		
		this.trn = trn;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.contactNum = contactNum;
		this.email = email;
		this.userType = userType;
	}

	public User(User user) 
	{
		this.trn = user.trn;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.password = user.password;
		this.contactNum = user.contactNum;
		this.email = user.email;
		this.userType = user.userType;
	}
	
	public void createAccount() {
		   System.out.println("trying <-result");
		   try {
		   String sql="INSERT INTO user VALUES ('"+trn+"','"+firstName+"', '"+lastName+"', '"+password+"','"+contactNum+"','"+email+"', '"+userType+"')";
		   Statement stat=conn.createStatement();
		   int  result=stat.executeUpdate(sql);
		   System.out.println(result +"<-result");
		   }catch (Exception e) {e.printStackTrace();}
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


	
	
	
}
