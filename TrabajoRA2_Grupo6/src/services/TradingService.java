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
import java.util.Date;

import javax.swing.JOptionPane;

import models.Product;
import models.Provider;
import models.Trading;

public class TradingService {

	static ResultSet resultSet = null;
	static Connection cnn = AzureSql.createConnection();

	// Method to autoIncrement the id when a new Transaction is created
	public static int getNextId() {
		int id = -1;
		String sql = "SELECT MAX(id) FROM Trading;";
		try {
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
		if (ProductServices.selectProductStock(t.getId_product()) >= t.getAmount()
				|| t.getType().equalsIgnoreCase("buy")) {
			String sql = "INSERT INTO Trading VALUES(" + t.getId() + ", " + t.getId_product() + ", "
					+ t.getId_provider() + ", " + t.getAmount() + ", \'"
					+ new SimpleDateFormat("yyyy-MM-dd").format(t.getDate()) + "\', \'" + t.getType() + "\');";
			try {
				PreparedStatement statement = cnn.prepareStatement(sql);
				statement.execute();
				if (t.getType().equalsIgnoreCase("buy")) {
					updateProductStock(t.getId_product(), t.getAmount()); // Increment stock
				} else if (t.getType().equalsIgnoreCase("sell")) {
					updateProductStock(t.getId_product(), -t.getAmount()); // Decrement stock
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} else
			JOptionPane.showMessageDialog(null, "Stock insufficient", "Warning!", JOptionPane.WARNING_MESSAGE);
		return true;
	}

	public static ArrayList<Trading> selectTrading(String field, Object value) {

		ArrayList<Trading> trades = new ArrayList<Trading>();
		String sql = "SELECT * FROM Trading";
		if (field == null) {
			sql += ";";
		} else if (field.equalsIgnoreCase("id, id_provider, id_product, amount")) {
			sql += " WHERE(" + field + " = " + value + ");";
		} else {
			sql += " WHERE(" + field + " = \'" + value + "\');";
		}

		try {
			Statement statement = cnn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				trades.add(new Trading(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trades;
	}

	// Method to increment o decrement the stock of the products in the data base
	private static void updateProductStock(int productId, int amountChange) {
		String sql = "UPDATE Product SET stock = stock + ? WHERE id = ?";
		try {
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.setInt(1, amountChange);
			statement.setInt(2, productId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean updateTrading(int id, int idProduct, int idProvider, int amount, String type) {
		String sql = "UPDATE Trading SET id_product = ?, id_provider = ?, amount = ?, type = ? WHERE id = ?";
		try {
			// Get the previous Amount before the trade
			int previousAmount = getPreviousAmount(id);
			int amountChange = amount - previousAmount;

			// Use the method to update the stock on the data base
			updateProductStock(idProduct, amountChange);

			// Update the trade
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.setInt(1, idProduct);
			statement.setInt(2, idProvider);
			statement.setInt(3, amount);
			statement.setString(4, type);
			statement.setInt(5, id);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int getPreviousAmount(int id) {
		int previousAmount = 0;
		String sql = "SELECT amount FROM Trading WHERE id = ?";
		try {
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				previousAmount = resultSet.getInt("amount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return previousAmount;
	}

	// Methods to get the product and provider name using their Ids
	public static String getProviderById(int providerId) {
		String providerName = null;
		String sql = "SELECT name FROM Provider WHERE id = ?";
		try {
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.setInt(1, providerId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				providerName = resultSet.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return providerName;
	}

	public static String getProductById(int productId) {
		String productName = null;
		String sql = "SELECT name FROM Product WHERE id = ?";
		try {
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.setInt(1, productId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				productName = resultSet.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productName;
	}

	// Methods to get the product and provider id using their names

	public static int getProductIdByName(String productName) {
		int productId = -1;
		String sql = "SELECT id FROM Product WHERE name = ?";
		try {
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.setString(1, productName);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				productId = resultSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productId;
	}

	public static int getProviderIdByName(String providerName) {
		int providerId = -1;
		String sql = "SELECT id FROM Provider WHERE name = ?";
		try {
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.setString(1, providerName);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				providerId = resultSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return providerId;
	}

	public static ArrayList<Trading> getReportData(Date backdate, Date nowdate) {
		ArrayList<Trading> reportData = new ArrayList<>();

		String sql = "SELECT * FROM Trading WHERE date BETWEEN ? AND ?";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			PreparedStatement statement = cnn.prepareStatement(sql);
			statement.setString(1, dateFormat.format(backdate));
			statement.setString(2, dateFormat.format(nowdate));
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				reportData.add(new Trading(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
						resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reportData;
	}

}
