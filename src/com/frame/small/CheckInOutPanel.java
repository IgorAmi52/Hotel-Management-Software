package com.frame.small;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.exceptions.NoPricingException;
import com.frame.Panel;
import com.models.Guest;
import com.models.Reservation;
import com.models.Room;
import com.service.ContainerService;
import com.service.Holder;
import com.service.ReservationService;
import com.service.RoomServiceInterface;

public class CheckInOutPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	private RoomServiceInterface roomService = Holder.getInstance().getRoomService();
	private ReservationService reservationService = Holder.getInstance().getReservationService();
	private JTable checkOutTable;
	private JTable CheckInTable;
	private Reservation[] checkInReservations;
	private String[][] checkInData;
	private Reservation[] checkOutReservations;
	private String[][] checkOutData;
	private String[] columnNames = { "Room ID", "Room Type", "User" };
	private String[] addServiceArr;
	private ArrayList<JCheckBox> addCheckBoxes = new ArrayList<JCheckBox>();
	private JLabel successLabel;
	private JLabel errorLabel;
	private JScrollPane addScrollPane;
	private JPanel checkBoxPanel;

	/**
	 * Create the frame.
	 */
	public CheckInOutPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);

		successLabel = new JLabel("");
		successLabel.setHorizontalAlignment(SwingConstants.CENTER);
		successLabel.setForeground(new Color(0, 183, 28));
		successLabel.setFont(new Font("KufiStandardGK", Font.PLAIN, 17));
		successLabel.setBounds(69, 20, 842, 25);
		add(successLabel);

		errorLabel = new JLabel("");
		errorLabel.setForeground(new Color(255, 5, 7));
		errorLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(69, 67, 842, 25);
		add(errorLabel);

		checkOutTable = new JTable(new DefaultTableModel(checkInData, columnNames));
		checkOutTable.setForeground(new Color(0, 0, 0));
		JScrollPane scrollPane = new JScrollPane(checkOutTable);

		scrollPane.setBounds(509, 135, 399, 200);
		add(scrollPane);

		CheckInTable = new JTable(new DefaultTableModel(checkOutData, columnNames));
		CheckInTable.setForeground(new Color(0, 0, 0));
		JScrollPane scrollPane1 = new JScrollPane(CheckInTable);

		scrollPane1.setBounds(69, 135, 399, 200);
		add(scrollPane1);

		JLabel lblNewLabel = new JLabel("Todays check-in:");
		lblNewLabel.setBounds(69, 89, 399, 24);
		add(lblNewLabel);

		JLabel lblTodaysCheckout = new JLabel("Todays check-out:");
		lblTodaysCheckout.setBounds(509, 89, 399, 24);
		add(lblTodaysCheckout);

		JButton checkInButton = new JButton("Check-in");

		checkInButton.setEnabled(false);
		checkInButton.setBounds(69, 347, 117, 29);
		add(checkInButton);

		JButton checkOutButton = new JButton("Check-out");

		checkOutButton.setEnabled(false);
		checkOutButton.setBounds(509, 347, 117, 29);
		add(checkOutButton);

		checkBoxPanel = new JPanel(new GridLayout(0, 1));
		checkBoxPanel.setLocation(200, 347);

		addScrollPane = new JScrollPane(checkBoxPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		addScrollPane.setSize(228, 80);
		addScrollPane.setLocation(200, 347);
		add(addScrollPane);

		CheckInTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
					int selectedRow = CheckInTable.getSelectedRow();
					if (selectedRow != -1) { // If a row is selected
						checkInButton.setEnabled(true);

						String[] allAddServiceArr = roomService.getAddServicesArr();
						String[] selectedAddServicesArr = checkInReservations[selectedRow].getAddServices();
						Set<String> set2 = new HashSet<>(Arrays.asList(selectedAddServicesArr));

						// Create a list to store the result
						List<String> result = new ArrayList<>();

						// Iterate through the first array
						for (String element : allAddServiceArr) {
							// Add element to result if it's not in set2
							if (!set2.contains(element)) {
								result.add(element);
							}
						}
						addServiceArr = result.toArray(new String[0]);

						while (!addCheckBoxes.isEmpty()) {
							addCheckBoxes.remove(0);
						}

						for (int i = 0; i < addServiceArr.length; i++) {
							addCheckBoxes.add(new JCheckBox(addServiceArr[i]));
						}
						checkBoxPanel.removeAll();
						for (int i = 0; i < addCheckBoxes.size(); i++) {
							checkBoxPanel.add(addCheckBoxes.get(i));
						}
						addScrollPane.setViewportView(checkBoxPanel);
					} else {
						checkInButton.setEnabled(false);
					}
				}
			}
		});
		checkInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = CheckInTable.getSelectedRow();

				Room room = checkInReservations[selectedRow].getRoom();
				String[] addServiceArr = ContainerService.getSelectedValues(addCheckBoxes);
				try {

					roomService.checkInRoom(room);
					reservationService.checkInReservation(checkInReservations[selectedRow], addServiceArr);

					String price = Double.toString(checkInReservations[selectedRow].getPrice());
					checkInReservations = reservationService.getTodaysCheckInReservations();
					checkInData = setReservationsData(checkInReservations);
					CheckInTable.setModel(new DefaultTableModel(checkInData, columnNames));

					checkOutReservations = reservationService.getTodaysCheckOutReservations();
					checkOutData = setReservationsData(checkOutReservations);
					checkOutTable.setModel(new DefaultTableModel(checkOutData, columnNames));

					successLabel.setText("User checked-in successfully! Total price " + price + ".");
					errorLabel.setText("");

					checkBoxPanel.removeAll();
					addScrollPane.setViewportView(checkBoxPanel);

				} catch (NoPricingException e1) {
					successLabel.setText("");
					errorLabel.setText(e1.getMessage());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		checkOutTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // To prevent multiple events when selection is still being adjusted
					int selectedRow = checkOutTable.getSelectedRow();
					if (selectedRow != -1) { // If a row is selected
						checkOutButton.setEnabled(true);
					} else {
						checkOutButton.setEnabled(false);

					}
				}
			}
		});

		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = checkOutTable.getSelectedRow();
				Room room = checkOutReservations[selectedRow].getRoom();
				try {
					reservationService.archiveReservation(checkOutReservations[selectedRow]);
					roomService.checkOutRoom(room);
					checkOutReservations = reservationService.getTodaysCheckOutReservations();
					checkOutData = setReservationsData(checkOutReservations);
					checkOutTable.setModel(new DefaultTableModel(checkOutData, columnNames));
					successLabel.setText("User checked-out successfully!");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}

	private String[][] setReservationsData(Reservation[] reservations) {
		String[][] ret = new String[reservations.length][];
		for (int i = 0; i < reservations.length; i++) {
			Room room = reservations[i].getRoom();
			Guest guest = reservations[i].getGuest();
			String[] row = { room.getID(), room.getType(), guest.getUserName() };
			ret[i] = row;
		}
		return ret;
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			checkBoxPanel.removeAll();
			addScrollPane.setViewportView(checkBoxPanel);

			checkInReservations = reservationService.getTodaysCheckInReservations();
			checkInData = setReservationsData(checkInReservations);
			CheckInTable.setModel(new DefaultTableModel(checkInData, columnNames));

			checkOutReservations = reservationService.getTodaysCheckOutReservations();
			checkOutData = setReservationsData(checkOutReservations);
			checkOutTable.setModel(new DefaultTableModel(checkOutData, columnNames));

			checkBoxPanel.removeAll();

			successLabel.setText("");
			errorLabel.setText("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		successLabel.setText("");
	}

}
