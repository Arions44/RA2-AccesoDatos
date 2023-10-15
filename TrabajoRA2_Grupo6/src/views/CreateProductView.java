package views;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import models.Product;
import services.ProductServices;
import services.ProviderServices;

public class CreateProductView extends JFrame {
	static String[] cat= {"Suit","Blouse","Shoes","Jacket","Trainers","Jeans","Shirt"};
	static String[] np;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton buttonBack,buttonCreate;
	private JTextField name,description,price;
	private JComboBox category,providerNames;
	private String pathImage=null;
	private JLabel image;
	private Path finalPath;
	private static Map<Integer, String> providerIdName;
	private ImageIcon defaultIcon = new ImageIcon("resources/images/default.jpg");
	private Image defaultImage = defaultIcon.getImage().getScaledInstance(168, 88, Image.SCALE_SMOOTH);
	private ImageIcon defaultIcon2 = new ImageIcon(defaultImage);
	private Product product;
	private String oldImagePath;
	
	public CreateProductView() {
		setBounds(100, 100, 600, 440);
		InterfaceModel.FrameModel(this, "Create product");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		Listener l=new Listener();
		contentPane.setLayout(null);
		
		buttonBack = new JButton("Back");
		buttonBack.setFont(new Font("Arial", Font.PLAIN, 10));
		buttonBack.setBounds(10, 346, 96, 21);
		buttonBack.addActionListener(l);
		contentPane.add(buttonBack);
		
		buttonCreate = new JButton("Create");
		buttonCreate.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonCreate.setBounds(194, 290, 100, 40);
		buttonCreate.addActionListener(l);
		contentPane.add(buttonCreate);
		
		JLabel labelName = new JLabel("Name:");
		labelName.setFont(new Font("Arial", Font.PLAIN, 14));
		labelName.setHorizontalAlignment(SwingConstants.RIGHT);
		labelName.setBounds(29, 49, 100, 21);
		contentPane.add(labelName);
		
		JLabel labelDescription = new JLabel("Description:");
		labelDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		labelDescription.setFont(new Font("Arial", Font.PLAIN, 14));
		labelDescription.setBounds(29, 93, 100, 21);
		contentPane.add(labelDescription);
		
		JLabel labelPrice = new JLabel("Price:");
		labelPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPrice.setFont(new Font("Arial", Font.PLAIN, 14));
		labelPrice.setBounds(29, 137, 100, 21);
		contentPane.add(labelPrice);
		
		JLabel labelCategory = new JLabel("Category:");
		labelCategory.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCategory.setFont(new Font("Arial", Font.PLAIN, 14));
		labelCategory.setBounds(29, 180, 100, 21);
		contentPane.add(labelCategory);
		
		JLabel labelProvider = new JLabel("Provider:");
		labelProvider.setHorizontalAlignment(SwingConstants.RIGHT);
		labelProvider.setFont(new Font("Arial", Font.PLAIN, 14));
		labelProvider.setBounds(29, 228, 100, 21);
		contentPane.add(labelProvider);
		
		name = new JTextField();
		name.setFont(new Font("Arial", Font.PLAIN, 14));
		name.setBounds(160, 49, 130, 21);
		contentPane.add(name);
		name.setColumns(10);
		
		description = new JTextField();
		description.setFont(new Font("Arial", Font.PLAIN, 14));
		description.setColumns(10);
		description.setBounds(160, 93, 130, 21);
		contentPane.add(description);
		
		price = new JTextField();
		price.setFont(new Font("Arial", Font.PLAIN, 14));
		price.setColumns(10);
		price.setBounds(160, 137, 130, 21);
		contentPane.add(price);
		
		category = new JComboBox(cat);
		category.setFont(new Font("Arial", Font.PLAIN, 14));
		category.setBounds(160, 180, 125, 21);
		contentPane.add(category);
		
		setProviderNames();
		
		providerNames = new JComboBox(np);
		providerNames.setFont(new Font("Arial", Font.PLAIN, 14));
		providerNames.setBounds(160, 229, 125, 21);
		contentPane.add(providerNames);
		
		image = new JLabel(defaultIcon2);
		image.setBounds(356, 112, 198, 121);
		contentPane.add(image);
		image.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				//This part is used to check if an image already exists associated with a product. 
				//If the product already exists and an image with the same name is selected, it allows the use of it.
				if(ProductServices.selectProduct("name", name.getText()).isEmpty()) {
					product = null;
					oldImagePath="";
				}
				else {
					product=ProductServices.selectProduct("name", name.getText()).get(0);
					oldImagePath=product.getImage();
				}
				
				String filePath=bringFileChooserImage();
				ImageIcon icon = new ImageIcon(filePath);
                Image i = icon.getImage().getScaledInstance(168, 88, Image.SCALE_SMOOTH);
                ImageIcon img2 = new ImageIcon(i);
                image.setIcon(img2);
                image.setToolTipText(filePath);
                
			}
		});

	}
	
	//This method collects the names of all the suppliers to display them in the JComboBox.
	private void setProviderNames() {
		providerIdName = ProviderServices.selectProviderName(null, 0, true);
		
		List<String> list=new ArrayList<>();
		for (Entry<Integer, String> entry : providerIdName.entrySet()) {
			list.add(entry.getValue());
        }
		np=new String[list.size()];
		np=list.toArray(np);
	}

	private class Listener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o=e.getSource();
			
			if(o.equals(buttonBack)) {
				dispose();
				ListProductsView lpv=new ListProductsView();
				lpv.setVisible(true);
			}else if(o.equals(buttonCreate)) {
				if(check()) {
					if(!(pathImage==null)) {
						
						int op = action(product);
						if(op==-1) {
							if(ProductServices.insertProduct(new Product(name.getText(),description.getText(),Float.parseFloat(price.getText()),
									(String)category.getSelectedItem(),pathImage,getKeyFromValue((String)providerNames.getSelectedItem())))) {
								if(!image.getToolTipText().matches("(.*)TrabajoRA2_Grupo6//resources//images//(.*)")) {
									//This if causes the product to not save the image until it is added to the database.
						    		try {
										Files.copy(Paths.get(image.getToolTipText()), finalPath);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
						    		JOptionPane.showMessageDialog(CreateProductView.this, "Product created successfuly");
						    	}

								name.setText("");
								description.setText("");
								price.setText("");
								
								image.setIcon(defaultIcon2);
								pathImage=null;
							}else {
					    		JOptionPane.showMessageDialog(CreateProductView.this, "Error inserting product");
					    	}
							
						}else if(op==0) {
							
								if(ProductServices.updateProduct(product.getId(),name.getText(),description.getText(),Float.parseFloat(price.getText()),
										(String)category.getSelectedItem(),pathImage,getKeyFromValue((String)providerNames.getSelectedItem()),1)) {
									if(!image.getToolTipText().matches("(.*)TrabajoRA2_Grupo6//resources//images//(.*)")) {
										//This if causes the product to not save the image until it is added to the database.
										//In this, unlike the insert, it updates the image, eliminating the old one if another is used.
										try {
							    			if(!oldImagePath.equals(pathImage)) {
											Files.copy(Paths.get(image.getToolTipText()), finalPath);
											File f=new File(String.valueOf(product.getImage()));
											f.delete();
							    			}
										} catch (IOException e1) {
											e1.printStackTrace();
										}
							    		JOptionPane.showMessageDialog(CreateProductView.this, "Product created successfuly");
							    	}
									name.setText("");
									description.setText("");
									price.setText("");
									
									image.setIcon(defaultIcon2);
									pathImage=null;
								}else {
									JOptionPane.showMessageDialog(CreateProductView.this, "Error inserting product");
								}
							
						}else {
							JOptionPane.showMessageDialog(CreateProductView.this, "That product already exists");
						}
						
					}
					else {
						JOptionPane.showMessageDialog(CreateProductView.this, "You have not selected any images");
					}
				}
			}
		}
		
		//This method is used to notify if the product to be added is new, 
		//has already been created (to activate it), or if it already exists.
		//This method is called in the create button, if it returns -1 it inserts the product, if it 
		//returns 0 it updates the previously existing product, and if it returns 1 it warns that the product already exists.
		private int action(Product product){
			if(product!=null) {
				if(product.getAvailable()==1)
					return 1;
				else
					return 0;
			}else
				return -1;
			
		}
		
		//Method to verify that the data entered in the JTextField and the image meet minimum requirements.
		private boolean check() {
			if(!name.getText().matches("^.{1,30}$")) {
				JOptionPane.showMessageDialog(CreateProductView.this, "The name has to be between 1 and 30 long");
				return false;
			}else if(!description.getText().matches("^.{1,50}$")) {
				JOptionPane.showMessageDialog(CreateProductView.this, "The description has to be between 1 and 50 long");
				return false;
			}else if(price.getText().length()==0) {
				JOptionPane.showMessageDialog(CreateProductView.this, "The price cannot be empty");
				return false;
			}else if(!price.getText().matches("[+-]?([0-9]*[.])?[0-9]+")) {
				JOptionPane.showMessageDialog(CreateProductView.this, "Price only permit numbers");
				return false;
			}else if(Float.valueOf(price.getText())<=0){
				JOptionPane.showMessageDialog(CreateProductView.this, "The price cannot be 0 or less");
				return false;
			}
			return true;
		}
	}
	
	//Method to add image
	public String bringFileChooserImage() {
		
		JFileChooser fc=new JFileChooser();
		String path="";
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG and GIF images", "JPG", "GIF","PNG"); 
	    fc.setFileFilter(imgFilter);
	    int result = fc.showOpenDialog(null);
	    try {
		    File file=fc.getSelectedFile();
		    path=file.getAbsolutePath();
		    
		    if(file==null || file.getName().equalsIgnoreCase("")) {
		    	JOptionPane.showMessageDialog(null, "Choose an image");
		    }else {
		    	pathImage = "resources/images/"+file.getName();
		    	File f=new File(pathImage);
		    	if(f.exists() && !oldImagePath.equals(pathImage)) {
		    		JOptionPane.showMessageDialog(null, "That name is used. Change the file name.");
					path="resources/images/default.jpg";
					return path;
		    	}
		    	else {
			    	finalPath=Path.of(pathImage).toAbsolutePath();
		    	}
		    }
	    }catch(Exception e) {
	    	//e.printStackTrace();
	    	JOptionPane.showMessageDialog(CreateProductView.this, "Error selecting the image!");
	    }
	    	
	    
	    if(path.length()==0)
	    	path="resources/images/default.jpg";
	    	
	    return path;
	}
	
	//Method to recover the id of each provider to be able to add to the database.
	private static int getKeyFromValue(String value) {
	        for (Map.Entry<Integer, String> entry : providerIdName.entrySet()) {
	            if (entry.getValue().equals(value)) {
	                return entry.getKey();
	            }
	        }
	        return 0;
	   }
}
