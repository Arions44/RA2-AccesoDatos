package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import models.Provider;
import services.ProviderServices;
import javax.swing.JButton;

public class ProviderView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblTitle;
	private JTextField textName;
	private JTextField textDescription;
	private JTextField textAddress;
	private JTextField textPhone;
	private JButton btnBack;
	private JButton btnEdit;
	private JButton btnSave;
	private Listener l=new Listener();
	private int id = 0;

	public ProviderView(int id) {
		
		setBounds(100, 100, 380, 260);
		getContentPane().setLayout(null);
		InterfaceModel.FrameModel(this, "New provider");
		this.id = id;
		
		lblTitle = new JLabel("New provider");
		lblTitle.setBounds(157, 11, 46, 14);
		getContentPane().add(lblTitle);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(32, 51, 46, 14);
		getContentPane().add(lblName);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(32, 79, 65, 14);
		getContentPane().add(lblDescription);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(32, 104, 46, 14);
		getContentPane().add(lblAddress);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(32, 129, 46, 14);
		getContentPane().add(lblPhone);
		
		textName = new JTextField();
		textName.setBounds(107, 48, 138, 20);
		getContentPane().add(textName);
		textName.setColumns(10);
		
		textDescription = new JTextField();
		textDescription.setColumns(10);
		textDescription.setBounds(107, 76, 138, 20);
		getContentPane().add(textDescription);
		
		textAddress = new JTextField();
		textAddress.setColumns(10);
		textAddress.setBounds(107, 101, 138, 20);
		getContentPane().add(textAddress);
		
		textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(107, 126, 138, 20);
		getContentPane().add(textPhone);
		
		btnBack = new JButton("Back");
		btnBack.setBounds(32, 187, 89, 23);
		getContentPane().add(btnBack);
		btnBack.addActionListener(l);
		
		btnEdit = new JButton("Edit");
		btnEdit.setBounds(230, 187, 89, 23);
		getContentPane().add(btnEdit);
		btnEdit.addActionListener(l);
		btnEdit.setVisible(false);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(230, 187, 89, 23);
		getContentPane().add(btnSave);
		btnSave.addActionListener(l);
		
		if(id!=-1) {
			setProviderValues(ProviderServices.selectProvider("id", id).get(0));
		}
		
	}
	
	private void setProviderValues(Provider p) {
		setTitle(p.getName());
		lblTitle.setText(p.getName());
		btnEdit.setVisible(true);
		btnSave.setVisible(false);
		textName.setText(p.getName());
		textName.setEditable(false);
		textDescription.setText(p.getDescription());
		textDescription.setEditable(false);
		textAddress.setText(p.getAddress());
		textAddress.setEditable(false);
		textPhone.setText(p.getPhone());
		textPhone.setEditable(false);
	}

	private class Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o=e.getSource();
			
			if(o.equals(btnBack)) {
				goBack();
				
			}else if(o.equals(btnEdit)) {
				
				btnSave.setVisible(true);
				btnEdit.setVisible(false);
				textName.setEditable(true);
				textDescription.setEditable(true);
				textAddress.setEditable(true);
				textPhone.setEditable(true);
			}else if(o.equals(btnSave)) {
				
				if(matchesProviderFields()) {
					if(id!=-1) {
						if(ProviderServices.updateProvider(id, textName.getText(), textDescription.getText(), textAddress.getText(), textPhone.getText())) {
							JOptionPane.showMessageDialog(ProviderView.this, "Provider updated");
						}else {
							JOptionPane.showMessageDialog(ProviderView.this, "Error");
						}
						goBack();
							
					}else {
						if(ProviderServices.insertProvider(new Provider(textName.getText(), textDescription.getText(), textAddress.getText(), textPhone.getText()))) {
							JOptionPane.showMessageDialog(ProviderView.this, "Provider inserted");
							textName.setText("");
							textDescription.setText("");
							textAddress.setText("");
							textPhone.setText("");
						}else {
							JOptionPane.showMessageDialog(ProviderView.this, "Error");
						}
					}
				}
			}
			
		}
		private void goBack() {
			
			dispose();
			ListProvidersView lpv=new ListProvidersView();
			lpv.setVisible(true);
		}
		private boolean matchesProviderFields() {
			if(!textName.getText().matches("^.{1,30}$")) {
				JOptionPane.showMessageDialog(ProviderView.this, "Name length should be between 1 and 30 characters");
				return false;
			}else if(!textDescription.getText().matches("^.{1,80}$")) {
				JOptionPane.showMessageDialog(ProviderView.this, "Description length should be between 1 and 80 characters");
				return false;
			}else if(!textAddress.getText().matches("^.{1,70}$")) {
				JOptionPane.showMessageDialog(ProviderView.this, "Address length should be between 1 and 70 characters");
				return false;
			}else if(!textPhone.getText().matches("^[1-9]\\d{8}$")) {
				JOptionPane.showMessageDialog(ProviderView.this, "The phone has to be 9 digits long and it cant start with 0");
				return false;
			}
			return true;
		}
	}
	
}
