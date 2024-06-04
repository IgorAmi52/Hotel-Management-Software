package com.frame.small;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.frame.Panel;
import com.models.Staff;
import com.service.ContainerService;
import com.service.UserService;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManageStaffPanel extends JPanel implements Panel {

	private final String[] columnNames = { "Username","Name", "Lastname","Role", "Sex", "Date of Birth", "Phone Number", "Address", };
	private String[][] staffArr = {{"temp"}};
	private JTable table;
	private JScrollPane scrollPane;
	
	public ManageStaffPanel() {
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
        table = new JTable(staffArr, columnNames);
        table.setOpaque(true);

        table.setSize(500, 50);
        table.setLocation(50, 100);
        table.setForeground(new Color(0, 0, 0));
         scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(6, 56, 977, 200);
        add(scrollPane);
        
        JButton deleteStaffButton = new JButton("Delete Staff");
        deleteStaffButton.setEnabled(false);
       
        deleteStaffButton.setBounds(49, 302, 317, 53);
        add(deleteStaffButton);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                    	deleteStaffButton.setEnabled(true);
                    }
                    else {
                    	deleteStaffButton.setEnabled(false);
                    }
                }
            }
        });
        deleteStaffButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String username = (String)table.getValueAt(table.getSelectedRow(), 0);
        		try {
					UserService.deleteUser(username);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		reset();
        	}
        });
 
	}
	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			staffArr = UserService.getStaff();
			table.setModel(new DefaultTableModel(staffArr, columnNames));
		} catch (IOException e) {
			/// print the error on screen
			e.printStackTrace();
		}
		
	}

}
