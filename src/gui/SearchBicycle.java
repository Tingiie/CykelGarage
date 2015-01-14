package gui;

import garage.Bicycle;
import garage.BicycleGarageManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SearchBicycle {

	private BicycleGarageManager manager;
	private JFrame frame;
	private FocusTextField streckkodI;

	public SearchBicycle(BicycleGarageManager manager) {
		this.manager = manager;
		frame = new JFrame("Sök cykel");

		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.add(createButtonPanel(), BorderLayout.SOUTH);

		frame.setSize(410, 165);
		frame.setMinimumSize(new Dimension(410, 165));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.setPreferredSize(new Dimension(380, 150));
		mainPanel.add(createTextPanel());
		return mainPanel;
	}

	private JPanel createTextPanel() {
		SpringLayout springLayout = new SpringLayout();
		JPanel textPanel = new JPanel();
		textPanel.setLayout(springLayout);
		textPanel.setPreferredSize(new Dimension(380, 150));

		JLabel streckkod = new JLabel("Cykelns Streckkod");
		textPanel.add(streckkod);
		streckkodI = new FocusTextField("Cykelns Streckkod", 100);
		textPanel.add(streckkodI);
		springLayout.putConstraint(SpringLayout.WEST, streckkod, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, streckkodI, 150,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, streckkodI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, streckkodI, 30,
				SpringLayout.NORTH, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, streckkod, 30,
				SpringLayout.NORTH, textPanel);
		return textPanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(new EmptyBorder(0, 0, 8, 8));
		buttonPanel.add(createSearchButton());
		buttonPanel.add(createCancelButton());
		return buttonPanel;
	}

	private JButton createSearchButton() {
		JButton searchButton = new JButton("sök");
		searchButton.setFocusPainted(false);
		searchButton.setBackground(Color.white);
		searchButton.setPreferredSize(new Dimension(90, 25));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String barcode = streckkodI.getText();
				LinkedList<Bicycle> bList = manager.getBicycles();
				boolean found = false;
				for (Bicycle b : bList) {
					if (b.getBarcode().equals(barcode)) {
						new BicycleInformation(manager, b);
						found = true;
						frame.dispose();
						break;
					}
				}
				if (!found) {
					new MessageDialog(
							"Sökningen misslyckades! \nDet existerar ingen cykel med streckkoden "
									+ barcode + " i systemet.", "Sök cykel");
				}
			}
		});
		return searchButton;
	}

	private JButton createCancelButton() {
		JButton cancelButton = new JButton("avbryt");
		cancelButton.setFocusPainted(false);
		cancelButton.setBackground(Color.white);
		cancelButton.setPreferredSize(new Dimension(90, 25));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		return cancelButton;
	}
}
