package com.frame.small;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import com.frame.Panel;
import com.service.ContainerService;
import com.service.Holder;
import com.service.ReportsServiceInterface;
import com.service.RoomServiceInterface;

public class StatisticsPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	private Map<String, double[]> yearlyRoomTypeMap;
	private Map<String, Integer> cleanersMap;
	private Map<String, Integer> reservationMap;
	private XYChart chart;
	private PieChart cleanersChart;
	private PieChart reservationChart;
	private double[] months = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	private ReportsServiceInterface reportsService = Holder.getInstance().getReportsService();
	private RoomServiceInterface roomService = Holder.getInstance().getRoomService();

	public StatisticsPanel() {
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);

		chart = new XYChartBuilder().width(800).height(600).title("Monthly Profit by Room Type").xAxisTitle("Month")
				.yAxisTitle("Profit").build();

		Styler styler = chart.getStyler();
		styler.setChartTitleVisible(true);
		styler.setLegendPosition(Styler.LegendPosition.OutsideE);
		styler.setMarkerSize(6);

		chart.getStyler().setXAxisDecimalPattern("0");
		chart.getStyler().setXAxisLabelRotation(45);

		JPanel chartPanel = new XChartPanel<>(chart);
		chartPanel.setLocation(79, 20);
		chartPanel.setSize(848, 300);

		add(chartPanel);

		cleanersChart = new PieChartBuilder().width(800).height(600).title("Obstruction of maids for the last 30 days")
				.build();
		JPanel cleanersChartPanel = new XChartPanel<>(cleanersChart);
		cleanersChartPanel.setLocation(79, 369);
		cleanersChartPanel.setSize(404, 214);

		add(cleanersChartPanel);

		double[] reservationValues = { 1, 2, 3, 4 };
		String[] reservationLabels = { "Sobarica 1", "Sobarica 2", "Sobarica 3", "Sobarica 4" };

		// Create Chart
		reservationChart = new PieChartBuilder().width(800).height(600).title("Status of reservations in last 30 days")
				.build();

		// Customize Chart
		for (int i = 0; i < reservationValues.length; i++) {
			reservationChart.addSeries(reservationLabels[i], reservationValues[i]);
		}
		JPanel reservationChartPanel = new XChartPanel<>(reservationChart);
		reservationChartPanel.setLocation(509, 369);
		reservationChartPanel.setSize(418, 214);

		add(reservationChartPanel);

	}

	public static void clearChartData(Object chart) {
		if (chart instanceof XYChart) {
			XYChart xyChart = (XYChart) chart;
			// Get all series names
			List<String> seriesNames = xyChart.getSeriesMap().keySet().stream().collect(Collectors.toList());
			// Remove each series
			for (String seriesName : seriesNames) {
				xyChart.removeSeries(seriesName);
			}
		} else if (chart instanceof PieChart) {
			PieChart pieChart = (PieChart) chart;
			// Get all series names
			List<String> seriesNames = pieChart.getSeriesMap().keySet().stream().collect(Collectors.toList());
			// Remove each series
			for (String seriesName : seriesNames) {
				pieChart.removeSeries(seriesName);
			}
		} else {
			throw new IllegalArgumentException("Unsupported chart type");
		}
	}

	@Override
	public void reset() {
		clearChartData(chart);
		clearChartData(cleanersChart);
		clearChartData(reservationChart);
		try {
			yearlyRoomTypeMap = reportsService.getYearlyRoomTypeStatistics(2024, roomService.getRoomTypes());
			cleanersMap = reportsService.getCleanersActivityInLastThirtyDays();
			reservationMap = reportsService.getReservationStatusesCreatedInLastThirtyDays();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String type : yearlyRoomTypeMap.keySet()) {
			double[] profitsByMonth = yearlyRoomTypeMap.get(type);
			XYSeries series = chart.addSeries(type, months, profitsByMonth);
			series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
		}
		for (String cleaner : cleanersMap.keySet()) {
			Integer roomsCleaned = cleanersMap.get(cleaner);
			cleanersChart.addSeries(cleaner, roomsCleaned);
		}
		for (String status : reservationMap.keySet()) {
			Integer temp = reservationMap.get(status);
			reservationChart.addSeries(status, temp);
		}
	}

}
