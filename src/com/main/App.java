package com.main;


import javax.swing.JPanel;
import javax.swing.UIManager;

import com.frame.Frame;
import com.frame.LoginPanel;
import com.models.enums.Role;
import com.service.Holder;

public class App {
	public static void main(String[]args) {
		
		try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		System.out.println(Role.ADMIN.getRole().getClass());
		
		Holder holder = Holder.getInstance();
		
		JPanel loginPanel = new LoginPanel();
		Frame mainFrame = holder.frame;
		
		mainFrame.setPanel(loginPanel);
		mainFrame.setVisible(true);
		
	}
}