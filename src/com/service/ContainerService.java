package com.service;

import java.awt.Component;
import java.awt.Container;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ContainerService {
	
	public static int panelHeight = 1000;
	public static int panelWidth = 550;
	
	public static void resetFields(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            } else if (component instanceof JComboBox) {
                ((JComboBox<?>) component).setSelectedIndex(0);
            } else if (component instanceof JCheckBox) {
                ((JCheckBox) component).setSelected(false);
            } else if (component instanceof Container) {
                resetFields((Container) component); // Recursively reset fields in nested containers
            }
        }
    }
    public static String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null; // Return null if no button is selected
    }
}
