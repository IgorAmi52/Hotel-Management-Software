package com.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import com.exceptions.BadLoginException;
import com.models.User;
import com.service.AuthService;
import com.service.ContainerService;
import com.service.DataAccessImpl;
import com.service.Holder;

public class LoginPanel extends JPanel implements Panel {

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel loginErrorLabel;
	private AuthService authService = new AuthService(new DataAccessImpl());

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		super();

		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		usernameLabel.setBounds(171, 146, 90, 29);
		add(usernameLabel);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		passwordLabel.setBounds(171, 220, 90, 29);
		add(passwordLabel);

		usernameField = new JTextField();
		usernameField.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		usernameField.setBounds(306, 141, 439, 39);
		add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		passwordField.setBounds(306, 215, 439, 39);
		add(passwordField);

		loginErrorLabel = new JLabel("");
		loginErrorLabel.setForeground(new Color(255, 36, 22));
		loginErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginErrorLabel.setBounds(30, 228, 908, 93);
		add(loginErrorLabel);

		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		loginButton.setBounds(50, 380, 900, 80);
		add(loginButton);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginButton.setEnabled(false);
				final String username = usernameField.getText();
				final char[] password = passwordField.getPassword();

				// Create and execute the SwingWorker
				SwingWorker<User, Void> loginWorker = new SwingWorker<User, Void>() {
					@Override
					protected User doInBackground() throws Exception {

						return authService.login(username, new String(password));
					}

					@Override
					protected void done() {
						try {
							// Get the result of the background operation
							User user = get();

							Holder.getInstance().setUser(user);
						} catch (Exception e) {
							Throwable ex = e.getCause();
							if (ex instanceof BadLoginException || ex instanceof IOException) {
								loginErrorLabel.setText(ex.getMessage());
							} else {
								ex.printStackTrace(); // Handle other exceptions
							}
							loginButton.setEnabled(true);
						}
					}
				};
				loginWorker.execute();
			}
		});
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);

	}
}
