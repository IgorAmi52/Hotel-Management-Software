package com.frame.small;

import javax.swing.JPanel;

import com.frame.Panel;
import com.service.ContainerService;
import javax.swing.JLabel;

public class ManageReservationsPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ManageReservationsPanel() {
		super();
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(100, 77, 257, 33);
		add(lblNewLabel);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
