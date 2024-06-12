package com.frame.small;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.frame.Panel;
import com.models.Staff;
import com.service.ContainerService;
import com.service.Holder;
import com.service.UserService;

public class ManageStaffPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	private final String[] columnNames = { "Username", "Name", "Lastname", "Role", "Sex", "Date of Birth",
			"Phone Number", "Address", };
	private Staff[] staffs;
	private String[][] staffArr = { { "temp" } };
	private JTable table;
	private JScrollPane scrollPane;
	private UserService userService = Holder.getInstance().getUserService();

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
					} else {
						deleteStaffButton.setEnabled(false);
					}
				}
			}
		});
		deleteStaffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				try {
					userService.deleteUser(staffs[selectedRow]);
					staffs = userService.getStaff();
					staffArr = seStaffData(staffs);
					table.setModel(new DefaultTableModel(staffArr, columnNames));
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
			staffs = userService.getStaff();
			staffArr = seStaffData(staffs);
			table.setModel(new DefaultTableModel(staffArr, columnNames));
		} catch (IOException e) {
			/// print the error on screen
			e.printStackTrace();
		}

	}

	private String[][] seStaffData(Staff[] staffs) {
		String[][] ret = new String[staffs.length][];
		int i = 0;

		for (Staff staff : staffs) {
			String username = staff.getUserName();
			String name = staff.getFirstName();
			String lastname = staff.getLastName();
			String role = staff.getRole().getRole();
			String sex = staff.getSex();
			String dateOfBirth = staff.getDateOfBirth();
			String phone = staff.getPhone();
			String address = staff.getAddress();
			String[] temp = { username, name, lastname, role, sex, dateOfBirth, phone, address };

			ret[i++] = temp;
		}
		return ret;
	}
}