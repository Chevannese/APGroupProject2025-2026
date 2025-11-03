package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.cj.jdbc.exceptions.SQLError;

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
	
	public User get(String input, String passCode)
	{
		User u = null;
		System.out.println("trying <-result");
		   try {
			   String sql="select * from  user where trn ='"+input+"' and password = '"+hashString(passCode)+ "'";
			   Statement stat=conn.createStatement();
			   ResultSet  result=stat.executeQuery(sql);
			   if(result.next()) 
			   {
				   u = new User();
				   u.setTrn(result.getString("trn"));
				   u.setFirstName(result.getString("firstName"));
				   u.setLastName(result.getString("lastName"));
				   u.setContactNum(result.getString("contactNum"));
				   u.setEmail(result.getString("email"));
				   u.setPassword(result.getString("password"));
				   u.setUserType(result.getString("userType"));
				   System.out.println(u);}
			   
		   }catch(Exception e)
		   {
			   e.printStackTrace();
		   }
		
		return u;
		
	}
	
	 private String hashString(String in) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(in.getBytes(StandardCharsets.UTF_8));
				StringBuilder hexString = new StringBuilder();
				for (byte b : hash) {
					String hex = Integer.toHexString(0xff & b);
					if (hex.length() == 1) hexString.append('0');
					hexString.append(hex);
				}
				return hexString.toString();
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
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
