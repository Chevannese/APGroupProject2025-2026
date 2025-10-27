package model;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection 
{
private static Connection conn=null;
	
	public static Connection getDatabaseConnection() {
		if(conn==null) {
			String url="jdbc:mysql://localhost:3306/smartshipdatabase";			
			try {
				conn=DriverManager.getConnection(url, "root", "");
				if(conn !=null) {
					JOptionPane.showMessageDialog(null, "Connected to Local Server","JDBC Connection Status",JOptionPane.INFORMATION_MESSAGE);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return conn;
	}
	
    
}
