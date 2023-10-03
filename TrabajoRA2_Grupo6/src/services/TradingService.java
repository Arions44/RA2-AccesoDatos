package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Trading;

public class TradingService {
	
	//Method to autoIncrement the id when a new Transaction is created
	public static int getNextId() {
		ResultSet resultSet = null;
		int id = -1;
		String sql = "SELECT MAX(id) FROM Trading;";
		try {
			Connection cnn = DriverManager.getConnection(AzureSql.cnnString);
			Statement statement = cnn.createStatement();
			resultSet = statement.executeQuery(sql);
			resultSet.next();
			id = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id + 1;
	}
	
	public static boolean insertTrading(Trading t) {
		String sql = "INSERT INTO Trading VALUES("+t.getId()+", "+t.getId_product()+", "+t.getId_provider()
		+", "+t.getAmount()+", \'"+t.getDate().getYear()+"-"+t.getDate().getMonthValue()+"-"+t.getDate().getDayOfMonth()+"\', \'"+t.getType()+"\');";
		try {
			Connection cnn = DriverManager.getConnection(AzureSql.cnnString);
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
