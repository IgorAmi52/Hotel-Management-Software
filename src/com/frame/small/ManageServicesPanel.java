package com.frame.small;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import com.frame.Panel;
import com.models.Room;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Component;

public class ManageServicesPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	private JTable roomTable;
	private JTable roomTypeTable;
	private JTable addServiceTable;
	private JScrollPane roomScrollPane;
	private JScrollPane roomTypeScrollPane;
	private JScrollPane addServiceScrollPane;
	private JLabel successLabel;
	private JLabel errorLabel;
	private final String[] roomColumnNames = {"Type","Number","Status"};
	private JComboBox<String> roomTypeBox;
	private final String[] columnNames = {"Name"};
	private Room[] rooms;
	private String[][] roomArr = {};
	private String[][] roomTypeArr = {};
	private String[][] addServiceArr = {};
	private JTextField serviceNameField;
	private JTextField roomTypeField;
	public ManageServicesPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		JLabel lblNewLabel = new JLabel("Add new Room:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(32, 137, 199, 26);
		add(lblNewLabel);
		
	    JLabel lblNewLabel_1 = new JLabel("Room type:");
	    lblNewLabel_1.setBounds(30, 205, 97, 16);
	    add(lblNewLabel_1);
	    
	    
		roomTypeBox = new JComboBox<String>(new String[] {"No rooms"});
	
		roomTypeBox.setBounds(138, 201, 192, 27);
	    add(roomTypeBox);
	    
	    JButton addRoomButton = new JButton("Add Room");
	    
        roomTable = new JTable(roomArr, roomColumnNames);
        roomTable.setOpaque(true);

        roomTable.setSize(500, 50);
        roomTable.setLocation(50, 100);
        roomTable.setForeground(new Color(0, 0, 0));
        roomScrollPane = new JScrollPane(roomTable);
        roomScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        roomScrollPane.setBounds(30, 391, 391, 177);
        add(roomScrollPane);
        
	    addRoomButton.setBounds(30, 251, 301, 29);
	    add(addRoomButton);
	    
	    JButton deleteRoomButton = new JButton("Delete");

        deleteRoomButton.setEnabled(false);
        deleteRoomButton.setBounds(31, 350, 129, 29);
        add(deleteRoomButton);
        
        deleteRoomButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow =roomTable.getSelectedRow();
        		try {
					RoomService.deleteRoom(rooms[selectedRow]);
					rooms = RoomService.getRooms();
					roomArr = setRoomData(rooms);
					roomTable.setModel(new DefaultTableModel(roomArr,roomColumnNames));
					successLabel.setText("Room deleted!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        	}
        });
    
	    roomTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
                    int selectedRow = roomTable.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                    	deleteRoomButton.setEnabled(true);
                    }
                    else {
                    	deleteRoomButton.setEnabled(false);
                    }
                }
            }
        });
	    JLabel lblNewLabel_3 = new JLabel("Hotel rooms:");
	    lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	    lblNewLabel_3.setBounds(30, 298, 115, 26);
	    add(lblNewLabel_3);
	    
	    successLabel = new JLabel("");
	    successLabel.setForeground(new Color(3, 198, 6));
	    successLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    successLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
	    successLabel.setBounds(6, 26, 988, 26);
	    add(successLabel);
	    
	    JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
	    separator.setBounds(710, 119, 20, 607);

	    // Add the separator to the panel
	    add(separator);
	    
	    // service part
	    JLabel lblNewLabel_4 = new JLabel("Add new Service:");
	    lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	    lblNewLabel_4.setBounds(731, 131, 182, 38);
	    add(lblNewLabel_4);
	    
	    JLabel lblNewLabel_5 = new JLabel("Name:");
	    lblNewLabel_5.setBounds(731, 205, 61, 16);
	    add(lblNewLabel_5);
	    
	    serviceNameField = new JTextField();
	    serviceNameField.setBounds(804, 200, 190, 26);
	    add(serviceNameField);
	    serviceNameField.setColumns(10);
	    
	    JButton addAddService = new JButton("Add Service");
	
	    addAddService.setBounds(731, 251, 267, 29);
	    add(addAddService);
	    
	    JLabel lblNewLabel_6 = new JLabel("Additional Services:");
	    lblNewLabel_6.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	    lblNewLabel_6.setBounds(731, 292, 253, 38);
	    add(lblNewLabel_6);
	    
	    addServiceTable = new JTable(addServiceArr, columnNames);
	    addServiceTable.setOpaque(true);

	    addServiceTable.setSize(500, 50);
	    addServiceTable.setLocation(50, 100);
	    addServiceTable.setForeground(new Color(0, 0, 0));
	    addServiceScrollPane = new JScrollPane(addServiceTable);
	    addServiceScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    addServiceScrollPane.setBounds(727, 391, 267, 177);
        add(addServiceScrollPane);
        
        JButton deleteAddButton = new JButton("Delete");

        deleteAddButton.setEnabled(false);
        deleteAddButton.setBounds(731, 350, 129, 29);
        add(deleteAddButton);
        
        JSeparator separator_1 = new JSeparator(SwingConstants.VERTICAL);
        separator_1.setBounds(422, 119, 20, 579);
        add(separator_1);
        
        JLabel lblNewLabel_2 = new JLabel("Add new Room type:");
        lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        lblNewLabel_2.setBounds(436, 139, 233, 23);
        add(lblNewLabel_2);
        
        JLabel lblNewLabel_7 = new JLabel("Room Type:");
        lblNewLabel_7.setBounds(436, 205, 86, 16);
        add(lblNewLabel_7);
        
        roomTypeField = new JTextField();
        roomTypeField.setBounds(516, 200, 182, 26);
        add(roomTypeField);
        roomTypeField.setColumns(10);
        
        JButton addRoomTypeButton = new JButton("Add Room Type");
 
        addRoomTypeButton.setBounds(436, 251, 262, 29);
        add(addRoomTypeButton);
        
        JLabel lblNewLabel_8 = new JLabel("Room Types:");
        lblNewLabel_8.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        lblNewLabel_8.setBounds(436, 301, 140, 21);
        add(lblNewLabel_8);
        
        JButton deleteRoomTypeButton = new JButton("Delete");

        deleteRoomTypeButton.setEnabled(false);
        deleteRoomTypeButton.setBounds(436, 350, 117, 29);
        add(deleteRoomTypeButton);
        
	    roomTypeTable = new JTable(roomTypeArr, columnNames);
	    roomTypeTable.setOpaque(true);

	    roomTypeTable.setSize(500, 50);
	    roomTypeTable.setLocation(50, 100);
	    roomTypeTable.setForeground(new Color(0, 0, 0));
	    roomTypeScrollPane = new JScrollPane(roomTypeTable);
	    roomTypeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    roomTypeScrollPane.setBounds(436, 391, 267, 177);
        add(roomTypeScrollPane);
        
        errorLabel = new JLabel("");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setForeground(new Color(255, 34, 41));
        errorLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        errorLabel.setBounds(6, 26, 988, 26);
        add(errorLabel);
        
        roomTypeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { 
                    int selectedRow = roomTypeTable.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                    	deleteRoomTypeButton.setEnabled(true);
                    }
                    else {
                    	deleteRoomTypeButton.setEnabled(false);
                    }
                }
            }
        });

        addServiceTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
                    int selectedRow = addServiceTable.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                    	deleteAddButton.setEnabled(true);
                    }
                    else {
                    	deleteAddButton.setEnabled(false);
                    }
                }
            }
        });

        deleteRoomTypeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String name = (String)roomTypeTable.getValueAt(roomTypeTable.getSelectedRow(), 0);
        		try {
					RoomService.deleteRoomType(name);
					roomTypeBox.removeAllItems();
					roomTypeArr = new String[RoomService.getRoomTypes().length][];
					int i = 0;
					for(String type: RoomService.getRoomTypes()) {
						String[] temp = {type};
						roomTypeArr[i++]=temp;
						roomTypeBox.addItem(type);
					}
					roomTypeTable.setModel(new DefaultTableModel(roomTypeArr,columnNames));
					successLabel.setText("Room Type deleted!");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        deleteAddButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String name = (String)addServiceTable.getValueAt(addServiceTable.getSelectedRow(), 0);
        		try {
					RoomService.deleteAddService(name);
					addServiceArr = RoomService.getAddServices();
					addServiceTable.setModel(new DefaultTableModel(addServiceArr,columnNames));
					successLabel.setText("Service deleted!");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
	    addRoomButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String roomType = roomTypeBox.getSelectedItem().toString();
	    		try {
					int roomID = RoomService.getNextRoomID();
					Room room = new Room(roomType, roomID);
					RoomService.addRoom(room);
					ContainerService.resetFields(ManageServicesPanel.this);
					rooms = RoomService.getRooms();
					roomArr = setRoomData(rooms);
					roomTable.setModel(new DefaultTableModel(roomArr, roomColumnNames));
					successLabel.setText("New Room was successfully added!");
					//success label
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace(); // show error message
				}
	    		
	    	}
	    });
       addRoomTypeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String newType = roomTypeField.getText(); // if this empty then and if addService empty error
        		if(newType.isEmpty()) {
        			successLabel.setText("");
        			errorLabel.setText("Room type field can't be empty!");
        		}
        		else {
            		try {
    					RoomService.addRoomType(newType);
    					roomTypeBox.removeAllItems();
    					roomTypeArr = new String[RoomService.getRoomTypes().length][];
    					int i = 0;
    					for(String type: RoomService.getRoomTypes()) {
    						String[] temp = {type};
    						roomTypeArr[i++]=temp;
    						roomTypeBox.addItem(type);
    					}
    					roomTypeTable.setModel(new DefaultTableModel(roomTypeArr,columnNames));
    	        		successLabel.setText("New Room Type was successfully added!");
    	        		roomTypeField.setText("");
    				} catch (Exception e2) {
    					e2.printStackTrace();
    				}
        		}
        	}
        });
        addAddService.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String name = serviceNameField.getText();
	    		if(name.isEmpty()) {
        			successLabel.setText("");
        			errorLabel.setText("Service name field can't be empty!");
	    		}
	    		else {
	        		try {
						RoomService.addAddService(name);
						addServiceArr = RoomService.getAddServices();
						addServiceTable.setModel(new DefaultTableModel(addServiceArr,columnNames));
						successLabel.setText("New Service was successfully added!");
						serviceNameField.setText("");

					} catch (Exception e2) {
						e2.printStackTrace();
					}
	    		}
	    	}
	    });
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			roomTypeBox.removeAllItems();
			roomTypeArr = new String[RoomService.getRoomTypes().length][];
			int i = 0;
			for(String type: RoomService.getRoomTypes()) {
				String[] temp = {type};
				roomTypeArr[i++]=temp;
				roomTypeBox.addItem(type);
			}
			rooms = RoomService.getRooms();
			roomArr = setRoomData(rooms);
			addServiceArr = RoomService.getAddServices();
		} catch (IOException e) {
			e.printStackTrace();
		}
		roomTable.setModel(new DefaultTableModel(roomArr, roomColumnNames));
		roomTypeTable.setModel(new DefaultTableModel(roomTypeArr,columnNames));
		addServiceTable.setModel(new DefaultTableModel(addServiceArr,columnNames));
		successLabel.setText("");
	}

	private String[][] setRoomData(Room[] rooms) {
		String[][] ret = new String[rooms.length][];
		int i = 0;
		
		for(Room room:rooms) {
			String type =room.getType();
			String ID = room.getID();
			String status = room.getStatus();
			String[] temp = {type,ID,status};
			ret[i++] = temp;
		}
		return ret;
	}
}
