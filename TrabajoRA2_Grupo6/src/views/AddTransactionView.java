package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddTransactionView extends JFrame{

	private JPanel contentPane;
	private JTextField amountTF;
	private JComboBox producNameCB, typeCB, providerNameCB;
	private JButton btnBack, btnAdd;

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
		
		String[] prodName = {};
		producNameCB = new JComboBox(prodName);
		producNameCB.setBounds(159, 45, 150, 22);
		contentPane.add(producNameCB);
		
		String[] provName = {};
		providerNameCB = new JComboBox(provName);
		providerNameCB.setBounds(159, 79, 150, 22);
		contentPane.add(providerNameCB);
		
		String[] type = {"Buy", "Sell"};
		typeCB = new JComboBox(type);
		typeCB.setBounds(159, 147, 150, 22);
		contentPane.add(typeCB);
		
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
				
			}
		}
		
	}
}
