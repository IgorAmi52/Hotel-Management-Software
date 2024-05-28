package com.frame.small;

import javax.swing.JPanel;

import com.frame.Panel;
import com.models.User;
import com.service.ContainerService;
import com.service.Holder;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		JLabel lblNewLabel = new JLabel("Welcome "+user.getFullName());
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(62, 59, 580, 50);
		add(lblNewLabel);
		
		JButton logoutButton = new JButton("Logout");
	
		logoutButton.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		logoutButton.setBounds(50, 380, 900, 80);
		add(logoutButton);
		
		JLabel lblNewLabel_1 = new JLabel("Some information about the user and his roles");
		lblNewLabel_1.setBounds(117, 164, 567, 16);
		add(lblNewLabel_1);
		
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
