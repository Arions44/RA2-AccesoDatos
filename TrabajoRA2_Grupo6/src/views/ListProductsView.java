package views;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import models.Product;
import services.ProductServices;
import services.ProviderServices;

public class ListProductsView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton buttonBack,buttonUpdate,buttonDelete,buttonCreate;
	private static JTable table;
	private int row;
	private String imageRoute;
	private JLabel image;
	private static Map<Integer,Integer> mapId;

	public ListProductsView() {
		setBounds(100, 100, 790, 480);
		InterfaceModel.FrameModel(this, "Products");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		Listener l=new Listener();
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 10, 525, 338);
		contentPane.add(scrollPane);
		
		table = new JTable(Model());
		scrollPane.setViewportView(table);
		
		buttonBack = new JButton("Back");
		buttonBack.setFont(new Font("Arial", Font.PLAIN, 10));
		buttonBack.setBounds(10, 417, 68, 21);
		buttonBack.addActionListener(l);
		getContentPane().add(buttonBack);
		
		buttonUpdate = new JButton("Update");
		buttonUpdate.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonUpdate.setBounds(281, 374, 100, 40);
		buttonUpdate.addActionListener(l);
		getContentPane().add(buttonUpdate);
		buttonUpdate.setEnabled(false);
		
		buttonDelete = new JButton("Delete");
		buttonDelete.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonDelete.setBounds(113, 374, 100, 40);
		buttonDelete.addActionListener(l);
		getContentPane().add(buttonDelete);
		buttonDelete.setEnabled(false);
		
		buttonCreate = new JButton("Create");
		buttonCreate.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonCreate.setBounds(450, 373, 100, 40);
		buttonCreate.addActionListener(l);
		getContentPane().add(buttonCreate);
		
		 table.setRowSelectionAllowed(true);
	        
	     table.setDefaultEditor(Object.class, null);
	     table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     
	     image = new JLabel("");
	     image.setBounds(582, 90, 160, 141);
	     contentPane.add(image);
	     table.getTableHeader().setResizingAllowed(false);
	     table.getTableHeader().setReorderingAllowed(false);
	     
	     table.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					
					row=table.getSelectedRow();

					if(row>=0) {
						buttonDelete.setEnabled(true);
						buttonUpdate.setEnabled(true);
					}else {
						buttonDelete.setEnabled(false);
						buttonUpdate.setEnabled(false);
					}
					System.out.println(mapId.get(row));
					
					imageRoute=ProductServices.selectImageProduct(mapId.get(row));
					ImageIcon icon = new ImageIcon(imageRoute);
                    Image i = icon.getImage().getScaledInstance(112, 137, Image.SCALE_SMOOTH);
                    ImageIcon img2 = new ImageIcon(i);
                    image.setIcon(img2);
                    image.setText(null);
				}
			});
	     
	}
	
	private TableModel Model() {
		
        String[] colum = {"Name", "Description","Price","Category","Stock","Provider name"};
        DefaultTableModel modelo = new DefaultTableModel(colum, 0);
        mapId=new HashMap<Integer,Integer>(); 
        Map<Integer, String> providerIdName = ProviderServices.selectProviderName(null, 0);
        int count=0;
        for (Product p : ProductServices.selectProduct(null, null)) {
            Object[] product = {p.getName(),p.getDescription(),p.getPrice(),p.getCategory(),p.getStock(),providerIdName.get(p.getId_provider())};
            modelo.addRow(product);
            mapId.put(count, p.getId());
            count++;
        }
        
        return modelo;
	}

	private class Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o=e.getSource();

			if(o.equals(buttonBack)) {
				dispose();
				HomeView hv=new HomeView();
				hv.setVisible(true);
			}else if(o.equals(buttonUpdate)) {
				
				
				
			}else if(o.equals(buttonDelete)) {
				
				int option = JOptionPane.showConfirmDialog(ListProductsView.this, "Are you sure you want to delete this product?", "Confirmation", JOptionPane.YES_NO_OPTION);
				
				if(option==JOptionPane.YES_OPTION) {
					if(ProductServices.deleteProduct(mapId.get(row))) {
						File f=new File(imageRoute);
						f.delete();
						image.setIcon(null);
						table.setModel(Model());
						JOptionPane.showMessageDialog(ListProductsView.this, "Product deleted!");
					}
				}
				
			}else if(o.equals(buttonCreate)) {
				dispose();
				CreateProductView cpv=new CreateProductView();
				cpv.setVisible(true);
			}
		}
	}
}
