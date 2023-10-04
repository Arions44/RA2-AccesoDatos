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
	
	public static boolean insertUser(User u) { //Insert into table by a given user
		
		String sql = "INSERT INTO Username VALUES("+u.getId()+", \'"+u.getName()+"\', \'"+u.getPassword()+"\');";
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString()); //Connection to database
			PreparedStatement statement = conn.prepareStatement(sql); //Prepare sql query
			statement.execute(); //Execution of the prepared query
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static ArrayList<String> selectUsername(){ //Return the username from every user of the database
		
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
	
	public static ArrayList<User> selectUser(){ //Return every user from the database, we establish id 0 because we wont use this attribute for now
		
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
	
	public static int getNextId() { //Return the next id to use for next team
		
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
