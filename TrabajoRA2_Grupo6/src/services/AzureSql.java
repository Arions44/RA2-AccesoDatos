package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AzureSql {
	private static String cnnString =
			"jdbc:sqlserver://sql-server-ra2.database.windows.net;"
			+"database=RA2_Grupo6;"
			+"user=Grupo6;"
			+"password=AccesoDatos@;"
			+"encrypt=true;"
			+"trustServerCertificate=false;"
			+"loginTimeout=30;";
	
	public static String getCnnString() {
		return cnnString;
	}
	
	public static Connection createConnection() {
		try {
			return DriverManager.getConnection(AzureSql.getCnnString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
