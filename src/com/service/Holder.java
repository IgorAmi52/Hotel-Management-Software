package com.service;

import javax.swing.JPanel;
import com.frame.Frame;
import com.models.Person;

public class Holder {
	
	private static Holder instance;
	
	private Person user;
	public Frame frame;
	private JPanel[] panels; //to be impleemented for hashing
	
	
	private Holder(Frame frame) {
		this.frame = frame;
	}
	public static Holder getInstance() {
		if(instance == null) {
			instance = new Holder(new Frame());
		}
		return instance;
	}
	public void setUser(Person user) {
		this.user = user;
		frame.setPanel(AuthService.userRedirect(user));
	}
	public Person getUser() {
		return this.user;
	}

}
