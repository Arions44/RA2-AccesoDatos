package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import models.Provider;
import models.Trading;

public class TradingService {
	
	//Method to autoIncrement the id when a new Transaction is created
	public static int getNextId() {
		ResultSet resultSet = null;
		int id = -1;
		String sql = "SELECT MAX(id) FROM Trading;";
		try {
			Connection cnn = DriverManager.getConnection(AzureSql.getCnnString());
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
		+", "+t.getAmount()+", \'"+new SimpleDateFormat("yyyy-MM-dd").format(t.getDate())+"\', \'"+t.getType()+"\');";
		try {
			Connection cnn = DriverManager.getConnection(AzureSql.getCnnString());
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
public static ArrayList<Trading> selectTrading(String field, Object value){
		
		ArrayList<Trading> trades = new ArrayList<Trading>();
		ResultSet resultSet = null;
		String sql = "SELECT * FROM Trading";
		if(field == null) {
			sql += ";";
		}else if(field.equalsIgnoreCase("id, id_provider, id_product, amount")){
			sql += " WHERE(" + field + " = " + value +");";
		}else {
			sql += " WHERE(" + field + " = \'" + value +"\');";
		}
		
		try {
			Connection conn = DriverManager.getConnection(AzureSql.getCnnString());
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				trades.add(new Trading(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trades;
	}


}
