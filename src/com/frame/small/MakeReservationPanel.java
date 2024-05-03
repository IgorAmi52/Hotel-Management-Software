package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;

import java.awt.Component;
import java.awt.FlowLayout;

import org.jdatepicker.impl.JDatePickerImpl;
import com.frame.Panel;
import com.models.Reservation;
import com.models.enums.RoomType;
import com.service.ContainerService;
import com.service.Holder;
import com.service.PricingService;
import com.service.ReservationService;
import com.service.RoomService;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class MakeReservationPanel extends JPanel implements Panel {
	
	private JComboBox roomTypeBox;
	private String[] roomTypes = RoomType.getTypes();
	private ArrayList<JCheckBox> addCheckBoxes = new ArrayList<JCheckBox>();
	private JLabel successLabel;
	private Object[][] resData;
    private String[] columnNames = {"Room Type","Check-in", "Check-out", "Additionals","Status","Price","Comment"};
    private JTable table;
    private String[] addServiceArr;
	public MakeReservationPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		JLabel lblNewLabel = new JLabel("Room Type:");
		lblNewLabel.setBounds(484, 122, 92, 16);
		add(lblNewLabel);
		
		roomTypeBox = new JComboBox(roomTypes);
		roomTypeBox.setBounds(650, 118, 228, 27);
		add(roomTypeBox);
		
        JLabel lblNewLabel_5 = new JLabel("Additionals:");
        lblNewLabel_5.setBounds(484, 182, 92, 16);
        add(lblNewLabel_5);
        
        addServiceArr = RoomService.getAddServicesArr();
        
        for (int i = 0; i < addServiceArr.length; i++) {
        	addCheckBoxes.add(new JCheckBox(addServiceArr[i]));
        }
        JPanel checkBoxPanel = new JPanel(new GridLayout(0,1));
        for(int i = 0; i < addCheckBoxes.size(); i++) {
        	checkBoxPanel.add(addCheckBoxes.get(i));
        }
  
        checkBoxPanel.setLocation(650, 182);
        JScrollPane addScrollPane = new JScrollPane(checkBoxPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        addScrollPane.setSize(228, 80);
        addScrollPane.setLocation(650, 182);

        add(addScrollPane);
        
        
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
    
        successLabel = new JLabel("");
        successLabel.setHorizontalAlignment(SwingConstants.CENTER);
        successLabel.setForeground(new Color(0, 183, 28));
        successLabel.setFont(new Font("KufiStandardGK", Font.PLAIN, 17));
        successLabel.setBounds(191, 21, 476, 25);
        add(successLabel);
        
        JButton requestReservationButton = new JButton("Request a Reservation");
        requestReservationButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        
        requestReservationButton.setBounds(73, 258, 370, 43);
        add(requestReservationButton);
        
        requestReservationButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String checkInDate = checkinDatePicker.getJFormattedTextField().getText();
        		String checkOutDate = checkoutDatePicker.getJFormattedTextField().getText();
        		RoomType roomType = RoomType.getByAssociatedValue(roomTypeBox.getSelectedItem().toString());
        		String[] addServiceArr = getSelectedValues(addCheckBoxes);
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
	public String[] getSelectedValues(ArrayList<JCheckBox> addList) {
		  List<String> selectedValues = new ArrayList<>();
		  for (JCheckBox checkBox : addList) {
		    if (checkBox.isSelected()) {
		      selectedValues.add(checkBox.getText());
		    }
		  }
		  return selectedValues.toArray(new String[0]);
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
			addServiceArr = RoomService.getAddServicesArr();
			table.setModel(new DefaultTableModel(resData, columnNames));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		successLabel.setText("");
	}
}
