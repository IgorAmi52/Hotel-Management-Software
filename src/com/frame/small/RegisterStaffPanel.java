package com.frame.small;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePickerImpl;

import com.exceptions.ElementAlreadyExistsException;
import com.frame.Panel;
import com.models.Staff;
import com.models.enums.Role;
import com.service.AuthService;
import com.service.ContainerService;
import com.service.DataAccessImpl;

public class RegisterStaffPanel extends JPanel implements Panel {

	private AuthService authService = new AuthService(new DataAccessImpl());
	private JTextField nameField;
	private JTextField lastnameField;
	private JTextField phoneField;
	private JTextField addressField;
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField lvlOfEdctField;
	private JTextField yearsOfXpField;
	private JLabel errorLabel = new JLabel("");
	private JLabel successLabel = new JLabel("");

	/**
	 * Create the panel.
	 */
	public RegisterStaffPanel() {
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		JLabel nameLabel = new JLabel("First Name:");
		nameLabel.setBounds(84, 56, 87, 16);
		add(nameLabel);

		nameField = new JTextField();
		nameField.setBounds(193, 51, 152, 26);
		add(nameField);
		nameField.setColumns(10);

		JLabel lastnameLabel = new JLabel("Last Name:");
		lastnameLabel.setBounds(84, 102, 87, 16);
		add(lastnameLabel);

		lastnameField = new JTextField();
		lastnameField.setBounds(193, 97, 152, 26);
		add(lastnameField);
		lastnameField.setColumns(10);

		JLabel sexLabel = new JLabel("Sex:");
		sexLabel.setBounds(403, 56, 61, 16);
		add(sexLabel);

		String[] sexes = { "Male", "Female", "Never" };
		JComboBox sexBox = new JComboBox(sexes);
		sexBox.setBounds(476, 52, 119, 27);
		add(sexBox);

		JDatePickerImpl datePicker = ContainerService.getDatePicker();

		datePicker.setBounds(193, 147, 158, 26);
		;
		add(datePicker);

		JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
		dateOfBirthLabel.setBounds(84, 147, 108, 26);
		add(dateOfBirthLabel);

		JLabel lblNewLabel = new JLabel("Phone Number:");
		lblNewLabel.setBounds(84, 199, 108, 16);
		add(lblNewLabel);

		phoneField = new JTextField();
		phoneField.setBounds(193, 194, 152, 26);
		add(phoneField);
		phoneField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Address:");
		lblNewLabel_1.setBounds(84, 242, 61, 16);
		add(lblNewLabel_1);

		addressField = new JTextField();
		addressField.setBounds(193, 237, 152, 26);
		add(addressField);
		addressField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Username:");
		lblNewLabel_2.setBounds(623, 56, 94, 16);
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Password:");
		lblNewLabel_3.setBounds(620, 102, 80, 16);
		add(lblNewLabel_3);

		usernameField = new JTextField();
		usernameField.setBounds(750, 51, 158, 26);
		add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JTextField();
		passwordField.setBounds(750, 97, 158, 26);
		add(passwordField);
		passwordField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Level of Education:");
		lblNewLabel_4.setBounds(620, 147, 127, 16);
		add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Years of Experience:");
		lblNewLabel_5.setBounds(623, 199, 127, 16);
		add(lblNewLabel_5);

		lvlOfEdctField = new JTextField();
		lvlOfEdctField.setBounds(750, 142, 158, 26);
		add(lvlOfEdctField);
		lvlOfEdctField.setColumns(10);

		yearsOfXpField = new JTextField();
		yearsOfXpField.setBounds(750, 194, 158, 26);
		add(yearsOfXpField);
		yearsOfXpField.setColumns(10);

		JButton registerButton = new JButton("Register Employee");
		registerButton.setFont(new Font("Lucida Grande", Font.PLAIN, 22));

		registerButton.setBounds(50, 380, 900, 80);
		add(registerButton);

		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setForeground(new Color(255, 25, 19));
		errorLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		errorLabel.setBounds(193, 321, 634, 16);
		add(errorLabel);

		JLabel lblNewLabel_6 = new JLabel("Role:");
		lblNewLabel_6.setBounds(403, 134, 61, 16);
		add(lblNewLabel_6);

		String[] roles = { Role.AGENT.getRole(), Role.CLEANER.getRole(), Role.ADMIN.getRole() };
		JComboBox roleBox = new JComboBox(roles);
		roleBox.setBounds(476, 130, 119, 27);

		add(roleBox);

		successLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		successLabel.setForeground(new Color(9, 205, 12));
		successLabel.setHorizontalAlignment(SwingConstants.CENTER);
		successLabel.setBounds(84, 17, 825, 16);
		add(successLabel);

		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some validation of course
				int yearsOfXp;
				int lvlOfEdct;
				try {
					yearsOfXp = Integer.parseInt(yearsOfXpField.getText());
					lvlOfEdct = Integer.parseInt(lvlOfEdctField.getText());
				} catch (NumberFormatException e2) {
					errorLabel.setText("Years Of Experience and level of Education fields must be numbers!");
					return;
				}
				String name = nameField.getText();
				String lastName = lastnameField.getText();
				String sex = sexBox.getSelectedItem().toString();
				String dateOfBirth = datePicker.getJFormattedTextField().getText();
				String phone = phoneField.getText();
				String address = addressField.getText();
				String username = usernameField.getText();
				String password = passwordField.getText();
				Role role = Role.getByAssociatedValue(roleBox.getSelectedItem().toString());
				// Check if any of the fields are empty
				if (name.isEmpty() || lastName.isEmpty() || sex.isEmpty() || dateOfBirth.isEmpty() || phone.isEmpty()
						|| address.isEmpty() || username.isEmpty() || password.isEmpty()) {
					errorLabel.setText("All fields are required!");
					successLabel.setText("");
				} else {
					Staff user = new Staff(name, lastName, sex, dateOfBirth, phone, address, username, password,
							lvlOfEdct, yearsOfXp, role);
					try {
						authService.registerUser(user);
						ContainerService.resetFields(RegisterStaffPanel.this);
						errorLabel.setText("");
						successLabel.setText("New staff member was successfully registered!");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						errorLabel.setText(e1.getMessage());
					} catch (ElementAlreadyExistsException e1) {
						// TODO Auto-generated catch block
						errorLabel.setText(e1.getMessage());
						successLabel.setText("");
					}
				}
			}
		});
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
		errorLabel.setText("");
		successLabel.setText("");
	}

}
