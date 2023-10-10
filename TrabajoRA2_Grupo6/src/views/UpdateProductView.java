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

public class UpdateProductView extends JFrame {
	static String[] cat= {"Suit","Blouse","Shoes","Jacket","Trainers","Jeans","Shirt"};
	static String[] np;
	private static final long serialVersionUID = 1L;
	private int id;
	private JPanel contentPane;
	private JButton buttonBack,buttonUpdate;
	private JTextField name,description,price;
	private JComboBox category,providerNames;
	private String pathImage;
	private JLabel image;
	private Path finalPath;
	private static Map<Integer, String> providerIdName;
	private String savedImage;
	private ImageIcon Icon2;
	
	
	public UpdateProductView(int i) {
		setBounds(100, 100, 600, 440);
		InterfaceModel.FrameModel(this, "Products");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		Listener l=new Listener();
		contentPane.setLayout(null);
		
		id=i;
		buttonBack = new JButton("Back");
		buttonBack.setFont(new Font("Arial", Font.PLAIN, 10));
		buttonBack.setBounds(10, 346, 96, 21);
		buttonBack.addActionListener(l);
		contentPane.add(buttonBack);
		
		buttonUpdate = new JButton("Update");
		buttonUpdate.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonUpdate.setBounds(194, 290, 100, 40);
		buttonUpdate.addActionListener(l);
		contentPane.add(buttonUpdate);
		
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
		
		image = new JLabel();
		image.setBounds(356, 112, 198, 121);
		contentPane.add(image);
		image.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String filePath=bringFileChooserImage();
				ImageIcon icon = new ImageIcon(filePath);
                Image i = icon.getImage().getScaledInstance(168, 88, Image.SCALE_SMOOTH);
                ImageIcon img2 = new ImageIcon(i);
                image.setIcon(img2);
                image.setToolTipText(filePath);
			}
		});
		setProviderNames();
		fileData();
	}
	
	private void fileData() {
		for (Product p : ProductServices.selectProduct("id", id)) {
			name.setText(p.getName());
			description.setText(p.getDescription());
			price.setText(String.valueOf(p.getPrice()));
			category.setSelectedItem(p.getCategory());
			providerNames.setSelectedItem(providerIdName.get(p.getId_provider()));
				ImageIcon Icon = new ImageIcon(p.getImage());
				Image Img = Icon.getImage().getScaledInstance(168, 88, Image.SCALE_SMOOTH);
				Icon2 = new ImageIcon(Img);
			image.setIcon(Icon2);
			savedImage=p.getImage();
			pathImage=p.getImage();
		}
		
	}

	private void setProviderNames() {
		providerIdName = ProviderServices.selectProviderName(null, 0);
		
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
			}else if(o.equals(buttonUpdate)) {
				if(name.getText().length()==0) {
					JOptionPane.showMessageDialog(UpdateProductView.this, "The name cannot be empty");
				}else if(description.getText().length()==0) {
					JOptionPane.showMessageDialog(UpdateProductView.this, "The description cannot be empty");
				}else if(price.getText().length()==0) {
					JOptionPane.showMessageDialog(UpdateProductView.this, "The price cannot be empty");
				}else if(!price.getText().matches("[+-]?([0-9]*[.])?[0-9]+")) {
					JOptionPane.showMessageDialog(UpdateProductView.this, "Price only permit numbers");
				}else if(Float.valueOf(price.getText())<=0){
					JOptionPane.showMessageDialog(UpdateProductView.this, "The price cannot be 0 or less");
				}else {
					int option = JOptionPane.showConfirmDialog(UpdateProductView.this, "Are you sure you want to update this product?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if(option==JOptionPane.YES_OPTION) {
						if(ProductServices.updateProduct(id,name.getText(),description.getText(),Float.parseFloat(price.getText()),
								(String)category.getSelectedItem(),pathImage,getKeyFromValue((String)providerNames.getSelectedItem()))) {
							if(!pathImage.equals(savedImage)) {
					    		try {
									Files.copy(Paths.get(image.getToolTipText()), finalPath);
									File f=new File(String.valueOf(savedImage));
									f.delete();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					    	}
							JOptionPane.showMessageDialog(UpdateProductView.this, "Product updated successfuly");
							dispose();
							ListProductsView lpv=new ListProductsView();
							lpv.setVisible(true);
						}
					}
				}
			}
		}
		
		
	}
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
		    	if(f.exists()) {
		    		JOptionPane.showMessageDialog(null, "That name is used. Change the file name.");
					path=savedImage;
					return path;
		    	}
		    	else {
			    	finalPath=Path.of(pathImage).toAbsolutePath();
		    	}
		    	
		    }
	    }catch(Exception e) {
	    	JOptionPane.showMessageDialog(UpdateProductView.this, "Error selecting the image!");
	    }
	    
	    if(path.length()==0)
	    	path=savedImage;
	    	
	    return path;
	}
	
	 private static int getKeyFromValue(String value) {
	        for (Map.Entry<Integer, String> entry : providerIdName.entrySet()) {
	            if (entry.getValue().equals(value)) {
	                return entry.getKey();
	            }
	        }
	        return 0;
	   }
}
