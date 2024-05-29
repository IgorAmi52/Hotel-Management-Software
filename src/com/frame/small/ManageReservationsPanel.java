package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.exceptions.NoRoomAvailableException;
import com.frame.Panel;
import com.models.Guest;
import com.models.Reservation;
import com.models.enums.ReservationStatus;
import com.service.ContainerService;
import com.service.Holder;
import com.service.ReservationService;
import com.service.RoomService;
import com.service.UserService;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManageReservationsPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
    private JTable table;
	private Object[][] resData;
    private String[] columnNames = {"Room Type","Check-in", "Check-out", "Additionals","Status","Price","User"};
	private JLabel successLabel;
	private JButton acceptButton;
	private JButton rejectButton;
	private JLabel errorLabel;
	/**
	 * Create the panel.
	 */
	public ManageReservationsPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
        table = new JTable(new DefaultTableModel(resData, columnNames));
        table.setForeground(new Color(0, 0, 0));
        JScrollPane scrollPane = new JScrollPane(table);
        
        scrollPane.setBounds(69, 135, 842, 200);
        add(scrollPane);
        
        successLabel = new JLabel("");
        successLabel.setHorizontalAlignment(SwingConstants.CENTER);
        successLabel.setForeground(new Color(0, 183, 28));
        successLabel.setFont(new Font("KufiStandardGK", Font.PLAIN, 17));
        successLabel.setBounds(69, 20, 842, 25);
        add(successLabel);
        
        acceptButton = new JButton("Accept Reservation");

        acceptButton.setEnabled(false);
        acceptButton.setBounds(69, 383, 172, 29);
        add(acceptButton);
        
        rejectButton = new JButton("Reject Reservation");

        rejectButton.setEnabled(false);
        rejectButton.setBounds(268, 383, 172, 29);
        add(rejectButton);
        
        errorLabel = new JLabel("");
        errorLabel.setForeground(new Color(255, 5, 7));
        errorLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBounds(69, 67, 842, 25);
        add(errorLabel);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                      	String status = (String) table.getValueAt(selectedRow, 4);
                      	if (status.equals(ReservationStatus.PENDING.getStatus())) {
                    		acceptButton.setEnabled(true);
                    		rejectButton.setEnabled(true);
                      	}

                    }
                    else {
                    	acceptButton.setEnabled(false);
                    	rejectButton.setEnabled(false);
                    }
                }
            }
        });
        acceptButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {

        	    try {
            	    int selectedRow = table.getSelectedRow();
            	    String roomType = (String) table.getValueAt(selectedRow, 0);
            	    String checkInDate = (String) table.getValueAt(selectedRow, 1);
            	    String checkOutDate = (String) table.getValueAt(selectedRow, 2);
            	    String[] addServices;
            	    if (((String) table.getValueAt(selectedRow, 3)).equals("")) {
            	    	addServices = new String[0];
            	    }
            	    else {
            	    	addServices = ((String) table.getValueAt(selectedRow, 3)).split(", ");
            	    }
            	    String username = (String) table.getValueAt(selectedRow, 6); 
					Guest guest =UserService.getGuest(username);
	        	    Reservation reservation = new Reservation(checkInDate, checkOutDate, roomType, addServices, guest);
	        	    
	        	    ReservationService.proccessReservation(reservation);
	            	successLabel.setText("Reservation confirmed successfully!");
	            	errorLabel.setText("");
	    			resData = ReservationService.getReservations(Holder.getInstance().getUser());
	    			table.setModel(new DefaultTableModel(resData, columnNames));
				}
        	    catch (NoRoomAvailableException ex) {
        	    	successLabel.setText("");
        	    	errorLabel.setText(ex.getMessage());
				}
        	    catch (IOException ex) {
					ex.printStackTrace();
				}

        	}
        });
        rejectButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 try {
             	    int selectedRow = table.getSelectedRow();
             	    String roomType = (String) table.getValueAt(selectedRow, 0);
             	    String checkInDate = (String) table.getValueAt(selectedRow, 1);
             	    String checkOutDate = (String) table.getValueAt(selectedRow, 2);
             	    String[] addServices;
             	    if (((String) table.getValueAt(selectedRow, 3)).equals("")) {
             	    	addServices = new String[0];
             	    }
             	    else {
             	    	addServices = ((String) table.getValueAt(selectedRow, 3)).split(", ");
             	    }
             	    String username = (String) table.getValueAt(selectedRow, 6); 
 					Guest guest =UserService.getGuest(username);
 	        	    Reservation reservation = new Reservation(checkInDate, checkOutDate, roomType, addServices, guest);
 	        	    
 	        	    ReservationService.rejectReservation(reservation);
 	            	successLabel.setText("Reservation rejected!");
 	            	errorLabel.setText("");
 	    			resData = ReservationService.getReservations(Holder.getInstance().getUser());
 	    			table.setModel(new DefaultTableModel(resData, columnNames));
 				}

         	    catch (IOException ex) {
 					ex.printStackTrace();
 				}
        	}
        });
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			resData = ReservationService.getReservations(Holder.getInstance().getUser());
			table.setModel(new DefaultTableModel(resData, columnNames));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		successLabel.setText("");		
	}

}
