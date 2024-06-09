package com.frame.small;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import com.frame.Panel;
import com.service.ContainerService;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class StatisticsPanel extends JPanel implements Panel{

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public StatisticsPanel() {
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		XYChart chart = new XYChartBuilder().width(800).height(600).title("Monthly Profit by Room Type").xAxisTitle("Month").yAxisTitle("Profit").build();

        // Customize the chart with Styler
        Styler styler = chart.getStyler();
        styler.setChartTitleVisible(true);
        styler.setLegendPosition(Styler.LegendPosition.OutsideE);
        styler.setMarkerSize(6);
      //  styler.setXAxisTickMarkSpacingHint(50);

        // Define the data
        double[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        double[] profitsRoomType1 = {1000, 1200, 900, 1100, 1500, 1300, 1400, 1700, 1600, 1800, 1900, 2000};
        double[] profitsRoomType2 = {2000, 2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000, 3100};
        double[] profitsRoomType3 = {1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600};
        double[] totalProfits = {4500, 4900, 4800, 5200, 5800, 5800, 6100, 6600, 6700, 7100, 7400, 7700};

        // Add series
        XYSeries series1 = chart.addSeries("Room Type 1", months, profitsRoomType1);
        XYSeries series2 = chart.addSeries("Room Type 2", months, profitsRoomType2);
        XYSeries series3 = chart.addSeries("Room Type 3", months, profitsRoomType3);
        XYSeries series4 = chart.addSeries("Total", months, totalProfits);

        // Set render style for each series
        series1.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        series2.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        series3.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        series4.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
    
        // Customize the chart further for better readability
        chart.getStyler().setXAxisDecimalPattern("0");
        chart.getStyler().setXAxisLabelRotation(45);

        // Create a JPanel to hold the chart
        JPanel chartPanel = new XChartPanel<>(chart);
        chartPanel.setLocation(100, 20);
        chartPanel.setSize(800, 300);

	    add(chartPanel);
	    
	    
	    double[] cleanerValues = { 1, 2, 3, 4 };
        String[] cleanerLabels = { "Sobarica 1", "Sobarica 2", "Sobarica 3", "Sobarica 4" };

        // Create Chart
        PieChart cleanersChart = new PieChartBuilder().width(800).height(600).title("Pie Chart").build();

        // Customize Chart
        for (int i = 0; i < cleanerValues.length; i++) {
        	cleanersChart.addSeries(cleanerLabels[i], cleanerValues[i]);
        }
        JPanel cleanersChartPanel = new XChartPanel<>(cleanersChart);
        cleanersChartPanel.setLocation(100, 369);
        cleanersChartPanel.setSize(331, 214);

	    add(cleanersChartPanel);
	    
	    double[] reservationValues = { 1, 2, 3, 4 };
        String[] reservationLabels = { "Sobarica 1", "Sobarica 2", "Sobarica 3", "Sobarica 4" };

        // Create Chart
        PieChart reservationChart = new PieChartBuilder().width(800).height(600).title("Pie Chart").build();

        // Customize Chart
        for (int i = 0; i < reservationValues.length; i++) {
        	reservationChart.addSeries(reservationLabels[i], reservationValues[i]);
        }
        JPanel reservationChartPanel = new XChartPanel<>(reservationChart);
        reservationChartPanel.setLocation(569, 369);
        reservationChartPanel.setSize(331, 214);

	    add(reservationChartPanel);
	    
	    JLabel lblNewLabel = new JLabel("Last 30 days stats");
	    lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setBounds(100, 333, 800, 26);
	    add(lblNewLabel);
	    
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
