package com.frame.small;

import javax.swing.JPanel;

import com.frame.Panel;
import com.service.ContainerService;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePickerImpl;

import java.awt.Font;
import javax.swing.JButton;

public class ReportsPanel extends JPanel implements Panel {

	private static final long serialVersionUID = 1L;
	
	private JLabel cancelledNumberLabel;
	private JLabel rejectedNumberLabel;
	private JLabel confirmedNumberLabel;
	
	public ReportsPanel() {
		setLayout(null);
		setSize(ContainerService.panelWidth, ContainerService.panelHeight);
		
		JLabel lblNewLabel = new JLabel("Reports");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 17, 988, 36);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("From date:");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(38, 80, 99, 16);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("To date:");
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(38, 117, 99, 16);
		add(lblNewLabel_1_1);
		
	    JDatePickerImpl fromAddDatePicker = ContainerService.getDatePicker();
	    fromAddDatePicker.setBounds(100, 75,158,26);
	    JDatePickerImpl toAddDatePicker = ContainerService.getDatePicker();
	    toAddDatePicker.setBounds(100, 112,158,26);
        add(fromAddDatePicker);
        add(toAddDatePicker);
        
        JButton getReportsButton = new JButton("Get reports");
        getReportsButton.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        getReportsButton.setBounds(38, 159, 174, 29);
        add(getReportsButton);
        
        JLabel lblNewLabel_1_2 = new JLabel("Reservations:");
        lblNewLabel_1_2.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        lblNewLabel_1_2.setBounds(579, 73, 143, 29);
        add(lblNewLabel_1_2);
        
        JLabel lblNewLabel_1_3 = new JLabel("Confirmed:");
        lblNewLabel_1_3.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        lblNewLabel_1_3.setBounds(589, 120, 99, 16);
        add(lblNewLabel_1_3);
        
        JLabel lblNewLabel_1_3_1 = new JLabel("Rejected:");
        lblNewLabel_1_3_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        lblNewLabel_1_3_1.setBounds(589, 155, 99, 16);
        add(lblNewLabel_1_3_1);
        
        JLabel lblNewLabel_1_3_2 = new JLabel("Cancelled:");
        lblNewLabel_1_3_2.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        lblNewLabel_1_3_2.setBounds(589, 190, 99, 16);
        add(lblNewLabel_1_3_2);
        
        confirmedNumberLabel = new JLabel("");
        confirmedNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        confirmedNumberLabel.setBounds(690, 120, 99, 16);
        add(confirmedNumberLabel);
        
        rejectedNumberLabel = new JLabel("");
        rejectedNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        rejectedNumberLabel.setBounds(690, 155, 99, 16);
        add(rejectedNumberLabel);
        
        cancelledNumberLabel = new JLabel("");
        cancelledNumberLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        cancelledNumberLabel.setBounds(690, 190, 99, 16);
        add(cancelledNumberLabel);
        
        
	}

	@Override
	public void reset() {
		ContainerService.resetFields(this);
	    confirmedNumberLabel = new JLabel("");
	    cancelledNumberLabel = new JLabel("");
	    rejectedNumberLabel = new JLabel("");
	}
}
