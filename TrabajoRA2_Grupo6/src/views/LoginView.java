package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	private JButton buttonSignIn,buttonSignUp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginView() {
		setBounds(100, 100, 380, 300);
		InterfaceModel.FrameModel(this, "Login");
		getContentPane().setLayout(null);
		
		Manejador m=new Manejador();
		
		JLabel labelUsername = new JLabel("Username:");
		labelUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		labelUsername.setBounds(44, 43, 70, 17);
		getContentPane().add(labelUsername);
		
		JLabel labelPassword = new JLabel("Password:");
		labelPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		labelPassword.setBounds(44, 79, 70, 17);
		getContentPane().add(labelPassword);
		
		username = new JTextField();
		username.setFont(new Font("Arial", Font.PLAIN, 14));
		username.setBounds(124, 42, 129, 19);
		getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setFont(new Font("Arial", Font.PLAIN, 14));
		password.setBounds(124, 79, 129, 19);
		getContentPane().add(password);
		
		JLabel labelNewAccount = new JLabel("New account?");
		labelNewAccount.setFont(new Font("Arial", Font.PLAIN, 14));
		labelNewAccount.setBounds(146, 183, 101, 17);
		getContentPane().add(labelNewAccount);
		
		buttonSignIn = new JButton("Sign in");
		buttonSignIn.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonSignIn.setBounds(130, 125, 120, 30);
		buttonSignIn.addActionListener(m);
		getContentPane().add(buttonSignIn);
		
		buttonSignUp = new JButton("Sign up");
		buttonSignUp.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonSignUp.setBounds(130, 210, 120, 30);
		buttonSignUp.addActionListener(m);
		getContentPane().add(buttonSignUp);
		

	}
	
	private class Manejador implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o=e.getSource();

			if(o.equals(buttonSignIn)) {
				
			}else if(o.equals(buttonSignUp)) {
				dispose();
				RegisterView rw=new RegisterView();
				rw.setVisible(true);
			}
		}
	}
	
}
