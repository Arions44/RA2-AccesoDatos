package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UpdateTransactionView extends JFrame{

	private JPanel contentPane;

	public UpdateTransactionView() {
		setBounds(100, 100, 600, 340);
		InterfaceModel.FrameModel(this, "Update Transaction");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
	}


}
