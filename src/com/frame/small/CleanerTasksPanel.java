package com.frame.small;

import javax.swing.JPanel;

import com.frame.Panel;
import com.service.ContainerService;

public class CleanerTasksPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CleanerTasksPanel() {
		super();
		
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
