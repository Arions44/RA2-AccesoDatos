package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class RegisterView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField Username;
	private JPasswordField password,passwordConfirm;
	private JButton buttonRegister,buttonBack;
	
	public RegisterView() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		setBounds(100, 100, 380, 260);
		InterfaceModel.FrameModel(this, "Register");
		getContentPane().setLayout(null);
		
		Manejador m=new Manejador();
		
		JLabel labelUsername = new JLabel("Username:");
		labelUsername.setBounds(46, 34, 123, 13);
		labelUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		labelUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		getContentPane().add(labelUsername);
		
		JLabel labelPassword = new JLabel("Password:");
		labelPassword.setBounds(46, 66, 123, 13);
		labelPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		getContentPane().add(labelPassword);
		
		JLabel labelConfimPassword = new JLabel("Confim Password:");
		labelConfimPassword.setBounds(46, 99, 123, 13);
		labelConfimPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		getContentPane().add(labelConfimPassword);
		
		Username = new JTextField();
		Username.setBounds(179, 32, 136, 19);
		getContentPane().add(Username);
		Username.setColumns(10);
		
		password = new JPasswordField();
		password.setFont(new Font("Arial", Font.PLAIN, 14));
		password.setBounds(179, 63, 136, 19);
		getContentPane().add(password);
		
		passwordConfirm = new JPasswordField();
		passwordConfirm.setFont(new Font("Arial", Font.PLAIN, 14));
		passwordConfirm.setBounds(179, 96, 136, 19);
		getContentPane().add(passwordConfirm);
		
		buttonRegister = new JButton("Register");
		buttonRegister.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonRegister.setBounds(199, 144, 103, 33);
		buttonRegister.addActionListener(m);
		getContentPane().add(buttonRegister);
		
		buttonBack = new JButton("Back");
		buttonBack.setFont(new Font("Arial", Font.PLAIN, 10));
		buttonBack.setBounds(22, 174, 66, 21);
		buttonBack.addActionListener(m);
		getContentPane().add(buttonBack);
	}
	
	private class Manejador implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o=e.getSource();

			if(o.equals(buttonRegister)) {
				//llamar a userservices
			}else if(o.equals(buttonBack)) {
				dispose();
				LoginView lv=new LoginView();
				lv.setVisible(true);
			}
		}
	}

}
