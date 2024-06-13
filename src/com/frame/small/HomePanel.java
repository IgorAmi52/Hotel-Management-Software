package com.frame.small;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.frame.Panel;
import com.models.User;
import com.service.ContainerService;
import com.service.Holder;

public class HomePanel extends JPanel implements Panel {

	/**
	 * Create the panel.
	 */
	public HomePanel() {
		super();

		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);

		Holder holder = Holder.getInstance();
		User user = holder.getUser();
		JLabel lblNewLabel = new JLabel("Welcome " + user.getFullName());
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 34));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 92, 888, 89);
		add(lblNewLabel);

		JButton logoutButton = new JButton("Logout");

		logoutButton.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		logoutButton.setBounds(50, 380, 900, 80);
		add(logoutButton);

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				holder.setUser(null);
			}
		});

	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
	}
}
