package com.frame.small;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;

import com.frame.Panel;
import com.service.ContainerService;
import com.service.DateLabelFormatter;
import com.service.Holder;
import com.service.ReportsServiceInterface;

public class ReportsPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;

	private JLabel cancelledNumberLabel;
	private JLabel rejectedNumberLabel;
	private JLabel confirmedNumberLabel;

	JLabel errorLabel;
	private final String[] roomColumnNames = { "Room ID", "Used Days", "Produced Income" };
	private String[][] roomArr = {};
	private final String[] cleanerColumnNames = { "Username", "Rooms cleaned" };
	private String[][] cleanerArr = {};
	private JTable roomTable;
	private JScrollPane roomScrollPane;
	private JTable cleanerTable;
	private JScrollPane cleanerScrollPane;

	private ReportsServiceInterface reportsService = Holder.getInstance().getReportsService();

	public ReportsPanel() {
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);

		JLabel lblNewLabel_1 = new JLabel("From date:");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(38, 80, 99, 16);
		add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("To date:");
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(38, 117, 99, 16);
		add(lblNewLabel_1_1);

		JDatePickerImpl fromAddDatePicker = ContainerService.getDatePicker();
		fromAddDatePicker.setBounds(120, 75, 158, 26);
		JDatePickerImpl toAddDatePicker = ContainerService.getDatePicker();
		toAddDatePicker.setBounds(120, 112, 158, 26);
		add(fromAddDatePicker);
		add(toAddDatePicker);

		JButton getReportsButton = new JButton("Get reports");

		getReportsButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		getReportsButton.setBounds(38, 159, 265, 29);
		add(getReportsButton);

		JLabel lblNewLabel_1_2 = new JLabel("Reservations:");
		lblNewLabel_1_2.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblNewLabel_1_2.setBounds(482, 73, 143, 29);
		add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("Confirmed:");
		lblNewLabel_1_3.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNewLabel_1_3.setBounds(482, 120, 99, 16);
		add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_3_1 = new JLabel("Rejected:");
		lblNewLabel_1_3_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNewLabel_1_3_1.setBounds(482, 155, 99, 16);
		add(lblNewLabel_1_3_1);

		JLabel lblNewLabel_1_3_2 = new JLabel("Cancelled:");
		lblNewLabel_1_3_2.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNewLabel_1_3_2.setBounds(482, 190, 99, 16);
		add(lblNewLabel_1_3_2);

		confirmedNumberLabel = new JLabel("");
		confirmedNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		confirmedNumberLabel.setBounds(607, 120, 99, 16);
		add(confirmedNumberLabel);

		rejectedNumberLabel = new JLabel("");
		rejectedNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		rejectedNumberLabel.setBounds(607, 155, 99, 16);
		add(rejectedNumberLabel);

		cancelledNumberLabel = new JLabel("");
		cancelledNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		cancelledNumberLabel.setBounds(607, 190, 99, 16);
		add(cancelledNumberLabel);

		JLabel lblNewLabel_1_2_1 = new JLabel("Cleaners:");
		lblNewLabel_1_2_1.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblNewLabel_1_2_1.setBounds(38, 280, 143, 29);
		add(lblNewLabel_1_2_1);

		JLabel lblNewLabel_1_2_1_1 = new JLabel("Rooms:");
		lblNewLabel_1_2_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblNewLabel_1_2_1_1.setBounds(482, 280, 143, 29);
		add(lblNewLabel_1_2_1_1);

		roomTable = new JTable(roomArr, roomColumnNames);
		roomTable.setOpaque(true);
		roomTable.setSize(500, 50);
		roomTable.setLocation(50, 100);
		roomTable.setForeground(new Color(0, 0, 0));
		roomScrollPane = new JScrollPane(roomTable);
		roomScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		roomScrollPane.setBounds(482, 347, 391, 177);
		add(roomScrollPane);

		cleanerTable = new JTable(cleanerArr, cleanerColumnNames);
		cleanerTable.setOpaque(true);
		cleanerTable.setSize(500, 50);
		cleanerTable.setLocation(50, 100);
		cleanerTable.setForeground(new Color(0, 0, 0));
		cleanerScrollPane = new JScrollPane(cleanerTable);
		cleanerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		cleanerScrollPane.setBounds(38, 347, 391, 177);
		add(cleanerScrollPane);

		errorLabel = new JLabel("");
		errorLabel.setForeground(new Color(255, 12, 16));
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		errorLabel.setBounds(6, 6, 988, 34);
		add(errorLabel);

		getReportsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fromDate = fromAddDatePicker.getJFormattedTextField().getText();
				String toDate = toAddDatePicker.getJFormattedTextField().getText();

				if (fromDate.isEmpty() || toDate.isEmpty()) {
					errorLabel.setText("Dates cannot be empty!");
					return;
				}
				if (!DateLabelFormatter.isFirstDateGreater(toDate, fromDate)) {
					errorLabel.setText("From date needs to be greater then to date!");
					return;
				}
				errorLabel.setText("");
				try {
					int cancelledNumber = reportsService.getCancelledNumber(fromDate, toDate);
					int rejectedNumber = reportsService.getRejectedNumber(fromDate, toDate);
					int confirmedNumber = reportsService.getConfirmedNumber(fromDate, toDate);
					cancelledNumberLabel.setText(Integer.toString(cancelledNumber));
					rejectedNumberLabel.setText(Integer.toString(rejectedNumber));
					confirmedNumberLabel.setText(Integer.toString(confirmedNumber));
					roomArr = setRoomData(reportsService.getRoomsActivity(fromDate, toDate));
					cleanerArr = setCleanerData(reportsService.getCleanersActity(fromDate, toDate));

					roomTable.setModel(new DefaultTableModel(roomArr, roomColumnNames));
					cleanerTable.setModel(new DefaultTableModel(cleanerArr, cleanerColumnNames));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private String[][] setCleanerData(HashMap<String, Integer> cleanersMap) {
				String[][] ret = new String[cleanersMap.size()][];
				int i = 0;
				for (String cleaner : cleanersMap.keySet()) {
					String activity = Integer.toString(cleanersMap.get(cleaner));
					String[] temp = { cleaner, activity };
					ret[i++] = temp;
				}
				return ret;
			}

			private String[][] setRoomData(HashMap<String, double[]> roomsMap) {
				String[][] ret = new String[roomsMap.size()][];
				int i = 0;
				for (String roomID : roomsMap.keySet()) {
					String activity = Double.toString(roomsMap.get(roomID)[0]);
					String profit = Double.toString(roomsMap.get(roomID)[1]);
					String[] temp = { roomID, activity, profit };
					ret[i++] = temp;
				}
				return ret;
			}
		});
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);

		confirmedNumberLabel.setText("");
		cancelledNumberLabel.setText("");
		rejectedNumberLabel.setText("");
		errorLabel.setText("");
		roomArr = new String[0][];
		cleanerArr = new String[0][];
		roomTable.setModel(new DefaultTableModel(roomArr, roomColumnNames));
		cleanerTable.setModel(new DefaultTableModel(cleanerArr, cleanerColumnNames));
	}
}
