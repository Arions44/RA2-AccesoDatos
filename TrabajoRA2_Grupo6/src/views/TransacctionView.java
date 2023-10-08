package views;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import models.Provider;
import models.Trading;
import services.ProviderServices;
import services.TradingService;

public class TransacctionView extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnBack, btnAdd;
	private static JTable table;
	private JTextField textFilter;
	private static Map<Integer,Integer> mapId=new HashMap<Integer,Integer>();
	private JComboBox comboBoxFilter;
	private String[] columns = {"Product", "Provider", "Amount", "Date", "Type"};
	private String[] filter = {"Buy", "Sell", "Both"};
	private JButton btnApply;
	private String selectedFilter = "";

	public TransacctionView() {
		setBounds(100, 100, 600, 340);
		InterfaceModel.FrameModel(this, "Transacctions");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 53, 525, 295);
		contentPane.add(scrollPane);
		
		table = new JTable(UpdateTable(null ,null));
		scrollPane.setViewportView(table);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Arial", Font.PLAIN, 10));
		btnBack.setBounds(10, 417, 68, 21);
		getContentPane().add(btnBack);
		
		
		btnAdd = new JButton("Create");
		btnAdd.setFont(new Font("Arial", Font.PLAIN, 14));
		btnAdd.setBounds(450, 373, 100, 40);
		getContentPane().add(btnAdd);
		
		table.setRowSelectionAllowed(true);
	        
	    table.setDefaultEditor(Object.class, null);
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    JLabel lblFilter = new JLabel("Filter:");
	    lblFilter.setFont(new Font("Arial", Font.PLAIN, 14));
	    lblFilter.setBounds(62, 22, 41, 14);
	    contentPane.add(lblFilter);
	    
	    
	    comboBoxFilter = new JComboBox(filter);
	    comboBoxFilter.setFont(new Font("Arial", Font.PLAIN, 14));
	    comboBoxFilter.setBounds(113, 19, 100, 22);
	    contentPane.add(comboBoxFilter);
	    
	    textFilter = new JTextField();
	    textFilter.setFont(new Font("Arial", Font.PLAIN, 14));
	    textFilter.setBounds(223, 19, 170, 22);
	    contentPane.add(textFilter);
	    textFilter.setColumns(10);
		
	    btnApply = new JButton("Apply");
	    btnApply.setFont(new Font("Arial", Font.PLAIN, 14));
	    btnApply.setBounds(403, 19, 89, 22);
	    contentPane.add(btnApply);
	    
		ManejadorClass manejador=new ManejadorClass();
	    btnAdd.addActionListener(manejador);
		btnBack.addActionListener(manejador);
		comboBoxFilter.addActionListener(manejador);
		btnApply.addActionListener(manejador);
		
		setVisible(true);
	}
	
	private class ManejadorClass implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object btn = e.getSource();
			
			//Controling filter ComboBox:
		 	if (btn == btnAdd) {
	            
	        } else if (btn == btnBack) {
	            new HomeView();
	            dispose();
	        } else if (btn == comboBoxFilter) {
	        	selectedFilter = comboBoxFilter.getSelectedItem().toString();
	        } else if (btn == btnApply) {
	            String filterValue = textFilter.getText();
	            DefaultTableModel model = (DefaultTableModel) table.getModel();
	            model.setRowCount(0); // Limpiar la tabla
	            int i = 0;
	            mapId = new HashMap<Integer, Integer>();
	            
	            if (selectedFilter.equalsIgnoreCase("Buy") || selectedFilter.equalsIgnoreCase("Sell")) {
	                for (Trading t : TradingService.selectTrading("type", selectedFilter)) {
	                    String productName = TradingService.getProductById(t.getId_product());
	                    String providerName = TradingService.getProviderById(t.getId_provider());
	                    Object[] trade = {productName, providerName, t.getAmount(), t.getDate(), t.getType()};
	                    model.addRow(trade);
	                    mapId.put(i, t.getId());
	                    i++;
	                }
	            } else {
	                for (Trading t : TradingService.selectTrading(null, filterValue)) {
	                    String productName = TradingService.getProductById(t.getId_product());
	                    String providerName = TradingService.getProviderById(t.getId_provider());
	                    Object[] trade = {productName, providerName, t.getAmount(), t.getDate(), t.getType()};
	                    model.addRow(trade);
	                    mapId.put(i, t.getId());
	                    i++;
	                }
	            }
	        }
		}
	}
	
	
	private TableModel UpdateTable(String field, String value) {
			
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        int i=0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mapId = new HashMap<Integer, Integer>();
        for (Trading t : TradingService.selectTrading(field, value)) {
        	//Here I get the product name and provider name using the methods of the class TradingService
        	String productName = TradingService.getProductById(t.getId_product());
            String providerName = TradingService.getProviderById(t.getId_provider());
            
            //Here I change the format of the date display on the table using SimpleDateFormat
            String formattedDate = dateFormat.format(t.getDate());
            
            Object[] trade = {productName, providerName , t.getAmount(), formattedDate, t.getType()};
            model.addRow(trade);
            mapId.put(i, t.getId());
            i++;
        }
        
        return model;
	}
}
