package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Main 
{
	public static void main(String[] args)
	{
		String url = "jdbc:mysql://localhost:3306/";
		Connection myConn = null;
		
		try
		{
			myConn = DriverManager.getConnection(url, "root", "");
			if(myConn != null)
			{
				JOptionPane.showMessageDialog(null, "Connected to Local Server\n" + "JDBC Connection Status\n" + myConn);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
    
}
