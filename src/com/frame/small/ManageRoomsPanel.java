package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ManageRoomsPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	private JTable roomTable;
	private JScrollPane roomScrollPane;
	private JLabel successLabel;
	private final String[] roomColumnNames = {"Type","Number","Status"};
	private String[][] roomArr = {};
	public ManageRoomsPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		JLabel lblNewLabel = new JLabel("Add new Room:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(30, 80, 199, 26);
		add(lblNewLabel);
		
	    JLabel lblNewLabel_1 = new JLabel("Room type:");
	    lblNewLabel_1.setBounds(70, 144, 97, 16);
	    add(lblNewLabel_1);
	    
		JComboBox roomTypeBox = new JComboBox(RoomType.getTypes());
		roomTypeBox.setBounds(200, 140, 161, 27);
	    add(roomTypeBox);
	    
	    JLabel lblNewLabel_2 = new JLabel("Will add additional specifications of the room ");
	    lblNewLabel_2.setBounds(70, 189, 291, 16);
	    add(lblNewLabel_2);
	    
	    JButton addRoomButton = new JButton("Add Room");
	    
	    try {
			roomArr = RoomService.getRooms();
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
        roomScrollPane.setBounds(30, 354, 650, 177);
        add(roomScrollPane);
        
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
	    addRoomButton.setBounds(62, 236, 301, 29);
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
	    
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			roomArr = RoomService.getRooms();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		roomTable.setModel(new DefaultTableModel(roomArr, roomColumnNames));
		successLabel.setText("");
	}
}
