package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AddTransactionView extends JFrame{

	private JPanel contentPane;

	public AddTransactionView() {
		setBounds(100, 100, 600, 340);
		InterfaceModel.FrameModel(this, "Transacctions");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
	}


}
