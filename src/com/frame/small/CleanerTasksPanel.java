package com.frame.small;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.frame.Panel;

import com.models.Room;
import com.service.ContainerService;
import com.service.Holder;
import com.service.RoomService;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CleanerTasksPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	private final String[] columnNames = { "Room ID","Room Type" };
	private String[][] rowArr = {{"temp","temp"}};
	private Room[] rooms;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel successLabel;
	/**
	 * Create the panel.
	 */
	public CleanerTasksPanel() {
		super();
		
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
        successLabel = new JLabel("");
        successLabel.setHorizontalAlignment(SwingConstants.CENTER);
        successLabel.setForeground(new Color(0, 183, 28));
        successLabel.setFont(new Font("KufiStandardGK", Font.PLAIN, 17));
        successLabel.setBounds(69, 20, 842, 25);
        add(successLabel);
        
		table = new JTable(rowArr, columnNames);
        table.setOpaque(true);

        table.setSize(500, 50);
        table.setLocation(50, 100);
        table.setForeground(new Color(0, 0, 0));
         scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(89, 74, 798, 200);
        add(scrollPane);
        
        JButton cleanRoomButton = new JButton("Room Cleaned");

        cleanRoomButton.setEnabled(false);
        cleanRoomButton.setBounds(89, 286, 117, 29);
        add(cleanRoomButton);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                    	cleanRoomButton.setEnabled(true);
                    }
                    else {
                    	cleanRoomButton.setEnabled(false);
                    }
                }
            }
        });
        cleanRoomButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = table.getSelectedRow();
        		Room cleanedRoom = rooms[selectedRow];
        		try {
					RoomService.cleanRoom(cleanedRoom,Holder.getInstance().getUser());
					rooms = RoomService.getCleanersRooms(Holder.getInstance().getUser());
					rowArr = setRoomData(rooms);
					table.setModel(new DefaultTableModel(rowArr, columnNames));
					successLabel.setText("Room set to cleaned!");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
	}
	
	private String[][] setRoomData(Room[] rooms) {
		String[][] ret = new String[rooms.length][];
		for(int i = 0; i < rooms.length; i++) {
			Room room = rooms[i];
			String[] row = {room.getID(),room.getType()};
			ret[i] = row;
		}
		return ret;
	}
	
	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			rooms = RoomService.getCleanersRooms(Holder.getInstance().getUser());
			rowArr = setRoomData(rooms);
			table.setModel(new DefaultTableModel(rowArr, columnNames));
			successLabel.setText("");
		} catch (IOException e) {
			/// print the error on screen
			e.printStackTrace();
		}
		
	}
}
