
package com.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.models.Reservation;
import com.models.Staff;

public interface ReportsServiceInterface {

	Map<String, double[]> getYearlyRoomTypeStatistics(int year, String[] roomTypes) throws IOException;

	Map<String, Integer> getCleanersActivityInLastThirtyDays() throws IOException;

	Map<String, Integer> getReservationStatusesCreatedInLastThirtyDays() throws IOException;

	int getConfirmedNumber(String fromDate, String toDate) throws IOException;

	int getCancelledNumber(String fromDate, String toDate) throws IOException;

	int getRejectedNumber(String fromDate, String toDate) throws IOException;

	HashMap<String, Integer> getCleanersActity(String fromDate, String toDate) throws IOException;

	HashMap<String, double[]> getRoomsActivity(String fromDate, String toDate) throws IOException;

	void reservationConfirmed(Reservation reservation) throws IOException;

	void reservationCancelled() throws IOException;

	void reservationRejected() throws IOException;

	void cleaningAssigned(Staff cleaner) throws IOException;

	void createMissingReports(String lastDate) throws IOException;
}
