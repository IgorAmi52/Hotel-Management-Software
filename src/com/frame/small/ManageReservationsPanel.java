package com.frame.small;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.exceptions.NoRoomAvailableException;
import com.frame.Panel;
import com.models.Reservation;
import com.models.enums.ReservationStatus;
import com.service.ContainerService;
import com.service.Holder;
import com.service.ReservationService;
import com.service.RoomServiceInterface;

public class ManageReservationsPanel extends JPanel implements Panel {

	private RoomServiceInterface roomService = Holder.getInstance().getRoomService();
	private ReservationService reservationService = Holder.getInstance().getReservationService();
	private static final long serialVersionUID = 1L;
	private JTable table;
	private String[][] filteredResData;
	private Reservation[] reservations;
	private Reservation[] filteredReservations;

	private String[] columnNames = { "Room Type", "Check-in", "Check-out", "Additionals", "Status", "Price", "User" };
	private JLabel successLabel;
	private JButton acceptButton;
	private JButton rejectButton;
	private JLabel errorLabel;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JTextField fromPriceField;
	private JTextField toPriceField;

	private String[] addServiceArr = { "temp" };;
	private ArrayList<JCheckBox> addCheckBoxes = new ArrayList<JCheckBox>();
	JPanel addCheckBoxPanel;

	private String[] roomTypesArr = { "temp" };
	private ArrayList<JCheckBox> roomTypeCheckBoxes = new ArrayList<JCheckBox>();
	JPanel typeCheckBoxPanel;

	private String[] roomArr = { "temp" };;
	private ArrayList<JCheckBox> roomCheckBoxes = new ArrayList<JCheckBox>();
	JPanel roomCheckBoxPanel;

