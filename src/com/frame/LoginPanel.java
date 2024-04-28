package com.frame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.service.AuthService;
import com.service.ContainerService;
import com.service.Holder;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import com.exceptions.*;
public class LoginPanel extends JPanel implements Panel {
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel loginErrorLabel;
	
	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		super();

		setLayout(null);
		setSize(ContainerService.panelHeight, ContainerService.panelWidth);
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		usernameLabel.setBounds(30, 58, 90, 29);
		add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		passwordLabel.setBounds(30, 113, 90, 29);
		add(passwordLabel);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		usernameField.setBounds(132, 58, 439, 29);
		add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		passwordField.setBounds(132, 113, 439, 29);
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
				if(true) {
					Holder holder = Holder.getInstance();
					try {
						holder.setUser(AuthService.login(usernameField.getText(), new String(passwordField.getPassword())));

					} catch (IOException e1) {
						loginErrorLabel.setText(e1.getMessage()); //move to Auth Service
					} catch (BadLoginException e2) {
						loginErrorLabel.setText(e2.getMessage());
					}
				
				}
			}
		});

		
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
		
	}
}
