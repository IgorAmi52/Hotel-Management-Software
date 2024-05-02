package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.jdatepicker.impl.JDatePickerImpl;
import com.frame.Panel;
import com.models.Reservation;
import com.models.enums.AdditionalServiceType;
import com.models.enums.RoomType;
import com.service.ContainerService;
import com.service.Holder;
import com.service.PricingService;
import com.service.ReservationService;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MakeReservationPanel extends JPanel implements Panel {
	
	private JComboBox roomTypeBox;
	private String[] roomTypes = RoomType.getTypes();
	private JCheckBox breakfast;
	private JCheckBox lunch;
	private JCheckBox dinner;
	private JLabel successLabel;
	private Object[][] resData;
    private String[] columnNames = {"Room Type","Check-in", "Check-out", "Additionals","Status","Price","Comment"};
    private JTable table;
    
	public MakeReservationPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		JLabel lblNewLabel = new JLabel("Room Type:");
		lblNewLabel.setBounds(484, 122, 92, 16);
		add(lblNewLabel);
		
		roomTypeBox = new JComboBox(roomTypes);
		roomTypeBox.setBounds(652, 118, 228, 27);
		add(roomTypeBox);

        JDatePickerImpl checkinDatePicker = ContainerService.getDatePicker();
        JDatePickerImpl checkoutDatePicker = ContainerService.getDatePicker();
        
        checkinDatePicker.setBounds(191, 122, 228, 29);
        checkoutDatePicker.setBounds(191, 182, 228, 29);
        add(checkinDatePicker);
        add(checkoutDatePicker);
        
        JLabel lblNewLabel_1 = new JLabel("Check-in Date:");
        lblNewLabel_1.setBounds(73, 122, 103, 16);
        add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Check-out Date:");
        lblNewLabel_2.setBounds(73, 182, 118, 16);
        add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Request new Reservation:");
        lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_3.setBounds(72, 58, 347, 29);
        add(lblNewLabel_3);

        breakfast = new JCheckBox("Breakfast");
        breakfast.setSize(100, 20);
        breakfast.setLocation(497, 180);
        lunch = new JCheckBox("Lunch");
        lunch.setSize(100, 20);
        lunch.setLocation(663, 180);
        dinner = new JCheckBox("Dinner");
        dinner.setSize(100, 20);
        dinner.setLocation(801, 180);
    
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
        requestReservationButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        
        requestReservationButton.setBounds(73, 258, 428, 43);
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
        			resData = ReservationService.getReservationsGuest(Holder.getInstance().getUser());
        			table.setModel(new DefaultTableModel(resData, columnNames));
        			successLabel.setText("");
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
		
		    try {
				resData = ReservationService.getReservationsGuest(Holder.getInstance().getUser());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        table = new JTable(new DefaultTableModel(resData, columnNames));
	        table.setForeground(new Color(0, 0, 0));
	        JScrollPane scrollPane = new JScrollPane(table);
	        
	        scrollPane.setBounds(73, 392, 807, 176);
	        add(scrollPane);
	        
	        JLabel lblNewLabel_4 = new JLabel("Your Reservations:");
	        lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	        lblNewLabel_4.setBounds(73, 331, 221, 30);
	        add(lblNewLabel_4);
	        
	}
	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			resData = ReservationService.getReservationsGuest(Holder.getInstance().getUser());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setModel(new DefaultTableModel(resData, columnNames));
		successLabel.setText("");
	}
}
