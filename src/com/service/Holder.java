package com.service;

import com.frame.Frame;
import com.models.User;

public class Holder {

	private static Holder instance;
	private AuthService authService;
	private PricingServiceInterface pricingService;
	private ReservationService reservationService;
	private ReportsServiceInterface reportService;
	private RoomServiceInterface roomService;
	private UserService userService;

	private User user;
	private Frame frame;

	private Holder(Frame frame, AuthService authService, UserService userService,
			PricingServiceInterface pricingService, ReservationService reservationService,
			ReportsServiceInterface reportService, RoomServiceInterface roomService) {
		this.frame = frame;
		this.authService = authService;
		this.userService = userService;
		this.pricingService = pricingService;
		this.reservationService = reservationService;
		this.reportService = reportService;
		this.roomService = roomService;

	}

	public static Holder getInstance() {
		if (instance == null) {
			DataAccessInterface dataAccessService = new DataAccessImpl();
			AuthService authService = new AuthService(dataAccessService);
			UserService userService = new UserService(dataAccessService);
			PricingServiceInterface pricingService = new PricingServiceImpl(dataAccessService);
			ReportsServiceInterface reportService = new ReportsServiceImpl(dataAccessService);
			RoomServiceInterface roomService = new RoomServiceImpl(dataAccessService, reportService);
			ReservationService reservationService = new ReservationService(dataAccessService, pricingService,
					reportService);

			instance = new Holder(new Frame(), authService, userService, pricingService, reservationService,
					reportService, roomService);
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

	public AuthService getAuthService() {
		return this.authService;
	}

	public PricingServiceInterface getPricingService() {
		return this.pricingService;
	}

	public ReservationService getReservationService() {
		return this.reservationService;
	}

	public ReportsServiceInterface getReportService() {
		return this.reportService;
	}

	public RoomServiceInterface getRoomService() {
		return this.roomService;
	}

	public ReportsServiceInterface getReportsService() {
		return this.reportService;
	}

	public UserService getUserService() {
		return this.userService;
	}

	public Frame getFrame() {
		return this.frame;
	}

}
