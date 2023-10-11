package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import models.Product;
import services.ProductServices;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;

public class DownloadReportView extends JFrame{

	private JPanel contentPane;
	private JButton btnDownload;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private JDateChooser dateChooser;


	public DownloadReportView() {
		setBounds(100, 100, 600, 340);
		InterfaceModel.FrameModel(this, "Download Report");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Inventory Movements");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 564, 28);
		contentPane.add(lblTitle);
		
		dateChooser = new JDateChooser();
        dateChooser.setBounds(224, 88, 146, 38);
        contentPane.add(dateChooser);
		
		JLabel lblDescription = new JLabel("Choose a date:");
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescription.setBounds(10, 50, 564, 28);
		contentPane.add(lblDescription);
		
		
		btnDownload = new JButton("Download Report");
		btnDownload.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDownload.setBounds(200, 148, 190, 38);
		contentPane.add(btnDownload);
		
		ManejadorJButton manejador = new ManejadorJButton();
		btnDownload.addActionListener(manejador);
		
		//iText things:
	
		
		setVisible(true);
	}
	
	private class ManejadorJButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object btn = e.getSource();
			
			if(btn==btnDownload) {
				int result;

			    Date nowdate = new Date(System.currentTimeMillis());  
			    Date backday  =  ;


			    if(backday==null){//checking the date is null or not
			        JOptionPane.showMessageDialog(null, "Please Enter the Date ...");

			    }else if(!backday.before(nowdate)){//checking given date before todays date or not
			        JOptionPane.showMessageDialog(null, "Please Enter Date before Todays date...");

			    }else{
			        // backup function goes here
			        JFileChooser chooser = new JFileChooser();
		            chooser.setCurrentDirectory(new java.io.File("."));
		            chooser.setDialogTitle("Save");
		            chooser.setApproveButtonText("Save");
		            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		            chooser.setAcceptAllFileFilterUsed(false);

			            
			        if(chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){


			            try {
			                Document pdfsup = new Document();
			                PdfWriter.getInstance(pdfsup, new FileOutputStream(new File(chooser.getSelectedFile(), "Inventory Movements Report.pdf")));

			                pdfsup.open();

			            pdfsup.add(new Paragraph("Supplier Details Report",FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.BLUE)));
			            pdfsup.add(new Paragraph(new Date().toString()));
			            pdfsup.add(new Paragraph("----------------------------------------------------------------------------------------------------------------"));

			            PdfPTable tablesup= new PdfPTable(2);

			            PdfPCell cell = new PdfPCell(new Paragraph("Title"));
			            cell.setColspan(4);
			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			            cell.setBackgroundColor(BaseColor.ORANGE);
			            tablesup.addCell(cell);

			            tablesup.addCell("Supplier ID");
			            tablesup.addCell("Supplier ID2");
			            tablesup.addCell("Supplier ID3");
			            tablesup.addCell("Supplier ID4");
			            pdfsup.add(tablesup);

			            pdfsup.close();

			            JOptionPane.showMessageDialog(null, "Report Saved...");


			            } catch (Exception ex) {
			                ex.printStackTrace();
			            }
			        }else{
			            System.out.println("No Selection");
			        }
			    }
			}
		}
		
	}
	
	
}
