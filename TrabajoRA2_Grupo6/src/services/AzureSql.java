package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AzureSql {
	
	private static Connection cnn = null;
	private static String userName = "Grupo6";
	private static String passW = "AccesoDatos@";
	private static String cnnString = 
			"jdbc:sqlserver://sql-server-ra2.database.windows.net;"
			+ "database=RA2_Grupo6;"
			+ "user="+ userName+";"
			+"password="+passW+";"
			+"encrypt=true;"
			+"trustServerCertificate=false;"
			+"loginTimeout=30;";
	
	public static Connection ConnectingDB() {
		
		try(Connection cnn = DriverManager.getConnection(cnnString)){
			System.out.println("Connected");
			return cnn;
		}	
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Connected failed");
			return null;
		}
		
		
	}
}