	public ManageReservationsPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);

		table = new JTable(new DefaultTableModel(filteredResData, columnNames));
		table.setForeground(new Color(0, 0, 0));
		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setBounds(69, 320, 842, 200);
		add(scrollPane);

		successLabel = new JLabel("");
		successLabel.setHorizontalAlignment(SwingConstants.CENTER);
		successLabel.setForeground(new Color(0, 183, 28));
		successLabel.setFont(new Font("KufiStandardGK", Font.PLAIN, 17));
		successLabel.setBounds(69, 20, 842, 25);
		add(successLabel);

		acceptButton = new JButton("Accept Reservation");

		acceptButton.setEnabled(false);
		acceptButton.setBounds(69, 532, 172, 29);
		add(acceptButton);

		rejectButton = new JButton("Reject Reservation");

		rejectButton.setEnabled(false);
		rejectButton.setBounds(266, 532, 172, 29);
		add(rejectButton);

		errorLabel = new JLabel("");
		errorLabel.setForeground(new Color(255, 5, 7));
		errorLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(69, 32, 842, 25);
		add(errorLabel);

		lblNewLabel = new JLabel("Filter reservations:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(69, 113, 159, 25);
		add(lblNewLabel);

		JButton filterButton = new JButton("Filter");

		filterButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		filterButton.setBounds(243, 112, 159, 29);
		add(filterButton);

		JLabel lblNewLabel_1 = new JLabel("By room type:");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(69, 173, 111, 25);
		add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("By room:");
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(266, 173, 111, 25);
		add(lblNewLabel_1_1);

		lblNewLabel_2 = new JLabel("By price:");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(708, 173, 111, 25);
		add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("By additioninal services:");
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(465, 173, 184, 25);
		add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("From:");
		lblNewLabel_4.setBounds(708, 210, 61, 16);
		add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("To:");
		lblNewLabel_5.setBounds(708, 242, 61, 16);
		add(lblNewLabel_5);

		fromPriceField = new JTextField();
		fromPriceField.setBounds(768, 205, 130, 26);
		add(fromPriceField);
		fromPriceField.setColumns(10);

		toPriceField = new JTextField();
		toPriceField.setBounds(768, 237, 130, 26);
		add(toPriceField);
		toPriceField.setColumns(10);

		addServiceArr = roomService.getAddServicesArr();

		for (int i = 0; i < addServiceArr.length; i++) {
			addCheckBoxes.add(new JCheckBox(addServiceArr[i]));
		}
		addCheckBoxPanel = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < addCheckBoxes.size(); i++) {
			addCheckBoxPanel.add(addCheckBoxes.get(i));
		}
		addCheckBoxPanel.setLocation(650, 182);
		JScrollPane addScrollPane = new JScrollPane(addCheckBoxPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		addScrollPane.setSize(172, 80);
		addScrollPane.setLocation(69, 210);
		add(addScrollPane);

		for (int i = 0; i < roomTypesArr.length; i++) {
			roomTypeCheckBoxes.add(new JCheckBox(roomTypesArr[i]));
		}
		typeCheckBoxPanel = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < roomTypeCheckBoxes.size(); i++) {
			typeCheckBoxPanel.add(roomTypeCheckBoxes.get(i));
		}
		typeCheckBoxPanel.setLocation(650, 182);
		JScrollPane typeScrollPane = new JScrollPane(typeCheckBoxPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		typeScrollPane.setSize(201, 80);
		typeScrollPane.setLocation(465, 210);
		add(typeScrollPane);

		for (int i = 0; i < roomArr.length; i++) {
			roomCheckBoxes.add(new JCheckBox(roomArr[i]));
		}
		roomCheckBoxPanel = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < roomCheckBoxes.size(); i++) {
			roomCheckBoxPanel.add(roomCheckBoxes.get(i));
		}
		roomCheckBoxPanel.setLocation(650, 182);
		JScrollPane roomScrollPane = new JScrollPane(roomCheckBoxPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		roomScrollPane.setSize(172, 80);
		roomScrollPane.setLocation(266, 210);
		add(roomScrollPane);

		filterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filterAction();
			}
		});

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
						} else {
							acceptButton.setEnabled(false);
							rejectButton.setEnabled(false);
						}

					} else {
						acceptButton.setEnabled(false);
						rejectButton.setEnabled(false);
					}
				}
			}
		});
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selectedRow = table.getSelectedRow();
				Reservation reservation = filteredReservations[selectedRow];
				try {

					reservationService.proccessReservation(reservation);
					successLabel.setText("Reservation confirmed successfully!");
					errorLabel.setText("");

					reservations = reservationService.getReservations(Holder.getInstance().getUser());
					filterAction();
				} catch (NoRoomAvailableException ex) {
					successLabel.setText("");
					errorLabel.setText(ex.getMessage());
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		});
		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				Reservation reservation = reservations[selectedRow];
				try {
					reservationService.rejectReservation(reservation, false);
					successLabel.setText("Reservation rejected!");
					errorLabel.setText("");
					reservations = reservationService.getReservations(Holder.getInstance().getUser());
					filterAction();
				}

				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	protected void filterAction() {
		double fromPrice = Double.MIN_VALUE;
		double toPrice = Double.MAX_VALUE;
		String fromPriceStr = fromPriceField.getText();
		String toPriceStr = toPriceField.getText();
		if (!fromPriceStr.isEmpty()) {
			try {
				fromPrice = Double.parseDouble(fromPriceStr);
			} catch (Exception e2) {
				errorLabel.setText("From price must be a number!");
			}
		}
		if (!toPriceStr.isEmpty()) {
			try {
				toPrice = Double.parseDouble(toPriceStr);
			} catch (Exception e2) {
				errorLabel.setText("To price must be a number!");
			}
		}
		if (fromPrice > toPrice) {
			errorLabel.setText("From price must be greater then to price!");
			return;
		}
		String[] addServices = ContainerService.getSelectedValues(addCheckBoxes);
		String[] roomIDs = ContainerService.getSelectedValues(roomCheckBoxes);
		String[] roomTypes = ContainerService.getSelectedValues(roomTypeCheckBoxes);

		Boolean addServiceFilter = true;
		Boolean roomIDFilter = true;
		Boolean roomTypesFilter = true;

		if (addServices.length == 0) {
			addServiceFilter = false;
		}
		if (roomIDs.length == 0) {
			roomIDFilter = false;
		}
		if (roomTypes.length == 0) {
			roomTypesFilter = false;
		}
		ArrayList<Reservation> filteredResArrayList = new ArrayList<Reservation>();

		for (Reservation reservation : reservations) {
			double price = reservation.getPrice();
			if (fromPrice > price || toPrice < price) {
				continue;
			}
			if (addServiceFilter) {
				String[] resAddServices = reservation.getAddServices();
				if (!haveCommonElement(addServices, resAddServices)) {
					continue;
				}
			}
			if (roomIDFilter) {
				String ID = reservation.getRoom().getID();
				if (!isInArray(roomIDs, ID)) {
					continue;
				}
			}
			if (roomTypesFilter) {
				String roomType = reservation.getRoomType();
				if (!isInArray(roomTypes, roomType)) {
					continue;
				}
			}
			filteredResArrayList.add(reservation);
		}
		filteredReservations = new Reservation[filteredResArrayList.size()];
		int i = 0;
		for (Reservation reservation : filteredResArrayList) {
			filteredReservations[i++] = reservation;
		}
		filteredResData = setData(filteredReservations);
		table.setModel(new DefaultTableModel(filteredResData, columnNames));

	}

	private String[][] setData(Reservation[] reservations) {
		String[][] ret = new String[reservations.length][];
		int i = 0;
		for (Reservation reservation : reservations) {
			String roomType = reservation.getRoomType();
			String checkInDate = reservation.getCheckInDate();
			String checkOutDate = reservation.getCheckOutDate();
			String additionals = String.join(", ", reservation.getAddServices());
			String status = reservation.getStatus();
			String price = Double.toString(reservation.getPrice());
			String user = reservation.getGuest().getUserName();

			String[] row = { roomType, checkInDate, checkOutDate, additionals, status, price, user };
			ret[i++] = row;
		}
		return ret;
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
		try {
			reservations = reservationService.getReservations(Holder.getInstance().getUser());
			filteredResData = setData(reservations);
			filteredReservations = new Reservation[reservations.length];
			for (int i = 0; i < reservations.length; i++) {
				filteredReservations[i] = reservations[i];
			}
			table.setModel(new DefaultTableModel(filteredResData, columnNames));

			addServiceArr = roomService.getAddServicesArr();
			roomTypesArr = roomService.getRoomTypes();
			roomArr = roomService.getRoomIDs();

			addCheckBoxes.clear();
			roomTypeCheckBoxes.clear();
			roomCheckBoxes.clear();

			for (int i = 0; i < addServiceArr.length; i++) {
				addCheckBoxes.add(new JCheckBox(addServiceArr[i]));
			}
			for (int i = 0; i < roomTypesArr.length; i++) {
				roomTypeCheckBoxes.add(new JCheckBox(roomTypesArr[i]));
			}
			for (int i = 0; i < roomArr.length; i++) {
				roomCheckBoxes.add(new JCheckBox(roomArr[i]));
			}
			typeCheckBoxPanel.removeAll();
			roomCheckBoxPanel.removeAll();
			addCheckBoxPanel.removeAll();

			for (int i = 0; i < addCheckBoxes.size(); i++) {
				addCheckBoxPanel.add(addCheckBoxes.get(i));
			}
			for (int i = 0; i < roomTypeCheckBoxes.size(); i++) {
				typeCheckBoxPanel.add(roomTypeCheckBoxes.get(i));
			}
			for (int i = 0; i < roomCheckBoxes.size(); i++) {
				roomCheckBoxPanel.add(roomCheckBoxes.get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		successLabel.setText("");
		errorLabel.setText("");
	}

	public static boolean haveCommonElement(String[] array1, String[] array2) {
		// Create a HashSet to store elements of the first array
		Set<String> set = new HashSet<>();
		for (String element : array1) {
			set.add(element);
		}

		// Check if any element of the second array exists in the set
		for (String element : array2) {
			if (set.contains(element)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isInArray(String[] array, String element) {
		for (String el : array) {
			if (el.equals(element)) {
				return true;
			}
		}
		return false;
	}
}
