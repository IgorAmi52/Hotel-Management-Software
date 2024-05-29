package com.frame;


import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.frame.small.CheckInOutPanel;
import com.frame.small.HomePanel;
import com.frame.small.ManageReservationsPanel;
import com.frame.small.RegisterGuestPanel;
import com.service.ContainerService;

public class AgentMainPanel extends JTabbedPane {

	/**
	 * Create the panel.
	 */
	public AgentMainPanel() {
		Panel registerGuestPanel = new RegisterGuestPanel();
		Panel homePanel = new HomePanel();
		Panel manageReservations = new ManageReservationsPanel();
		Panel checkInOut = new CheckInOutPanel();
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		addTab("Home", (Component)homePanel);
		addTab("Register Guest", (Component)registerGuestPanel);
		addTab("Manage Reservations", (Component)manageReservations);
		addTab("Todays check-in/out", (Component)checkInOut);
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
