package views;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import models.Product;
import models.Provider;
import services.ProductServices;
import services.ProviderServices;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ListProvidersView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnBack,btnView,btnDelete,btnCreate;
	private static JTable table;
	private int selectedRow;
	private static Map<Integer,Integer> mapId=new HashMap<Integer,Integer>();
	private JTextField textFilter;
	private JComboBox comboBoxFilter;
	private JButton btnApply;
	private JButton button;
	private JButton btnReset;
	private String[] columns = {"Name", "Description","Address","Phone"};

	public ListProvidersView() {
		setBounds(100, 100, 585, 480);
		InterfaceModel.FrameModel(this, "Providers");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		Listener l=new Listener();
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 53, 525, 295);
		contentPane.add(scrollPane);
		
		table = new JTable(UpdateTable(null ,null));
		scrollPane.setViewportView(table);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Arial", Font.PLAIN, 10));
		btnBack.setBounds(10, 417, 68, 21);
		btnBack.addActionListener(l);
		getContentPane().add(btnBack);
		
		btnView = new JButton("View");
		btnView.setFont(new Font("Arial", Font.PLAIN, 14));
		btnView.setBounds(281, 374, 100, 40);
		btnView.addActionListener(l);
		getContentPane().add(btnView);
		btnView.setEnabled(false);
		
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 14));
		btnDelete.setBounds(113, 374, 100, 40);
		btnDelete.addActionListener(l);
		getContentPane().add(btnDelete);
		btnDelete.setEnabled(false);
		
		btnCreate = new JButton("Create");
		btnCreate.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCreate.setBounds(450, 373, 100, 40);
		btnCreate.addActionListener(l);
		getContentPane().add(btnCreate);
		
		table.setRowSelectionAllowed(true);
	        
	    table.setDefaultEditor(Object.class, null);
	    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    
	    JLabel lblFilter = new JLabel("Filter:");
	    lblFilter.setFont(new Font("Arial", Font.PLAIN, 14));
	    lblFilter.setBounds(62, 22, 41, 14);
	    contentPane.add(lblFilter);
	    
	    comboBoxFilter = new JComboBox(columns);
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
	    btnApply.addActionListener(l);
	    btnApply.setBounds(403, 19, 89, 22);
	    contentPane.add(btnApply);
	    
	    btnReset = new JButton("Reset");
	    btnReset.setEnabled(false);
	    btnReset.setFont(new Font("Arial", Font.PLAIN, 14));
	    btnReset.addActionListener(l);
	    btnReset.setBounds(403, 19, 89, 22);
	    contentPane.add(btnReset);
	    table.getTableHeader().setResizingAllowed(false);
	    table.getTableHeader().setReorderingAllowed(false); 
	    table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedRow=table.getSelectedRow();
				if(selectedRow>=0) {
					btnDelete.setEnabled(true);
					btnView.setEnabled(true);
				}else {
					btnDelete.setEnabled(false);
					btnView.setEnabled(false);
				}
			}
		});   
	}
	
	private TableModel UpdateTable(String field, String value) {
		
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        int i=0;
        mapId = new HashMap<Integer, Integer>();
        for (Provider p : ProviderServices.selectProvider(field, value)) {
            Object[] product = {p.getName(),p.getDescription(),p.getAddress(),p.getPhone()};
            model.addRow(product);
            mapId.put(i, p.getId());
            i++;
        }
        
        return model;
	}

	private class Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o=e.getSource();
			if(o.equals(btnBack)) {
				
				dispose();
				HomeView hv=new HomeView();
				hv.setVisible(true);
				
			}else if(o.equals(btnView)) {
				
				dispose();
				ProviderView pv=new ProviderView(mapId.get(selectedRow));
				pv.setVisible(true);
				
			}else if(o.equals(btnDelete)) {
				
				int option = JOptionPane.showConfirmDialog(ListProvidersView.this, "Are you sure you want to delete this provider?", "Confirmation", JOptionPane.YES_NO_OPTION);
				
				if(option==JOptionPane.YES_OPTION) {
					if(ProductServices.deleteProduct(mapId.get(selectedRow))) {
						table.setModel(UpdateTable(null,null));
						JOptionPane.showMessageDialog(ListProvidersView.this, "Provider deleted");
					}
				}
				
			}else if(o.equals(btnCreate)) {
				
				dispose();
				ProviderView pv=new ProviderView(-1);
				pv.setVisible(true);
				
			}else if(o.equals(btnApply)) {
				table.setModel(UpdateTable((String)comboBoxFilter.getSelectedItem(),textFilter.getText()));
				btnApply.setVisible(false);
				btnApply.setEnabled(false);
				btnReset.setVisible(true);
				btnReset.setEnabled(true);
				
			}else if(o.equals(btnReset)) {
				
				table.setModel(UpdateTable(null,null));
				textFilter.setText("");
				btnApply.setVisible(true);
				btnApply.setEnabled(true);
				btnReset.setVisible(false);
				btnReset.setEnabled(false);
				
			}
		}
	}
}

