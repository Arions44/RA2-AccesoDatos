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
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ListProductsView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton buttonBack,buttonUpdate,buttonDelete,buttonCreate,buttonApply,buttonReset;
	private static JTable table;
	private int row;
	private String imageRoute;
	private JLabel image,labelFilter;
	private static Map<Integer,Integer> mapId;
	private static  Map<Integer, String> providerIdName;
	private static String[] colum = {"Name", "Description","Price","Category","Stock","Provider name"};
	private JTextField filter;
	private JComboBox typeFilter;

	public ListProductsView() {
		setBounds(100, 100, 864, 482);
		InterfaceModel.FrameModel(this, "Products");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		Listener l=new Listener();
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 81, 603, 267);
		contentPane.add(scrollPane);
		
		table = new JTable(Model(null,null));
		scrollPane.setViewportView(table);
		
		ImageIcon back=new ImageIcon("resources/icon/Back.png");
		buttonBack = new JButton(back);
		buttonBack.setToolTipText("Back");
		buttonBack.setFont(new Font("Arial", Font.PLAIN, 10));
		buttonBack.setBounds(10, 403, 35, 35);
		buttonBack.addActionListener(l);
		getContentPane().add(buttonBack);
		
		ImageIcon update=new ImageIcon("resources/icon/Update.png");
		buttonUpdate = new JButton(update);
		buttonUpdate.setToolTipText("Update");
		buttonUpdate.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonUpdate.setBounds(281, 374, 47, 47);
		buttonUpdate.addActionListener(l);
		getContentPane().add(buttonUpdate);
		buttonUpdate.setEnabled(false);
		
		ImageIcon delete=new ImageIcon("resources/icon/Delete.png");
		buttonDelete = new JButton(delete);
		buttonDelete.setToolTipText("Delete");
		buttonDelete.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonDelete.setBounds(113, 374, 47, 47);
		buttonDelete.addActionListener(l);
		getContentPane().add(buttonDelete);
		buttonDelete.setEnabled(false);
		
		ImageIcon add=new ImageIcon("resources/icon/Add.png");
		buttonCreate = new JButton(add);
		buttonCreate.setToolTipText("Create");
		buttonCreate.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonCreate.setBounds(450, 373, 47, 47);
		buttonCreate.addActionListener(l);
		getContentPane().add(buttonCreate);
		
		 table.setRowSelectionAllowed(true);
	        
	     table.setDefaultEditor(Object.class, null);
	     table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     
	     image = new JLabel("");
	     image.setBounds(645, 120, 195, 169);
	     contentPane.add(image);
	     
	     labelFilter = new JLabel("Filter:");
	     labelFilter.setFont(new Font("Arial", Font.PLAIN, 14));
	     labelFilter.setBounds(62, 25, 51, 21);
	     contentPane.add(labelFilter);
	     
	     typeFilter = new JComboBox(colum);
	     typeFilter.setFont(new Font("Arial", Font.PLAIN, 14));
	     typeFilter.setBounds(113, 25, 116, 21);
	     contentPane.add(typeFilter);
	     
	     filter = new JTextField();
	     filter.setFont(new Font("Arial", Font.PLAIN, 14));
	     filter.setBounds(247, 24, 116, 23);
	     contentPane.add(filter);
	     filter.setColumns(10);
	     
	     buttonApply = new JButton("Apply");
	     buttonApply.setFont(new Font("Arial", Font.PLAIN, 14));
	     buttonApply.setBounds(388, 23, 100, 25);
	     buttonApply.addActionListener(l);
	     contentPane.add(buttonApply);
	     
	     buttonReset = new JButton("Reset");
	     buttonReset.setFont(new Font("Arial", Font.PLAIN, 14));
	     buttonReset.setBounds(388, 23, 100, 25);
	     buttonReset.addActionListener(l);
	     contentPane.add(buttonReset);
	     buttonReset.setVisible(false);
	     
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
					
					imageRoute=ProductServices.selectImageProduct(mapId.get(row));
					ImageIcon icon = new ImageIcon(imageRoute);
                    Image i = icon.getImage().getScaledInstance(184, 160, Image.SCALE_SMOOTH);
                    ImageIcon img2 = new ImageIcon(i);
                    image.setIcon(img2);
                    image.setText(null);
				}
			});
	     
	}
	
	//Method to create and add data from the database to the table. The parameters it has are used to apply search filters.
	private TableModel Model(String field, Object value) {
        
        DefaultTableModel modelo = new DefaultTableModel(colum, 0);
        mapId=new HashMap<Integer,Integer>(); //This map associates the number of rows to each product id.
        providerIdName = ProviderServices.selectProviderName(null, 0,false); //This map collects the id and name of the supplier
        int count=0;
        for (Product p : ProductServices.selectProduct(field, value)) {
        	if(p.getAvailable()==1) {
	            Object[] product = {p.getName(),p.getDescription(),p.getPrice(),p.getCategory(),p.getStock(),providerIdName.get(p.getId_provider())};
	            modelo.addRow(product);
	            mapId.put(count, p.getId());
	            count++;
        	}
        }
        return modelo;
	}

	private class Listener implements ActionListener{
		boolean active=false;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o=e.getSource();
			
			if(o.equals(buttonBack)) {
				dispose();
				HomeView hv=new HomeView();
				hv.setVisible(true);
			}else if(o.equals(buttonUpdate)) {
				dispose();
				UpdateProductView upv=new UpdateProductView(mapId.get(row));
				upv.setVisible(true);
			}else if(o.equals(buttonDelete)) {
				
				int option = JOptionPane.showConfirmDialog(ListProductsView.this, "Are you sure you want to delete this product?", "Confirmation", JOptionPane.YES_NO_OPTION);
				
				if(option==JOptionPane.YES_OPTION) {
					if(ProductServices.deleteProduct(mapId.get(row))) {
						image.setIcon(null);
						//Updates the table once a product is deleted, depending on whether there is a filter or not, one action or another is performed.
						if(active) {
							if(typeFilter.getSelectedItem().equals("Provider name")) {
								table.setModel(Model((String)typeFilter.getSelectedItem(),getKeyFromValue(filter.getText())));
							}else {
								table.setModel(Model((String)typeFilter.getSelectedItem(),filter.getText()));
							}
						}
						else {
							table.setModel(Model(null,null));
						}
						buttonDelete.setEnabled(false);
						buttonUpdate.setEnabled(false);
						JOptionPane.showMessageDialog(ListProductsView.this, "Product deleted!");
					}
				}
				
			}else if(o.equals(buttonCreate)) {
				dispose();
				CreateProductView cpv=new CreateProductView();
				cpv.setVisible(true);
				
			}else if(o.equals(buttonApply)) {
				if(filter.getText().length()>0) {
					//Apply filters according to the type of element selected, if the filter is for the name of suppliers, the getKeyFromValue method is required.
					active=true;
					if(typeFilter.getSelectedItem().equals("Provider name")) {
						table.setModel(Model("id_provider",getKeyFromValue(filter.getText())));
					}else {
						table.setModel(Model((String)typeFilter.getSelectedItem(),filter.getText()));
					}
					buttonApply.setVisible(false);
					buttonReset.setVisible(true);
					
					buttonDelete.setEnabled(false);
					buttonUpdate.setEnabled(false);
					filter.setEditable(false);
					image.setIcon(null);
				}else
					JOptionPane.showMessageDialog(ListProductsView.this, "You have not applied any filter!");
				
			}else if(o.equals(buttonReset)) {
				//Resets the filter, emptying the field and updating the table.
				active=false;
				table.setModel(Model(null,null));
				filter.setText("");
				buttonReset.setVisible(false);
				buttonApply.setVisible(true);
				filter.setEditable(true);
			}
		}
	}
	
	//Method used when applying filters on supplier names, it is necessary to search for names based on the IDs of the providers that have the products
	private static int getKeyFromValue(String value) {
        for (Map.Entry<Integer, String> entry : providerIdName.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return 0;
   }
}
