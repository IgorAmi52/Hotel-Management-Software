package com.frame.small;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.frame.Panel;
import com.models.Room;
import com.models.enums.ReservationStatus;
import com.service.ContainerService;
import com.service.Holder;
import com.service.ReservationService;
import com.service.RoomService;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class CheckInOutPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable checkOutTable;
	private JTable CheckInTable;
	private String[][] checkInData;
	private String[][] checkOutData;
    private String[] columnNames = {"Room ID","Room Type", "User"};
	private JLabel successLabel;
	private JLabel errorLabel;

/**
	 * Create the frame.
	 */
	public CheckInOutPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
        
        successLabel = new JLabel("");
        successLabel.setHorizontalAlignment(SwingConstants.CENTER);
        successLabel.setForeground(new Color(0, 183, 28));
        successLabel.setFont(new Font("KufiStandardGK", Font.PLAIN, 17));
        successLabel.setBounds(69, 20, 842, 25);
        add(successLabel);
        
        errorLabel = new JLabel("");
        errorLabel.setForeground(new Color(255, 5, 7));
        errorLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBounds(69, 67, 842, 25);
        add(errorLabel);
        
		checkOutTable = new JTable(new DefaultTableModel(checkInData, columnNames));
		checkOutTable.setForeground(new Color(0, 0, 0));
        JScrollPane scrollPane = new JScrollPane(checkOutTable);
        
        scrollPane.setBounds(509, 135, 399, 200);
        add(scrollPane);
        
        CheckInTable = new JTable(new DefaultTableModel(checkOutData, columnNames));
        CheckInTable.setForeground(new Color(0, 0, 0));
        JScrollPane scrollPane1 = new JScrollPane(CheckInTable);
        
        scrollPane1.setBounds(69, 135, 399, 200);
        add(scrollPane1);
        
        JLabel lblNewLabel = new JLabel("Todays check-in:");
        lblNewLabel.setBounds(69, 89, 399, 24);
        add(lblNewLabel);
        
        JLabel lblTodaysCheckout = new JLabel("Todays check-out:");
        lblTodaysCheckout.setBounds(509, 89, 399, 24);
        add(lblTodaysCheckout);
        
        JButton checkInButton = new JButton("Check-in");
   
        checkInButton.setEnabled(false);
        checkInButton.setBounds(69, 347, 117, 29);
        add(checkInButton);
        
        JButton checkOutButton = new JButton("Check-out");
        
        checkOutButton.setEnabled(false);
        checkOutButton.setBounds(509, 347, 117, 29);
        add(checkOutButton);
        checkOutTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
                    int selectedRow = checkOutTable.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                    	checkOutButton.setEnabled(true);
                    }
                    else {
                    	checkOutButton.setEnabled(false);
     
                    }
                }
            }
        });
        CheckInTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
                    int selectedRow = CheckInTable.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                    	checkInButton.setEnabled(true);
                    }
                    else {
                    	checkInButton.setEnabled(false);
     
                    }
                }
            }
        });
        checkInButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = CheckInTable.getSelectedRow();
          	    int roomID = Integer.parseInt((String)CheckInTable.getValueAt(selectedRow, 0));
          	    String roomType = (String) CheckInTable.getValueAt(selectedRow, 1);
          	    Room room = new Room(roomType, roomID);
          	    try {
					RoomService.checkInRoom(room);
					checkInData = ReservationService.getTodaysCheckInReservations();
					CheckInTable.setModel(new DefaultTableModel(checkInData, columnNames));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        checkOutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
         		int selectedRow = checkOutTable.getSelectedRow();
          	    int roomID = Integer.parseInt((String)checkOutTable.getValueAt(selectedRow, 0));
          	    String roomType = (String) checkOutTable.getValueAt(selectedRow, 1);
          	    Room room = new Room(roomType, roomID);
          	    try {
					RoomService.checkOutRoom(room);
					checkOutData = ReservationService.getTodaysCheckOutReservations();
					checkOutTable.setModel(new DefaultTableModel(checkOutData, columnNames));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	
        });
	}

@Override
public void reset() {
	ContainerService.resetFields(this);
	try {
		checkInData = ReservationService.getTodaysCheckInReservations();
		CheckInTable.setModel(new DefaultTableModel(checkInData, columnNames));
		checkOutData = ReservationService.getTodaysCheckOutReservations();
		checkOutTable.setModel(new DefaultTableModel(checkOutData, columnNames));
		successLabel.setText("");
		errorLabel.setText("");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	successLabel.setText("");		
}

	
}


