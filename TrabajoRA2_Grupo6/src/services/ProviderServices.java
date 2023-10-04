package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Provider;

public class ProviderServices {

	static ResultSet resultSet = null;
	
	public static boolean insertProvider(Provider p) {
		
		String sql = "INSERT INTO Provider VALUES("+p.getId()+", \'"+p.getName()+"\', \'"+p.getDescription()+"\', \'"+p.getAddress()+"\', \'"+p.getPhone()+"\');";
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static ArrayList<Provider> selectProvider(String field, Object value){
		
		ArrayList<Provider> providers = new ArrayList<Provider>();
		String sql = "SELECT * FROM Provider";
		if(field == null) {
			sql += ";";
		}else if(field.equalsIgnoreCase("id")){
			sql += " WHERE(" + field + " = " + value +");";
		}else {
			sql += " WHERE(" + field + " = \'" + value +"\');";
		}
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				providers.add(new Provider(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return providers;
	}
	
	public static boolean updateProvider(Provider p) {
		
		String sql = "UPDATE Provider SET name = \'"+p.getName()+"\', description = \'"+p.getDescription()+"\', address = \'"+p.getAddress()+"\', phone = \'"+p.getPhone()+"\' WHERE id = "+p.getId();
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean deleteProvider(Provider p) {
		
		String sql = "DELETE FROM Provider WHERE( id = "+p.getId()+");";
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static int getNextId() {
		
		int id = 0;
		String sql = "SELECT MAX(id) FROM Provider;";
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			resultSet.next();
			id = resultSet.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id+1;
		
	}
	
	
	
}
