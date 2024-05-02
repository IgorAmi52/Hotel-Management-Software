package com.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.models.Person;
import com.service.ContainerService;
import com.service.Holder;

public class Frame extends JFrame {
	
	private JComponent currPanel;

	
	public Frame() {
		setTitle("Hotel Managment App");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);		
	}
	public void setPanel(JComponent component) {
		removePanel();
		getContentPane().add(component);
		revalidate();
		repaint();
		currPanel = component;
	}
	private void removePanel() {
		if(currPanel!=null) {
			getContentPane().remove(currPanel);
		}
	}

}
