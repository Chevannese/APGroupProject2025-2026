package model;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class Database 
{
private static Connection conn=null;
	
	public static Connection getDatabaseConnection() {
		if(conn==null) {
			String url="jdbc:mysql://SmartShip_tubeablein:6b5e0a8b874847dbb9f33d20b1789a7b0b0e6c7c@zabwas.h.filess.io:61002/SmartShip_tubeablein";
			try {
				conn=DriverManager.getConnection(url);
				if(conn !=null) {
					//JOptionPane.showMessageDialog(null, "Connected to Local Server","JDBC Connection Status",JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Connected to database");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return conn;
	}
	
	
    
}
