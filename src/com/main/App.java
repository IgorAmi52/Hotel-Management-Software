package com.main;


import javax.swing.JPanel;
import com.frame.Frame;
import com.frame.LoginPanel;
import com.service.Holder;

public class App {
	public static void main(String[]args) {
		
		Holder holder = Holder.getInstance();
		
		JPanel loginPanel = new LoginPanel();
		Frame mainFrame = holder.frame;
		
		mainFrame.setPanel(loginPanel);
		mainFrame.setVisible(true);
		
	}
}