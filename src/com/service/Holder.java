package com.service;

import com.frame.Frame;
import com.models.User;

public class Holder {

	private static Holder instance;
	private static AuthService authService = new AuthService(new DataAccessImpl());
	private User user;
	public Frame frame;

	private Holder(Frame frame) {
		this.frame = frame;
	}

	public static Holder getInstance() {
		if (instance == null) {
			instance = new Holder(new Frame());
		}
		return instance;
	}

	public void setUser(User user) {
		this.user = user;
		frame.setPanel(authService.userRedirect(user));
	}

	public User getUser() {
		return this.user;
	}

}
