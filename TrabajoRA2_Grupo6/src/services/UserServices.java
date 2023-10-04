package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.User;

public class UserServices {

	static ResultSet resultSet = null;
	
	public static boolean insertUser(User u) {
		
		String sql = "INSERT INTO Username VALUES("+u.getId()+", \'"+u.getName()+"\', \'"+u.getPassword()+"\');";
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static ArrayList<String> selectUsername(){
		
		ArrayList<String> usernames = new ArrayList<String>();
		String sql = "SELECT name FROM Username;";
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				usernames.add(resultSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usernames;
	}
	
	public static ArrayList<User> selectUser(){
		
		ArrayList<User> usernames = new ArrayList<User>();
		String sql = "SELECT name, password FROM Username;";
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				usernames.add(new User(0,resultSet.getString(1),resultSet.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usernames;
	}
	
	public static int getNextId() {
		
		int id = 0;
		String sql = "SELECT MAX(id) FROM Username;";
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
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
