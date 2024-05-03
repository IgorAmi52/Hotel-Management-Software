package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import com.frame.Panel;
import com.models.Room;
import com.models.enums.RoomType;
import com.service.ContainerService;
import com.service.Holder;
import com.service.ReservationService;
import com.service.RoomService;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

public class ManageRoomsPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	private JTable roomTable;
	private JTable addServiceTable;
	private JScrollPane roomScrollPane;
	private JScrollPane addServiceScrollPane;
	private JLabel successLabel;
	private final String[] roomColumnNames = {"Type","Number","Status"};
	private final String[] addServiceColumnNames = {"Name"};
	private String[][] roomArr = {};
	private String[][] addServiceArr = {};
	private JTextField serviceNameField;
	public ManageRoomsPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		JLabel lblNewLabel = new JLabel("Add new Room:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(30, 76, 199, 26);
		add(lblNewLabel);
		
	    JLabel lblNewLabel_1 = new JLabel("Room type:");
	    lblNewLabel_1.setBounds(30, 144, 97, 16);
	    add(lblNewLabel_1);
	    
		JComboBox roomTypeBox = new JComboBox(RoomType.getTypes());
		roomTypeBox.setBounds(139, 140, 192, 27);
	    add(roomTypeBox);
	    
	    JLabel lblNewLabel_2 = new JLabel("Will add additional specifications of the room ");
	    lblNewLabel_2.setBounds(288, 257, 291, 16);
	    add(lblNewLabel_2);
	    
	    JButton addRoomButton = new JButton("Add Room");
	    
	    try {
			roomArr = RoomService.getRooms();
	    	addServiceArr = RoomService.getAddServices();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        roomTable = new JTable(roomArr, roomColumnNames);
        roomTable.setOpaque(true);

        roomTable.setSize(500, 50);
        roomTable.setLocation(50, 100);
        roomTable.setForeground(new Color(0, 0, 0));
        roomScrollPane = new JScrollPane(roomTable);
        roomScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        roomScrollPane.setBounds(30, 354, 614, 177);
        add(roomScrollPane);
        
	    addRoomButton.setBounds(30, 200, 301, 29);
	    add(addRoomButton);
	    
	    JLabel lblNewLabel_3 = new JLabel("Hotel rooms:");
	    lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	    lblNewLabel_3.setBounds(30, 298, 115, 26);
	    add(lblNewLabel_3);
	    
	    successLabel = new JLabel("");
	    successLabel.setForeground(new Color(3, 198, 6));
	    successLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    successLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
	    successLabel.setBounds(154, 26, 546, 16);
	    add(successLabel);
	    
	    JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
	    separator.setBounds(660, 0, 20, 650);

	    // Add the separator to the panel
	    add(separator);
	    
	    JLabel lblNewLabel_4 = new JLabel("Add new Service:");
	    lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	    lblNewLabel_4.setBounds(679, 70, 182, 38);
	    add(lblNewLabel_4);
	    
	    JLabel lblNewLabel_5 = new JLabel("Name:");
	    lblNewLabel_5.setBounds(679, 144, 61, 16);
	    add(lblNewLabel_5);
	    
	    serviceNameField = new JTextField();
	    serviceNameField.setBounds(765, 139, 177, 26);
	    add(serviceNameField);
	    serviceNameField.setColumns(10);
	    
	    JButton addAddService = new JButton("Add Service");
	
	    addAddService.setBounds(679, 200, 263, 29);
	    add(addAddService);
	    
	    JLabel lblNewLabel_6 = new JLabel("Additional Services:");
	    lblNewLabel_6.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	    lblNewLabel_6.setBounds(679, 292, 182, 38);
	    add(lblNewLabel_6);
	    
	    addServiceTable = new JTable(roomArr, roomColumnNames);
	    addServiceTable.setOpaque(true);

	    addServiceTable.setSize(500, 50);
	    addServiceTable.setLocation(50, 100);
	    addServiceTable.setForeground(new Color(0, 0, 0));
	    addServiceScrollPane = new JScrollPane(addServiceTable);
	    addServiceScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    addServiceScrollPane.setBounds(679, 354, 301, 177);
        add(addServiceScrollPane);
        
	    addRoomButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		RoomType roomType = RoomType.getByAssociatedValue(roomTypeBox.getSelectedItem().toString());
	    		try {
					int roomID = RoomService.getNextRoomID();
					Room room = new Room(roomType, roomID);
					RoomService.addRoom(room);
					ContainerService.resetFields(ManageRoomsPanel.this);
					roomArr = RoomService.getRooms();
					roomTable.setModel(new DefaultTableModel(roomArr, roomColumnNames));
					successLabel.setText("New Room was successfully added!");
					//success label
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace(); // show error message
				}
	    		
	    	}
	    });
	    
        addAddService.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String name = serviceNameField.getText();
	    		try {
					RoomService.addAddService(name);
					addServiceArr = RoomService.getAddServices();
					addServiceTable.setModel(new DefaultTableModel(addServiceArr,addServiceColumnNames));
					successLabel.setText("New Service was successfully added!");
				} catch (Exception e2) {
					// TODO: handle exception
				}
	    	}
	    });
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			roomArr = RoomService.getRooms();
			addServiceArr = RoomService.getAddServices();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		roomTable.setModel(new DefaultTableModel(roomArr, roomColumnNames));
		addServiceTable.setModel(new DefaultTableModel(addServiceArr,addServiceColumnNames));
		successLabel.setText("");
	}
}
