package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Product;

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
	
}
