package com.frame;


import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.frame.small.RegisterGuestPanel;

public class AgentMainPanel extends JTabbedPane {

	/**
	 * Create the panel.
	 */
	public AgentMainPanel() {
		Panel RegisterGuestPanel = new RegisterGuestPanel();
		addTab("Register Guest", (Component)RegisterGuestPanel);
		
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
