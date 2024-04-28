package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.frame.Panel;
import com.models.enums.RoomType;
import com.service.ContainerService;
import com.service.PricingService;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.jdatepicker.impl.JDatePickerImpl;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JButton;

public class ManagePricesPanel extends JPanel implements Panel {
	private JTextField textField;

	private final String[] bedColumnNames = { "Room Type","Price","From","To" };
	private String[][] bedArr= {{"gas","gas","gas","gas"}};
	private JTable bedTable;
	private JTable extrasTable;
	private JScrollPane bedScrollPane;
	private JScrollPane extrasScrollPane;
	
	public ManagePricesPanel() {
		
		super();
		setLayout(null);
		setSize(ContainerService.panelHeight,ContainerService.panelWidth);
		
		JLabel lblNewLabel = new JLabel("Add Room Pricing:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(70, 38, 199, 26);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(202, 135, 161, 26);
		add(textField);
		textField.setColumns(10);
		
		JComboBox roomTypeBox = new JComboBox(RoomType.getTypes());
		roomTypeBox.setBounds(202, 88, 161, 27);
	    add(roomTypeBox);
	    
	    JLabel lblNewLabel_1 = new JLabel("Room type:");
	    lblNewLabel_1.setBounds(70, 92, 97, 16);
	    add(lblNewLabel_1);
	    
	    JLabel lblNewLabel_2 = new JLabel("Daily price:");
	    lblNewLabel_2.setBounds(70, 140, 84, 16);
	    add(lblNewLabel_2);
	    
	    JLabel lblNewLabel_3 = new JLabel("From:");
	    lblNewLabel_3.setBounds(70, 188, 61, 16);
	    add(lblNewLabel_3);
	    
	    JLabel lblNewLabel_4 = new JLabel("To:");
	    lblNewLabel_4.setBounds(70, 236, 61, 16);
	    add(lblNewLabel_4);
	    
	    JDatePickerImpl fromDatePicker = ContainerService.getDatePicker();
	    fromDatePicker.setBounds(202,188,158,26);
	    JDatePickerImpl toDatePicker = ContainerService.getDatePicker();
        toDatePicker.setBounds(202,236,158,26);
        add(fromDatePicker);
        add(toDatePicker);
        
        try {
			bedArr =PricingService.getRoomPricing();  // add error message
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        bedTable = new JTable(bedArr, bedColumnNames);
        bedTable.setOpaque(true);

        bedTable.setSize(500, 50);
        bedTable.setLocation(50, 100);
        bedTable.setForeground(new Color(0, 0, 0));
        bedScrollPane = new JScrollPane(bedTable);
        bedScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bedScrollPane.setBounds(394, 88, 600, 177);
        add(bedScrollPane);
        
        JButton btnNewButton = new JButton("Delete Pricing");
        btnNewButton.setBounds(877, 40, 117, 29);
        add(btnNewButton);
        
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			bedArr = PricingService.getRoomPricing();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); // add error message
		}
	}
}
