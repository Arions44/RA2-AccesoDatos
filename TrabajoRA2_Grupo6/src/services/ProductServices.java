package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.Product;
import models.Provider;

public class ProductServices {
	
	public static boolean insertProduct(Product p) {
		ResultSet resultSet=null;
		
		String sql="INSERT INTO Product VALUES("+p.getId()+", \'"+p.getName()+"\', \'"+p.getDescription()+"\', "+p.getPrice()
				+", \'"+p.getCategory()+"\', \'"+p.getImage()+"\', "+p.getStock()+", "+p.getId_provider()+");";
		try {
			Connection conn=DriverManager.getConnection(AzureSql.getCnnString());
			PreparedStatement statement=conn.prepareStatement(sql);
			statement.execute();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//This method is used when creating a new product by putting the last id collected in the Products table.
	public static int getNextId() {
		ResultSet resultSet=null;
		int id=0;
		String sql="SELECT MAX(id)FROM Product;";
		try {
			Connection conn=DriverManager.getConnection(AzureSql.getCnnString());
			Statement statement=conn.createStatement();
			resultSet=statement.executeQuery(sql);
			resultSet.next();
			id=resultSet.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return id+1;
	}
	
	
	public static ArrayList<Product> selectProduct(String field, Object value){
		
		ArrayList<Product> products = new ArrayList<Product>();
		ResultSet resultSet = null;
		String sql = "SELECT * FROM Product";
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
				products.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7), resultSet.getInt(8)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}
	
}
