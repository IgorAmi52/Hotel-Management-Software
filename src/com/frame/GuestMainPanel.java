package com.frame;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.service.ContainerService;

public class GuestMainPanel extends JTabbedPane {


	public GuestMainPanel() {
		super();
		setSize(ContainerService.panelHeight, ContainerService.panelWidth);
		JPanel homePanel = new HomePanel();
		
		addTab("Home", homePanel);
	}

}
