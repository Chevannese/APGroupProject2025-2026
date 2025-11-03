package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Customer extends User
{
	private static final Connection conn = Database.getDatabaseConnection();

	public Customer()
	{
		super();
	}
	
	public Customer(String trn, String firstName, String lastName, String password, String contactNum, String email, String userType)
	{
		super(trn, firstName, lastName, password, contactNum, email, userType);
	}
	
	public Customer(Customer cust)
	{
		super(cust);
	}
	
	public void createAccount() {
		   System.out.println("trying <-result");
		   try {
		   String sql1="INSERT INTO user VALUES ('"+trn+"','"+firstName+"', '"+lastName+"', '"+password+"','"+contactNum+"','"+email+"', '"+userType+"');";
		   String sql2 = "INSERT INTO customer VALUES ('"+trn+"', CURRENT_DATE)";
		   Statement stat=conn.createStatement();
		   int result1 =stat.executeUpdate(sql1);
		   int result2 = stat.executeUpdate(sql2);
		   
		   System.out.println(result1 +"<-result");
		   System.out.println(result2 +"<-result");
		   }
		   
		   catch(SQLIntegrityConstraintViolationException s)
		   {
			   JOptionPane.showMessageDialog(null, "Whoops! Sorry the trn you entered exists.\nPlease check if you have registered already");
			   s.printStackTrace();
		   }catch (SQLException e) 
		   	{
			   e.printStackTrace();
			 }
	   }
	
	//Track Package
	//Manage Bills
	//View Account details
	
	
	
}
