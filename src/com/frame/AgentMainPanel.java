package com.frame;


import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.frame.small.HomePanel;
import com.frame.small.RegisterGuestPanel;
import com.service.ContainerService;

public class AgentMainPanel extends JTabbedPane {

	/**
	 * Create the panel.
	 */
	public AgentMainPanel() {
		Panel registerGuestPanel = new RegisterGuestPanel();
		Panel homePanel = new HomePanel();
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		addTab("Home", (Component)homePanel);
		addTab("Register Guest", (Component)registerGuestPanel);

		addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	   Component selectedComponent = getSelectedComponent();
                   if (selectedComponent instanceof Panel) {
                       ((Panel) selectedComponent).reset();
                   }
            }
        });
	}

}
