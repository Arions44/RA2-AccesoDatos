package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AzureSql {
	
	public static String cnnString =
			"jdbc:sqlserver://sql-server-ra2.database.windows.net;"
			+"database=RA2_Grupo6;"
			+"user=Grupo6;"
			+"password=AccesoDatos@;"
			+"encrypt=true;"
			+"trustServerCertificate=false;"
			+"loginTimeout=30;";
	
	/*public static Connection ConnectingDB() {
		
		try(Connection cnn = DriverManager.getConnection(cnnString)){
			System.out.println("Connected");
			return cnn;
		}	
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Connected failed");
			return null;
		}
	}*/
	public static void SelectProducts() {
		ResultSet resultSet = null;
		String sql = "SELECT * FROM Product;";
		try {
			Connection conn = DriverManager.getConnection(cnnString);
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1)+ ", "+resultSet.getString(2).trim()+ ", "+resultSet.getString(3).trim()+ ", "+ resultSet.getInt(4)+ ", "+resultSet.getString(5).trim()+ ", "+resultSet.getString(6).trim()+ ", "+resultSet.getInt(7)+ ", "+resultSet.getInt(8));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
