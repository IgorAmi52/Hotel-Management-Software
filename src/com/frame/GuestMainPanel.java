package com.frame;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.frame.small.HomePanel;
import com.frame.small.MakeReservationPanel;
import com.service.ContainerService;

public class GuestMainPanel extends JTabbedPane {


	public GuestMainPanel() {
		super();
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		Panel homePanel = new HomePanel();
		Panel reservationsPanel = new MakeReservationPanel();
		addTab("Home", (Component)homePanel);
		addTab("Reservations", (Component)reservationsPanel);
		
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
