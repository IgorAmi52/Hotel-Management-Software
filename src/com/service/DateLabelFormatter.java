package com.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
    public static boolean checkIntervalOverlap(String startDate1, String endDate1, String startDate2, String endDate2) {
        // Parse dates
        LocalDate start1 = LocalDate.parse(startDate1);
        LocalDate end1 = LocalDate.parse(endDate1);
        LocalDate start2 = LocalDate.parse(startDate2);
        LocalDate end2 = LocalDate.parse(endDate2);

        // Check for overlap
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }

}