package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.frame.Panel;
import com.models.Reservation;
import com.models.enums.AdditionalServiceType;
import com.models.enums.Role;
import com.models.enums.RoomType;
import com.service.ContainerService;
import com.service.DateLabelFormatter;
import com.service.Holder;
import com.service.ReservationService;

import javax.swing.JLabel;
import javax.swing.JList;

import java.util.ArrayList;
import java.util.Enumeration;
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

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MakeReservationPanel extends JPanel implements Panel {
	
	private JComboBox roomTypeBox;
	private String[] roomTypes = RoomType.getTypes();
	private JCheckBox breakfast;
	private JCheckBox lunch;
	private JCheckBox dinner;
	private JLabel successLabel;
	/**
	 * Create the panel.
	 */
	public MakeReservationPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		JLabel lblNewLabel = new JLabel("Room Type:");
		lblNewLabel.setBounds(72, 122, 92, 16);
		add(lblNewLabel);
		
		roomTypeBox = new JComboBox(roomTypes);
		roomTypeBox.setBounds(191, 118, 228, 27);
		add(roomTypeBox);

        JDatePickerImpl checkinDatePicker = ContainerService.getDatePicker();
        JDatePickerImpl checkoutDatePicker = ContainerService.getDatePicker();
        
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

        breakfast = new JCheckBox("Breakfast");
        breakfast.setSize(100, 20);
        breakfast.setLocation(64, 287);
        lunch = new JCheckBox("Lunch");
        lunch.setSize(100, 20);
        lunch.setLocation(194, 287);
        dinner = new JCheckBox("Dinner");
        dinner.setSize(100, 20);
        dinner.setLocation(319, 287);
    
        add(breakfast);
        add(lunch);
        add(dinner);
        
        successLabel = new JLabel("");
        successLabel.setHorizontalAlignment(SwingConstants.CENTER);
        successLabel.setForeground(new Color(0, 183, 28));
        successLabel.setFont(new Font("KufiStandardGK", Font.PLAIN, 17));
        successLabel.setBounds(191, 21, 476, 25);
        add(successLabel);
        
        JButton requestReservationButton = new JButton("Request a Reservation");
        
        requestReservationButton.setBounds(58, 330, 361, 29);
        add(requestReservationButton);
        
        requestReservationButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String checkInDate = checkinDatePicker.getJFormattedTextField().getText();
        		String checkOutDate = checkoutDatePicker.getJFormattedTextField().getText();
        		RoomType roomType = RoomType.getByAssociatedValue(roomTypeBox.getSelectedItem().toString());
        		AdditionalServiceType[] addServiceArr = getSelectedAddServices();
        		String guestID = Holder.getInstance().getUser().getUserName();
    
        		Reservation reservation = new Reservation(checkInDate, checkOutDate, roomType, addServiceArr,guestID);
        	
        		try {
        			ReservationService.requestReservation(reservation);
        			ContainerService.resetFields(MakeReservationPanel.this);
        			successLabel.setText("Reservation was successfully submited!");
				} catch (Exception e2) {
					e2.printStackTrace();
					// add error message;
				}
        	}
        });
        
        makeReservationTable();
	}
	
	   private AdditionalServiceType[] getSelectedAddServices() {
		   
		   int length = 0;
		   if(breakfast.isSelected()) {length++;}
		   if(lunch.isSelected()) {length++;}
		   if(dinner.isSelected()) {length++;}
		   AdditionalServiceType[] selectedValues = new AdditionalServiceType[length];
		   int i = 0;
		   if(breakfast.isSelected()) {
			   selectedValues[i] =AdditionalServiceType.getByAssociatedValue(breakfast.getText());  
			   i++;
		   }
		   if(lunch.isSelected()) {
			   selectedValues[i] =AdditionalServiceType.getByAssociatedValue(lunch.getText());  
			   i++;
		   }
		   if(dinner.isSelected()) {
			   selectedValues[i] =AdditionalServiceType.getByAssociatedValue(dinner.getText());  
			   i++;
		   }
	       return selectedValues;
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
	        
	        JLabel lblNewLabel_3_1 = new JLabel("Your Reservations: (To be inp.)");
	        lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
	        lblNewLabel_3_1.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	        lblNewLabel_3_1.setBounds(583, 58, 347, 29);
	        add(lblNewLabel_3_1);
	        
	}
	@Override
	public void reset() {
		ContainerService.resetFields(this);
		successLabel.setText("");
	}
}
