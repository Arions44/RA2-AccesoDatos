package views;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Product;
import models.Trading;
import services.ProductServices;
import services.TradingService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class UpdateTransactionView extends JFrame{

	private JPanel contentPane;
	private JTextField ProductNameTF;
	private JTextField ProviderNameTF;
	private JTextField AmountTF;
	private JTextField TypeTF;
	private JButton btnBack, btnUpdate;
	String productName, providerName;
	private int id;

	public UpdateTransactionView(int row) {
		id=row;
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
		
		ProductNameTF = new JTextField();
		ProductNameTF.setEditable(false);
		ProductNameTF.setBounds(151, 46, 152, 20);
		contentPane.add(ProductNameTF);
		ProductNameTF.setColumns(10);
		
		ProviderNameTF = new JTextField();
		ProviderNameTF.setEditable(false);
		ProviderNameTF.setColumns(10);
		ProviderNameTF.setBounds(151, 77, 152, 20);
		contentPane.add(ProviderNameTF);
		
		AmountTF = new JTextField();
		AmountTF.setColumns(10);
		AmountTF.setBounds(151, 114, 152, 20);
		contentPane.add(AmountTF);
		
		TypeTF = new JTextField();
		TypeTF.setEditable(false);
		TypeTF.setColumns(10);
		TypeTF.setBounds(151, 145, 152, 20);
		contentPane.add(TypeTF);

		btnBack = new JButton("Back");
		btnBack.setBounds(10, 268, 69, 22);
		contentPane.add(btnBack);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(379, 45, 115, 123);
		contentPane.add(btnUpdate);
		
		ManejadorJButton manejador = new ManejadorJButton();
		btnBack.addActionListener(manejador);
		btnUpdate.addActionListener(manejador);
		
		//Inserting data in the JTextField
		fileData();
		
		setVisible(true);
	}
	
	private class ManejadorJButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object btn = e.getSource();
			if(btn == btnBack) {
				new TransactionView();
				dispose();
			}else if(btn == btnUpdate) {
				String amountText = AmountTF.getText();
				if (amountText.matches("[+-]?([0-9]*[.])?[0-9]+")) {
					int productId = TradingService.getProductIdByName(productName);
			        int providerId = TradingService.getProviderIdByName(providerName);    
					TradingService.updateTrading(id, productId, providerId, Integer.parseInt(AmountTF.getText()), TypeTF.getText());
					JOptionPane.showMessageDialog(UpdateTransactionView.this, "Trade updated successfuly!");
	            } else {
	            	JOptionPane.showMessageDialog(UpdateTransactionView.this, "Amount only permit numbers");
	            }
			}
		}
		
	}
	
	private void fileData() {
		for (Trading t : TradingService.selectTrading("id", id)) {
			productName = TradingService.getProductById(t.getId_product());
			providerName = TradingService.getProviderById(t.getId_provider());
			ProductNameTF.setText(productName);
			ProviderNameTF.setText(providerName);
			AmountTF.setText(String.valueOf(t.getAmount()));
			TypeTF.setText(t.getType());
			
			
		}
		
	}
}
