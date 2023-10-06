package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import services.ProviderServices;

public class CreateProductView extends JFrame {
	static String[] cat= {"Suit","Blouse","Shoes","Jacket","Trainers","Jeans","Shirt"};
	static String[] np;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton buttonBack,buttonCreate;
	private JTextField name,Description,Precio;
	private JComboBox category,providerNames;
	
	
	public CreateProductView() {
		setBounds(100, 100, 490, 480);
		InterfaceModel.FrameModel(this, "Products");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		Listener l=new Listener();
		contentPane.setLayout(null);
		
		buttonBack = new JButton("Back");
		buttonBack.setFont(new Font("Arial", Font.PLAIN, 10));
		buttonBack.setBounds(10, 374, 96, 21);
		buttonBack.addActionListener(l);
		contentPane.add(buttonBack);
		
		buttonCreate = new JButton("Create");
		buttonCreate.setFont(new Font("Arial", Font.PLAIN, 14));
		buttonCreate.setBounds(160, 306, 100, 40);
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
		
		Description = new JTextField();
		Description.setFont(new Font("Arial", Font.PLAIN, 14));
		Description.setColumns(10);
		Description.setBounds(160, 93, 130, 21);
		contentPane.add(Description);
		
		Precio = new JTextField();
		Precio.setFont(new Font("Arial", Font.PLAIN, 14));
		Precio.setColumns(10);
		Precio.setBounds(160, 137, 130, 21);
		contentPane.add(Precio);
		
		category = new JComboBox(cat);
		category.setFont(new Font("Arial", Font.PLAIN, 14));
		category.setBounds(160, 180, 125, 21);
		contentPane.add(category);
		
		setProviderNames();
		
		providerNames = new JComboBox(np);
		providerNames.setFont(new Font("Arial", Font.PLAIN, 14));
		providerNames.setBounds(160, 229, 125, 21);
		contentPane.add(providerNames);
		
		
	}
	
	private void setProviderNames() {
		Map<Integer, String> providerIdName = ProviderServices.selectProviderName(null, 0);
		
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
				
			}
		}
	}
}
