package views;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListProvidersView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public ListProvidersView() {
		
		setBounds(100, 100, 380, 260);
		InterfaceModel.FrameModel(this, "Providers");
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ProviderView pv=new ProviderView(2);
				pv.setVisible(true);
			}
		});
		btnNewButton.setBounds(119, 77, 89, 23);
		getContentPane().add(btnNewButton);
		
		
		
	}

}
