package com.frame;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.frame.small.HomePanel;
import com.frame.small.ManagePricesPanel;
import com.frame.small.ManageStaffPanel;
import com.frame.small.RegisterGuestPanel;
import com.frame.small.RegisterStaffPanel;
import com.service.ContainerService;

import javax.swing.JSplitPane;

public class AdminMainPanel extends JTabbedPane {

	public AdminMainPanel() {
		super();
		setSize(ContainerService.panelHeight, ContainerService.panelWidth);
		Panel homePanel = new HomePanel();
		Panel registerStaffPanel = new RegisterStaffPanel();
		Panel registerGuestPanel = new RegisterGuestPanel();
		Panel manageStaffPanel = new ManageStaffPanel();
		Panel managePricesPanel = new ManagePricesPanel();
		addTab("Home", (Component) homePanel);
		addTab("Register Staff",(Component)  registerStaffPanel);
		addTab("Register Guest",(Component) registerGuestPanel);
		addTab("Manage Staff",(Component)  manageStaffPanel);
		addTab("Manage Pricing",(Component)  managePricesPanel);
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
