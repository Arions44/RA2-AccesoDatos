package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

import javax.swing.JButton;

public class DownloadReportView extends JFrame{

	private JPanel contentPane;
	private JButton btnCurrentStocks, btnInventoryMovements;

	public DownloadReportView() {
		setBounds(100, 100, 600, 340);
		InterfaceModel.FrameModel(this, "Transacctions");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("DOWNLOAD REPORT");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 564, 28);
		contentPane.add(lblTitle);
		
		JLabel lblDescription = new JLabel("Choose the report to download");
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescription.setBounds(10, 50, 564, 28);
		contentPane.add(lblDescription);
		
		btnCurrentStocks = new JButton("Current stocks");
		btnCurrentStocks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCurrentStocks.setBounds(219, 89, 152, 38);
		contentPane.add(btnCurrentStocks);
		
		btnInventoryMovements = new JButton("Inventory movements");
		btnInventoryMovements.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInventoryMovements.setBounds(200, 148, 190, 38);
		contentPane.add(btnInventoryMovements);
		
		ManejadorJButton manejador = new ManejadorJButton();
		btnInventoryMovements.addActionListener(manejador);
		btnCurrentStocks.addActionListener(manejador);
		
		//iText things:
		try{
			Document document = new Document();
			String destino = "resources/reports";
			PdfWriter.getInstance(document, new FileOutputStream(destino));
			document.open();
			//Terminar esto (es una barbaridad)
			
			document.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	
		
		setVisible(true);
	}
	
	private class ManejadorJButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object btn = e.getSource();
			
			if(btn==btnCurrentStocks) {
				
			}else if(btn==btnInventoryMovements) {
				
			}
		}
		
	}
	
	
}
