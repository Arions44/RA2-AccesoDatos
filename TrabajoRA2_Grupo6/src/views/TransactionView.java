package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.UtilDateModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import models.Trading;
import services.TradingService;

public class TransactionView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnBack, btnAdd, btnDownload, btnUpdate;
	private static JTable table;
	private static Map<Integer, Integer> mapId = new HashMap<Integer, Integer>();
	private JComboBox comboBoxFilter;
	private String[] columns = { "Product", "Provider", "Amount", "Date", "Type" };
	private String[] filter = { "Buy", "Sell", "All", "Date" };
	private JButton btnApply;
	private String selectedFilter = "";
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private JDateChooser dateChooser;
	private int row;

	public TransactionView() {
		setBounds(100, 100, 600, 340);
		InterfaceModel.FrameModel(this, "Transactions");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 53, 525, 203);
		contentPane.add(scrollPane);

		table = new JTable(UpdateTable(null, null));
		scrollPane.setViewportView(table);

		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Arial", Font.PLAIN, 10));
		btnBack.setBounds(21, 277, 68, 21);
		getContentPane().add(btnBack);

		btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Arial", Font.PLAIN, 14));
		btnAdd.setBounds(446, 266, 100, 27);
		getContentPane().add(btnAdd);

		table.setRowSelectionAllowed(true);
		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				row = table.getSelectedRow();

				if (row >= 0) {
					btnUpdate.setEnabled(true);
				} else {
					btnUpdate.setEnabled(false);
				}
			}
		});

		JLabel lblFilter = new JLabel("Filter:");
		lblFilter.setFont(new Font("Arial", Font.PLAIN, 14));
		lblFilter.setBounds(21, 29, 41, 14);
		contentPane.add(lblFilter);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(129, 25, 141, 22);
		contentPane.add(dateChooser);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateChooser.setDateFormatString("dd/MM/yyyy");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setHorizontalAlignment(JTextField.RIGHT);

		comboBoxFilter = new JComboBox(filter);
		comboBoxFilter.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBoxFilter.setBounds(60, 25, 59, 22);
		contentPane.add(comboBoxFilter);

		btnApply = new JButton("Apply");
		btnApply.setFont(new Font("Arial", Font.PLAIN, 14));
		btnApply.setBounds(280, 25, 79, 22);
		contentPane.add(btnApply);

		btnDownload = new JButton("Download Report");
		btnDownload.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDownload.setBounds(369, 25, 177, 23);
		contentPane.add(btnDownload);

		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Arial", Font.PLAIN, 14));
		btnUpdate.setBounds(332, 266, 100, 27);
		contentPane.add(btnUpdate);
		btnUpdate.setEnabled(false);

		ManejadorClass manejador = new ManejadorClass();
		btnAdd.addActionListener(manejador);
		btnBack.addActionListener(manejador);
		comboBoxFilter.addActionListener(manejador);
		btnApply.addActionListener(manejador);
		btnUpdate.addActionListener(manejador);
		btnDownload.addActionListener(manejador);

		setVisible(true);
	}

	private class ManejadorClass implements ActionListener {

		@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent e) {
			Object btn = e.getSource();

			// Controling filter ComboBox:
			if (btn == btnAdd) {
				new AddTransactionView();
				dispose();
			} else if (btn == btnBack) {
				HomeView hv = new HomeView();
				hv.setVisible(true);
				dispose();
			} else if (btn == btnUpdate) {
				UpdateTransactionView upT = new UpdateTransactionView(mapId.get(row));
				dispose();
			} else if (btn == btnDownload) {
				new DownloadReportView();
				dispose();
			} else if (btn == comboBoxFilter) {
				selectedFilter = comboBoxFilter.getSelectedItem().toString();
			} else if (btn == btnApply) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				int i = 0;
				mapId = new HashMap<Integer, Integer>();

				if (selectedFilter.equalsIgnoreCase("Buy") || selectedFilter.equalsIgnoreCase("Sell")) {
					for (Trading t : TradingService.selectTrading("type", selectedFilter)) {
						String productName = TradingService.getProductById(t.getId_product());
						String providerName = TradingService.getProviderById(t.getId_provider());
						String formattedDate = dateFormat.format(t.getDate());
						Object[] trade = { productName, providerName, t.getAmount(), formattedDate, t.getType() };
						model.addRow(trade);
						mapId.put(i, t.getId());
						i++;
					}
				} else if (selectedFilter.equalsIgnoreCase("All")) {
					for (Trading t : TradingService.selectTrading(null, selectedFilter)) {
						String productName = TradingService.getProductById(t.getId_product());
						String providerName = TradingService.getProviderById(t.getId_provider());
						String formattedDate = dateFormat.format(t.getDate());
						Object[] trade = { productName, providerName, t.getAmount(), formattedDate, t.getType() };
						model.addRow(trade);
						mapId.put(i, t.getId());
						i++;
					}
				} else if (selectedFilter.equalsIgnoreCase("Date")) {
					Date selectedDate = dateChooser.getDate();
					if (selectedDate != null) {
						String formattedDateFilter = dateFormat.format(selectedDate);
						for (Trading t : TradingService.selectTrading("date",
								new SimpleDateFormat("yyyy-MM-dd").format(selectedDate))) {
							String productName = TradingService.getProductById(t.getId_product());
							String providerName = TradingService.getProviderById(t.getId_provider());
							Date tradingDate = t.getDate();
							String formattedDate = dateFormat.format(tradingDate);
							Object[] trade = { productName, providerName, t.getAmount(), formattedDate, t.getType() };
							model.addRow(trade);
							mapId.put(i, t.getId());
							i++;
						}
					} else {
						for (Trading t : TradingService.selectTrading(null, selectedDate)) {
							String productName = TradingService.getProductById(t.getId_product());
							String providerName = TradingService.getProviderById(t.getId_provider());
							Date tradingDate = t.getDate();
							String formattedTradingDate = dateFormat.format(tradingDate);
							Object[] trade = { productName, providerName, t.getAmount(), formattedTradingDate,
									t.getType() };
							model.addRow(trade);
							mapId.put(i, t.getId());
							i++;
						}
					}

				}
			}
		}
	}

	private TableModel UpdateTable(String field, String value) {

		DefaultTableModel model = new DefaultTableModel(columns, 0);
		int i = 0;
		mapId = new HashMap<Integer, Integer>();
		for (Trading t : TradingService.selectTrading(field, value)) {
			// Here I get the product name and provider name using the methods of the class
			// TradingService
			String productName = TradingService.getProductById(t.getId_product());
			String providerName = TradingService.getProviderById(t.getId_provider());

			// Here I change the format of the date display on the table using
			// SimpleDateFormat
			String formattedDate = dateFormat.format(t.getDate());

			Object[] trade = { productName, providerName, t.getAmount(), formattedDate, t.getType() };
			model.addRow(trade);
			mapId.put(i, t.getId());
			i++;
		}

		return model;
	}
}
