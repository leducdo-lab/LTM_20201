package Manager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

/**
 * Server Application -> Main
 * @author Keerthikan
 */
public class ServerMain {
	
	public static void ConnectDB() throws ClassNotFoundException, SQLException{
		String hostName = "localhost";
	     String sqlInstanceName = "SQLEXPRESS";
	     String database = "Checker";
	     String userName = "SA";
	     String password = "Hongthao123";
	     String connectionURL = "jdbc:sqlserver://" + hostName + ":1433"
	             + ";instance=" + sqlInstanceName + ";databaseName=" + database;
	 
	     Connection conn = DriverManager.getConnection(connectionURL, userName,
	             password);
	     Statement statement = conn.createStatement();
	     ResultSet rSet = statement.executeQuery("SELECT * FROM Users");
	     while(rSet.next()) {
	    	 System.out.println(rSet.getString("Name") + " " + rSet.getString("Pass") + " " + rSet.getString("Score") );
	     }
	     System.out.println("Connect ok");
	}

	public static void main(String[] args) {
		
		try {
			ConnectDB();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServerApp server = new ServerApp();
		server.setSize(400,250);
		server.setVisible(true);
		server.setTitle("Checkers Server");
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//start Connection
		server.startRunning();
	}
}
