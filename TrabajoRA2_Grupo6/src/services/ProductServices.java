package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.Product;
import models.Provider;

public class ProductServices {
	static Connection conn=AzureSql.createConnection();
	static ResultSet resultSet=null;
	
	public static boolean insertProduct(Product p) {
		
		
		String sql="INSERT INTO Product VALUES("+p.getId()+", \'"+p.getName()+"\', \'"+p.getDescription()+"\', "+p.getPrice()
				+", \'"+p.getCategory()+"\', \'"+p.getImage()+"\', "+p.getStock()+", "+p.getId_provider()+", "+p.getAvailable()+");";
		try {
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
		String sql = "SELECT * FROM Product";
		if(field == null) {
			sql += ";";
		}else if(field.equalsIgnoreCase("id")){
			sql += " WHERE(" + field + " = " + value +");";
		}else if(field.equalsIgnoreCase("Provider name")){
			sql += " WHERE( id_Provider = " + value +");";
		}
		else {
			sql += " WHERE(" + field + " = \'" + value +"\');";
		}
		try {
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				products.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7), resultSet.getInt(8),resultSet.getInt(9)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}
	
	public static Map<Integer, String> selectProductName(String field, int id){ 
		Map<Integer, String> productIdName = new HashMap<Integer,String>();
		String sql = "SELECT id, name, available FROM Product";
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
					productIdName.put(resultSet.getInt(1), resultSet.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productIdName;
	}
	
	public static String selectImageProduct(int value){
		
		ResultSet resultSet = null;
		String sql = "SELECT image FROM Product WHERE id = "+ value+";";
		String img = null;
		try {
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			resultSet.next();
			
			img=resultSet.getString(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	public static boolean deleteProduct(int id) {
		
		String sql = "UPDATE Product SET available = "+0+" WHERE id = "+id+";";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateProduct(Product p) {
		
		String sql = "UPDATE Product SET name = \'"+p.getName()+"\', description = \'"+p.getDescription()
			+"\', price = "+p.getPrice()+", category = \'"+p.getCategory()+"\', image = \'"+p.getImage()+"\', stock = "+p.getStock() 
			+", id_provider = "+p.getId_provider()+" WHERE id = "+p.getId();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
}
