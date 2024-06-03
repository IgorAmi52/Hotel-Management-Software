package com.frame;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.frame.small.CleanerTasksPanel;
import com.frame.small.HomePanel;

public class CleanerMainPanel extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CleanerMainPanel() {
		
		Panel homePanel = new HomePanel();
		Panel cleanerPanel = new CleanerTasksPanel();
		
		addTab("Home", (Component) homePanel);
		addTab("Tasks", (Component) cleanerPanel);
		
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
