package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Trading;
import services.ProductServices;
import services.ProviderServices;
import services.TradingService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddTransactionView extends JFrame{

	private JPanel contentPane;
	private JTextField amountTF;
	private JComboBox<String> productNameCB, typeCB, providerNameCB;
	Map<Integer, String> productNamesMap, providerNamesMap;
	String productName, providerName; 
	private JButton btnBack, btnAdd;
	private String ParameterType = "buy";

	public AddTransactionView() {
		setBounds(100, 100, 600, 340);
		InterfaceModel.FrameModel(this, "Add Transaction");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProductName = new JLabel("Product Name:");
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProductName.setBounds(39, 43, 110, 23);
		contentPane.add(lblProductName);
		
		JLabel lblProviderName = new JLabel("Provider Name:");
		lblProviderName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProviderName.setBounds(39, 77, 110, 23);
		contentPane.add(lblProviderName);
		
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAmount.setBounds(39, 111, 110, 23);
		contentPane.add(lblAmount);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblType.setBounds(39, 145, 110, 23);
		contentPane.add(lblType);
		
		ArrayList<Integer> productIds = ProductServices.getProductIdsByType(ParameterType);
		productNamesMap = new HashMap<>();
		productNameCB = new JComboBox<>();
		productNameCB.setBounds(159, 45, 150, 22);
		contentPane.add(productNameCB);
		
		for (Integer prodId : productIds) {
			productName = TradingService.getProductById(prodId); 
		    productNamesMap.put(prodId, productName);
		    productNameCB.addItem(productName);
		}
		
		
		
		ArrayList<Integer> providerIds = ProviderServices.getProviderIds();
		providerNamesMap = new HashMap<>();

		providerNameCB = new JComboBox<>();
		providerNameCB.setBounds(159, 79, 150, 22);
		contentPane.add(providerNameCB);
		
		for (Integer provId : providerIds) {
		    providerName = TradingService.getProviderById(provId); 
		    providerNamesMap.put(provId, providerName);
		    providerNameCB.addItem(providerName); 
		}
		
		String[] type = {"Buy", "Sell"};
		typeCB = new JComboBox(type);
		typeCB.setBounds(159, 147, 150, 22);
		contentPane.add(typeCB);
		typeCB.setSelectedIndex(0);
		typeCB.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String selectedType = (String) typeCB.getSelectedItem();
		        if (selectedType != null) {
		            ParameterType = selectedType.equalsIgnoreCase("Buy") ? "buy" : "sell";
		            ArrayList<Integer> productIds = ProductServices.getProductIdsByType(ParameterType);
		            productNameCB.removeAllItems();
		            for (Integer prodId : productIds) {
		                String productName = TradingService.getProductById(prodId);
		                productNameCB.addItem(productName); 
		            }
		        }
		    }
		});
		
		amountTF = new JTextField();
		amountTF.setBounds(159, 114, 150, 20);
		contentPane.add(amountTF);
		amountTF.setColumns(10);
		
		btnBack = new JButton("Back");
		btnBack.setBounds(10, 268, 69, 22);
		contentPane.add(btnBack);
		
		btnAdd = new JButton("ADD");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAdd.setBounds(379, 45, 115, 123);
		contentPane.add(btnAdd);
		
		setVisible(true);
		
		ManejadorJButton manejador = new ManejadorJButton();
		btnBack.addActionListener(manejador);
		btnAdd.addActionListener(manejador);
	}
	
	private class ManejadorJButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object btn = e.getSource();
			if(btn == btnBack) {
				new TransactionView();
				dispose();
			}else if(btn == btnAdd) {
				String selectedProductName = (String) productNameCB.getSelectedItem();
		        String selectedProviderName = (String) providerNameCB.getSelectedItem();
		        String selectedType = (String) typeCB.getSelectedItem();
		        String amountText = amountTF.getText();

		        int productId = TradingService.getProductIdByName(selectedProductName);
		        int providerId = TradingService.getProviderIdByName(selectedProviderName);    
		        

		        if (selectedProductName != null && selectedProviderName != null && selectedType != null && !amountText.isEmpty()) {

		            int amount = Integer.parseInt(amountText);

		            if (productId != -1 && providerId != -1) {
		                Trading trading = new Trading(productId, providerId, amount, selectedType);
		                if (TradingService.insertTrading(trading)) {
		                    JOptionPane.showMessageDialog(null, "Trading added successfully.");
		                    System.out.println(trading);
		                } else {
		                    JOptionPane.showMessageDialog(null, "Error inserting the trading.", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		            } else {
		                JOptionPane.showMessageDialog(null, "Selected product or provider not found.", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select a product, provider, type, and enter an amount.", "Warning", JOptionPane.WARNING_MESSAGE);
		        }
			}
		}
		
	}
}
