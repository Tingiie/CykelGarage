package gui;

import garage.BicycleGarageManager;
import garage.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SearchUser {

	private BicycleGarageManager manager;
	private JFrame frame;
	private FocusTextField pNumberI;

	public SearchUser(BicycleGarageManager manager) {
		this.manager = manager;
		frame = new JFrame("Sök användare");

		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.add(createButtonPanel(), BorderLayout.SOUTH);

		frame.setSize(480, 165);
		frame.setMinimumSize(new Dimension(480, 165));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.setPreferredSize(new Dimension(450, 150));
		mainPanel.add(createTextPanel());
		return mainPanel;
	}

	private JPanel createTextPanel() {
		SpringLayout springLayout = new SpringLayout();
		JPanel textPanel = new JPanel();
		textPanel.setLayout(springLayout);
		textPanel.setPreferredSize(new Dimension(450, 150));

		JLabel pNumber = new JLabel("Användarens personnummer");
		textPanel.add(pNumber);
		pNumberI = new FocusTextField("Skriv personnummer", 100);
		textPanel.add(pNumberI);
		springLayout.putConstraint(SpringLayout.WEST, pNumber, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, pNumberI, 220,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, pNumberI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, pNumberI, 30,
				SpringLayout.NORTH, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, pNumber, 30,
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
				String pNumber = pNumberI.getText();
				LinkedList<User> uList = manager.getUsers();
				boolean found = false;
				for (User u : uList) {
					if (u.getPersonNumber().equals(pNumber)) {
						new UserInformation(manager, u);
						found = true;
						frame.dispose();
						break;
					}
				}
				if (!found) {
					new MessageDialog(
							"Sökningen misslyckades! \nDet existerar ingen användare med personnumret "
									+ pNumber + " i systemet.", "Sök användare");
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
