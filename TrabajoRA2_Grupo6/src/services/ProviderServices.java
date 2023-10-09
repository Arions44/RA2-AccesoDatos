package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.Provider;

public class ProviderServices {

	static ResultSet resultSet = null;
	static Connection conn = AzureSql.createConnection();
	
	
	public static boolean insertProvider(Provider p) { //Insert into table by a given provider
		
		String sql = "INSERT INTO Provider VALUES("+p.getId()+", \'"+p.getName()+"\', \'"+p.getDescription()+"\', \'"+p.getAddress()+"\', \'"+p.getPhone()+"\', "+p.getActive()+");";
		try {
			PreparedStatement statement = conn.prepareStatement(sql); //Prepare sql query
			statement.execute(); //Execution of the prepared query
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("a");
			return false;
		}
		
	}
	
	public static ArrayList<Provider> selectProvider(String field, Object value){ //Return provider by a given field and value when filtered or return every provider when parameters are null
		
		ArrayList<Provider> providers = new ArrayList<>();
		String sql = "SELECT * FROM Provider";
		if(field == null) {
			sql += ";";
		}else if(field.equalsIgnoreCase("id")){
			sql += " WHERE( id  = " + value +");";
		}else {
			sql += " WHERE(" + field.toLowerCase() + " = \'" + value +"\');";
		}
		try {
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				providers.add(new Provider(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return providers;
	}
	
	public static Map<Integer, String> selectProviderName(String field, int id){ //Return provider by a given field and value when filtered or return every provider when parameters are null
		
		Map<Integer, String> providerIdName = new HashMap<Integer,String>();
		String sql = "SELECT id, name, active FROM Provider";
		if(field == null) {
			sql += ";";
		}else if(field.equalsIgnoreCase("id")){
			sql += " WHERE(" + field + " = " + id +");";
		}
		try {
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				if(resultSet.getInt(3)==1)
					providerIdName.put(resultSet.getInt(1), resultSet.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return providerIdName;
	}
	
	public static boolean updateProvider(int id, String name,String description,String address,String phone) { //Update provider
		
		String sql = "UPDATE Provider SET name = \'"+name+"\', description = \'"+description+"\', address = \'"+address+"\', phone = \'"+phone+"\' WHERE id = "+id;
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean deleteProvider(int id) { //Delete provider by id
		
		String sql = "UPDATE Provider SET active = 0 WHERE( id = "+id+");";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static int getNextId() { //Return the next id to use for next team
		
		int id = 0;
		String sql = "SELECT MAX(id) FROM Provider;";
		try {
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			resultSet.next();
			id = resultSet.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id+1;
		
	}
	
	
	
}
