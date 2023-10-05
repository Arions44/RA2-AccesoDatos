package views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.User;
import services.UserServices;
import javax.swing.JButton;
import java.awt.Font;

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton buttonProducts,buttonProviders,buttonTransactions,buttonSignOut;

	public HomeView() {
		setBounds(100, 100, 450, 300);
		InterfaceModel.FrameModel(this, "Home");
		getContentPane().setLayout(null);
		
		Listener l=new Listener();
		
		buttonProducts = new JButton("Products");
		buttonProducts.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonProducts.setBounds(163, 51, 135, 46);
		buttonProducts.addActionListener(l);
		getContentPane().add(buttonProducts);
		
		buttonProviders = new JButton("Providers");
		buttonProviders.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonProviders.setBounds(163, 117, 135, 43);
		buttonProviders.addActionListener(l);
		getContentPane().add(buttonProviders);
		
		buttonTransactions = new JButton("Transactions");
		buttonTransactions.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonTransactions.setBounds(163, 182, 135, 46);
		buttonTransactions.addActionListener(l);
		getContentPane().add(buttonTransactions);
		
		buttonSignOut = new JButton("Sign Out");
		buttonSignOut.setFont(new Font("Arial", Font.PLAIN, 10));
		buttonSignOut.setBounds(24, 220, 85, 21);
		buttonSignOut.addActionListener(l);
		getContentPane().add(buttonSignOut);
		

	}
	
	private class Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o=e.getSource();

			if(o.equals(buttonProducts)) {
				
			}
			else if(o.equals(buttonProviders)) {
				dispose();
				ListProvidersView lpv=new ListProvidersView();
				lpv.setVisible(true);
			}
			else if(o.equals(buttonTransactions)) {
				
			}
			else if(o.equals(buttonSignOut)) {
				dispose();
				LoginView lv=new LoginView();
				lv.setVisible(true);
			}
		}
	}
}
