package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.frame.Panel;
import com.models.enums.RoomType;
import com.service.ContainerService;
import com.service.DateLabelFormatter;

import javax.swing.JLabel;

import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MakeReservationPanel extends JPanel implements Panel {
	private SpringLayout springLayout;

	/**
	 * Create the panel.
	 */
	public MakeReservationPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelHeight, ContainerService.panelWidth);
		
		JLabel lblNewLabel = new JLabel("Room Type:");
		lblNewLabel.setBounds(72, 122, 92, 16);
		add(lblNewLabel);
		
		String[] roomTypes = RoomType.getTypes();
		JComboBox roomTypeBox = new JComboBox(roomTypes);
		roomTypeBox.setBounds(191, 118, 228, 27);
		add(roomTypeBox);
		
		//
		UtilDateModel model = new UtilDateModel();

	    Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
      // Don't know about the formatter, but there it is...
        JDatePickerImpl checkinDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JDatePickerImpl checkoutDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        
        checkinDatePicker.setBounds(191, 176, 228, 29);
        checkoutDatePicker.setBounds(191, 227, 228, 29);
        add(checkinDatePicker);
        add(checkoutDatePicker);
        
        JLabel lblNewLabel_1 = new JLabel("Check-in Date:");
        lblNewLabel_1.setBounds(72, 182, 103, 16);
        add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Check-out Date:");
        lblNewLabel_2.setBounds(72, 232, 118, 16);
        add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Request new Reservation:");
        lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3.setBounds(72, 58, 347, 29);
        add(lblNewLabel_3);
        
        
        JButton btnNewButton = new JButton("Request a Reservation");

        btnNewButton.setBounds(58, 295, 361, 29);
        add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
        
        makeReservationTable();
	}
	private void makeReservationTable() { //to be implemented
		   Object[][] data = {
	                {"John", 25, "Male"},
	                {"Anna", 30, "Female"},
	                {"Mike", 35, "Male"},
	                {"Emily", 28, "Female"}
	        };

	        // Column names
	        String[] columnNames = {"Name", "Age", "Gender"};

	        // Create a table
	        JTable table = new JTable(new DefaultTableModel(data, columnNames));
	        table.setForeground(new Color(0, 0, 0));
	        JScrollPane scrollPane = new JScrollPane(table);
	        
	        scrollPane.setBounds(583, 121, 400, 200);
	        add(scrollPane);
	        
	        JLabel lblNewLabel_3_1 = new JLabel("Your Reservations:");
	        lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
	        lblNewLabel_3_1.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	        lblNewLabel_3_1.setBounds(583, 58, 347, 29);
	        add(lblNewLabel_3_1);

	}
	@Override
	public void reset() {
		ContainerService.resetFields(this);
		
	}

}
