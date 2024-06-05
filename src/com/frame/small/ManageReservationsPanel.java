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
	private String[][] resData;
	private Reservation[] reservations;
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
        		
           	    int selectedRow = table.getSelectedRow();
        	    Reservation reservation = reservations[selectedRow];
        	    try {
     
	        	    ReservationService.proccessReservation(reservation);
	            	successLabel.setText("Reservation confirmed successfully!");
	            	errorLabel.setText("");
	            
	    			reservations = ReservationService.getReservations(Holder.getInstance().getUser());
	    			resData = setData(reservations);
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
        	    int selectedRow = table.getSelectedRow();
        	    Reservation reservation = reservations[selectedRow];
        		 try {
 	        	    ReservationService.rejectReservation(reservation,false);
 	            	successLabel.setText("Reservation rejected!");
 	            	errorLabel.setText("");
	    			reservations = ReservationService.getReservations(Holder.getInstance().getUser());
	    			resData = setData(reservations);
 	    			table.setModel(new DefaultTableModel(resData, columnNames));
 				}

         	    catch (IOException ex) {
 					ex.printStackTrace();
 				}
        	}
        });
	}
	private String[][] setData(Reservation[] reservations) {
		String[][] ret = new String[reservations.length][];
		int i = 0;
		for(Reservation reservation:reservations) {
			String roomType = reservation.getRoomType();
			String checkInDate = reservation.getCheckInDate();
			String checkOutDate = reservation.getCheckOutDate();
			String additionals = String.join(", ", reservation.getAddServices());
			String status = reservation.getStatus();
			String price = Double.toString(reservation.getPrice());
			String user = reservation.getGuest().getUserName();
			
			String[] row = {roomType,checkInDate,checkOutDate,additionals,status,price,user};
			ret[i++] = row;
		}
		return ret;
	}
	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			reservations = ReservationService.getReservations(Holder.getInstance().getUser());
			resData = setData(reservations);
			table.setModel(new DefaultTableModel(resData, columnNames));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		successLabel.setText("");		
		errorLabel.setText("");
	}

}
