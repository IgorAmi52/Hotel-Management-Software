package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import com.exceptions.ElementAlreadyExistsException;
import com.frame.Panel;
import com.models.Pricing;

import com.models.enums.Role;

import com.service.ContainerService;
import com.service.PricingService;
import com.service.RoomService;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManagePricesPanel extends JPanel implements Panel {
	
	private JLabel successLabel;
	private JLabel erroLabel;
	private JTextField roomPriceLabel;
	private JTextField addPriceLabel;
	private final String[] bedColumnNames = { "Room Type","Price","From","To" };
	private final String[] addColumnNames = {"Service Type","Price","From","To"};
	private String[][] bedArr= {};
	private Pricing[] bedPricings;
	private String[][] addArr = {};
	private Pricing[] addPricings;
	private JTable bedTable;
	private JTable extrasTable;
	private JScrollPane bedScrollPane;
	private JScrollPane extrasScrollPane;
	private JComboBox<String> addTypeBox;
	private JComboBox<String> roomTypeBox;
	public ManagePricesPanel() {
		
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
	    successLabel = new JLabel("");
	    successLabel.setForeground(new Color(3, 198, 6));
	    successLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    successLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	    successLabel.setBounds(0, 6, 988, 16);
	    add(successLabel);
	    
		roomPricingDiv();
		addPricingDiv();
	}
	private void addPricingDiv() {
		JLabel lblNewLabel = new JLabel("Add Additional Service Pricing:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(17, 292, 266, 26);
		add(lblNewLabel);
		
	    JLabel lblNewLabel_1 = new JLabel("Service type:");
	    lblNewLabel_1.setBounds(70, 330, 97, 16);
	    add(lblNewLabel_1);

		addTypeBox = new JComboBox<String>(RoomService.getAddServicesArr());
		addTypeBox.setBounds(202, 330, 161, 27);
	    add(addTypeBox);
	    
	    JLabel lblNewLabel_2 = new JLabel("Daily price:");
	    lblNewLabel_2.setBounds(70, 370, 84, 16);
	    add(lblNewLabel_2);
	    
	    addPriceLabel = new JTextField();
	    addPriceLabel.setBounds(202, 365, 161, 26);
		add(addPriceLabel);
		addPriceLabel.setColumns(10);
		
	    
	    JLabel lblNewLabel_3 = new JLabel("From:");
	    lblNewLabel_3.setBounds(70, 410, 61, 16);
	    add(lblNewLabel_3);
	    
	    JLabel lblNewLabel_4 = new JLabel("To:");
	    lblNewLabel_4.setBounds(70, 450, 61, 16);
	    add(lblNewLabel_4);
	    
	    JDatePickerImpl fromAddDatePicker = ContainerService.getDatePicker();
	    fromAddDatePicker.setBounds(202,410,158,26);
	    JDatePickerImpl toAddDatePicker = ContainerService.getDatePicker();
	    toAddDatePicker.setBounds(202,450,158,26);
        add(fromAddDatePicker);
        add(toAddDatePicker);
        
   
        extrasTable = new JTable(addArr, addColumnNames);
        extrasTable.setOpaque(true);

        extrasTable.setSize(500, 50);
        extrasTable.setLocation(50, 100);
        extrasTable.setForeground(new Color(0, 0, 0));
        extrasScrollPane = new JScrollPane(extrasTable);
        extrasScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        extrasScrollPane.setBounds(388, 333, 600, 177);
        add(extrasScrollPane);
        
        JButton addAddPriceButton = new JButton("Add Pricing");
        addAddPriceButton.setBounds(70, 490, 306, 29);
        add(addAddPriceButton);
        
        JButton deleteAddPricingButton = new JButton("Delete Pricing");

        deleteAddPricingButton.setEnabled(false);
        deleteAddPricingButton.setBounds(877, 295, 117, 29);
        add(deleteAddPricingButton);
        
        erroLabel = new JLabel("");
        erroLabel.setHorizontalAlignment(SwingConstants.CENTER);
        erroLabel.setForeground(new Color(255, 6, 22));
        erroLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        erroLabel.setBounds(0, 12, 988, 16);
        add(erroLabel);
        
        addAddPriceButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//validation 
        		Double price;
				try {
					price = Double.parseDouble(addPriceLabel.getText());
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				}
				String serviceType = (addTypeBox.getSelectedItem().toString());
				String fromDate = fromAddDatePicker.getJFormattedTextField().getText();
				String toDate = toAddDatePicker.getJFormattedTextField().getText();
				
        		Pricing pricing = new Pricing(serviceType,price,fromDate,toDate);
        		try {
        			ContainerService.resetFields(ManagePricesPanel.this);
        			PricingService.addPricing(pricing);
					addPricings = PricingService.getPricing(false);  
					addArr = setData(addPricings);
        			extrasTable.setModel(new DefaultTableModel(addArr, addColumnNames));
        			successLabel.setText("Service price successfully added!");
        			erroLabel.setText("");
				} catch (IOException e2) {
					e2.printStackTrace();
				} catch (ElementAlreadyExistsException e1) {
					successLabel.setText("");
					erroLabel.setText(e1.getMessage());	
				}
        	}
        });
        extrasTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
                    int selectedRow = extrasTable.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                    	deleteAddPricingButton.setEnabled(true);
                    }
                    else {
                    	deleteAddPricingButton.setEnabled(false);
                    }
                }
            }
        });
        deleteAddPricingButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String type = (String)extrasTable.getValueAt(extrasTable.getSelectedRow(), 0);
        		Double price = Double.parseDouble((String)extrasTable.getValueAt(extrasTable.getSelectedRow(), 1));
        		String from = (String)extrasTable.getValueAt(extrasTable.getSelectedRow(), 2);
        		String to = (String)extrasTable.getValueAt(extrasTable.getSelectedRow(), 3);
        		Pricing pricing = new Pricing(type, price, from, to);
        		try {
					PricingService.deletePricing(pricing);
					addPricings = PricingService.getPricing(false);  
					addArr = setData(addPricings);
					extrasTable.setModel(new DefaultTableModel(addArr, addColumnNames));
					successLabel.setText("Extras Pricing succesfully deleted!");
					erroLabel.setText("");
				} catch (Exception e2) {
					// TODO: handle exception
				}
        	}
        });
	}
	private void roomPricingDiv() {
		JLabel lblNewLabel = new JLabel("Add Room Pricing:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(20, 32, 199, 26);
		add(lblNewLabel);
		
	
	
	    JLabel lblNewLabel_1 = new JLabel("Room type:");
	    lblNewLabel_1.setBounds(70, 70, 97, 16);
	    add(lblNewLabel_1);

	    roomTypeBox = new JComboBox<String>(new String[] {"No rooms"});

		roomTypeBox.setBounds(202, 70, 161, 27);
	    add(roomTypeBox);
	    
	    JLabel lblNewLabel_2 = new JLabel("Daily price:");
	    lblNewLabel_2.setBounds(70, 110, 84, 16);
	    add(lblNewLabel_2);
	    
		roomPriceLabel = new JTextField();
		roomPriceLabel.setBounds(202, 105, 161, 26);
		add(roomPriceLabel);
		roomPriceLabel.setColumns(10);
		
	    
	    JLabel lblNewLabel_3 = new JLabel("From:");
	    lblNewLabel_3.setBounds(70, 150, 61, 16);
	    add(lblNewLabel_3);
	    
	    JLabel lblNewLabel_4 = new JLabel("To:");
	    lblNewLabel_4.setBounds(70, 190, 61, 16);
	    add(lblNewLabel_4);
	    
	    JDatePickerImpl fromRoomDatePicker = ContainerService.getDatePicker();
	    fromRoomDatePicker.setBounds(202,150,158,26);
	    JDatePickerImpl toRoomDatePicker = ContainerService.getDatePicker();
	    toRoomDatePicker.setBounds(202,190,158,26);
        add(fromRoomDatePicker);
        add(toRoomDatePicker);
        

        bedTable = new JTable(bedArr, bedColumnNames);
        bedTable.setOpaque(true);

        bedTable.setSize(500, 50);
        bedTable.setLocation(50, 100);
        bedTable.setForeground(new Color(0, 0, 0));
        bedScrollPane = new JScrollPane(bedTable);
        bedScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bedScrollPane.setBounds(388, 73, 600, 177);
        add(bedScrollPane);
        
        JButton deleteRoomPricingButton = new JButton("Delete Pricing");
        deleteRoomPricingButton.setEnabled(false);

        deleteRoomPricingButton.setBounds(877, 40, 117, 29);
        add(deleteRoomPricingButton);
        
        JButton addRoomPriceButton = new JButton("Add Pricing");
        addRoomPriceButton.setBounds(70, 236, 306, 29);
        add(addRoomPriceButton);
        
        addRoomPriceButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//validation 
        		Double price;
				try {
					price = Double.parseDouble(roomPriceLabel.getText());
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				}
				String serviceType = (roomTypeBox.getSelectedItem().toString());
				String fromDate = fromRoomDatePicker.getJFormattedTextField().getText();
				String toDate = toRoomDatePicker.getJFormattedTextField().getText();
        		Pricing pricing = new Pricing(serviceType,price,fromDate,toDate);
        		
        		try {
        			PricingService.addPricing(pricing);
        			ContainerService.resetFields(ManagePricesPanel.this);
					bedPricings = PricingService.getPricing(true);  
					bedArr = setData(bedPricings);
        			bedTable.setModel(new DefaultTableModel(bedArr, bedColumnNames));
        			successLabel.setText("Bed price successfully added!");
        			erroLabel.setText("");
				} catch (IOException e2) {
					e2.printStackTrace();
				} catch (ElementAlreadyExistsException e1) {
					successLabel.setText("");
					erroLabel.setText(e1.getMessage());	
				}

        	}
        });
        bedTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
                    int selectedRow = bedTable.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                    	deleteRoomPricingButton.setEnabled(true);
                    }
                    else {
                    	deleteRoomPricingButton.setEnabled(false);
                    }
                }
            }
        });
        deleteRoomPricingButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String type = (String)bedTable.getValueAt(bedTable.getSelectedRow(), 0);
        		Double price = Double.parseDouble((String)bedTable.getValueAt(bedTable.getSelectedRow(), 1));
        		String from = (String)bedTable.getValueAt(bedTable.getSelectedRow(), 2);
        		String to = (String)bedTable.getValueAt(bedTable.getSelectedRow(), 3);
        		Pricing pricing = new Pricing(type, price, from, to);
        		try {
					PricingService.deletePricing(pricing);
					bedPricings = PricingService.getPricing(true);  
					bedArr = setData(bedPricings);
					bedTable.setModel(new DefaultTableModel(bedArr, bedColumnNames));
					successLabel.setText("Bed Pricing succesfully deleted!");
					erroLabel.setText("");
				} catch (Exception e2) {
					// TODO: handle exception
				}
        	}
        });
	}
	private String[][] setData(Pricing[] pricings) {
		// TODO Auto-generated method stub
		String[][] ret = new String[pricings.length][];
		
		int i = 0;
		
		for(Pricing pricing:pricings) {
			String type = pricing.getType();
			String price = Double.toString(pricing.getPrice());
			String fromDate = pricing.getFromDate();
			String toDate = pricing.getToDate();
			
			String[] temp = {type,price,fromDate,toDate};
			ret[i++] = temp;
		}
		
		return ret;
	}
	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			bedPricings = PricingService.getPricing(true);
			bedArr = setData(bedPricings);
			bedTable.setModel(new DefaultTableModel(bedArr, bedColumnNames));
			
			addPricings = PricingService.getPricing(false);
			addArr = setData(addPricings);
			extrasTable.setModel(new DefaultTableModel(addArr, addColumnNames));
			
			addTypeBox.removeAllItems();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(RoomService.getAddServicesArr());
			addTypeBox.setModel(model);
	    	roomTypeBox.removeAllItems();
			for(String type: RoomService.getRoomTypesArr()) {
				roomTypeBox.addItem(type);
			}
			successLabel.setText("");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); // add error message
		}
	}
}
