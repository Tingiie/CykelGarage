package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import garage.Bicycle;
import garage.BicycleGarageManager;
import garage.User;

public class UnregisterBicycle {

	private BicycleGarageManager manager;
	private JFrame frame;
	private FocusTextField pinkodI;
	private FocusTextField streckkodI;

	public UnregisterBicycle(BicycleGarageManager manager) {
		this.manager = manager;
		frame = new JFrame("Avregistrering av cykel");

		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.add(createButtonPanel(), BorderLayout.SOUTH);

		frame.setSize(430, 195);
		frame.setMinimumSize(new Dimension(430, 195));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.setPreferredSize(new Dimension(400, 200));
		mainPanel.add(createTextPanel());
		return mainPanel;
	}

	private JPanel createTextPanel() {
		SpringLayout springLayout = new SpringLayout();
		JPanel textPanel = new JPanel();
		textPanel.setLayout(springLayout);
		textPanel.setPreferredSize(new Dimension(400, 200));

		JLabel pinkod = new JLabel("Användarens PIN-kod");
		textPanel.add(pinkod);
		pinkodI = new FocusTextField("Skriv PIN-kod", 100);
		textPanel.add(pinkodI);
		springLayout.putConstraint(SpringLayout.WEST, pinkod, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, pinkodI, 170,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, pinkodI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, pinkodI, 30,
				SpringLayout.NORTH, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, pinkod, 30,
				SpringLayout.NORTH, textPanel);

		JLabel streckkod = new JLabel("Cykelns streckkod");
		textPanel.add(streckkod);
		streckkodI = new FocusTextField("Skriv streckkod", 100);
		textPanel.add(streckkodI);
		springLayout.putConstraint(SpringLayout.WEST, streckkod, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, streckkodI, 170,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, streckkodI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, streckkodI, 30,
				SpringLayout.NORTH, pinkodI);
		springLayout.putConstraint(SpringLayout.NORTH, streckkod, 30,
				SpringLayout.NORTH, pinkod);
		return textPanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(new EmptyBorder(0, 0, 8, 8));
		buttonPanel.add(createOkButton());
		buttonPanel.add(createCancelButton());
		return buttonPanel;
	}

	private JButton createOkButton() {
		JButton okButton = new JButton("ok");
		okButton.setFocusPainted(false);
		okButton.setBackground(Color.white);
		okButton.setPreferredSize(new Dimension(90, 25));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pincode = pinkodI.getText();
				String barcode = streckkodI.getText();
				LinkedList<User> uList = manager.getUsers();
				boolean found = false;
				for (User u : uList) {
					if (u.getPinCode().equals(pincode)) {
						found = true;
						Bicycle b1 = u.getBicycle(0);
						Bicycle b2 = u.getBicycle(1);
						if (b1 != null && b1.getBarcode().equals(barcode)) {
							if (b1.getGarageStatus()) {
								new MessageDialog(
										"Avregistreringen misslyckades! \nCykeln med streckkoden "
												+ barcode + " är incheckad.",
										"Avregistrering av cykel");
							} else {
								boolean check = manager.unregisterBicycle(b1
										.getBarcode());
								if (check) {
									new MessageDialog(
											"Avregistreringen lyckades! \nCykeln med streckkoden "
													+ barcode
													+ " är nu avregistrerad för användaren med personnummer "
													+ u.getPersonNumber()
													+ ". Om garaget är fullt bör användaren längst fram i platskön kontaktas.",
											"Avregistrering av cykel");
								} else {
									new MessageDialog(
											"Avregistreringen misslyckades! \nSystemfel.",
											"Avregistrering av cykel");
								}
							}
							frame.dispose();
						} else if (b2 != null && b2.getBarcode().equals(barcode)) {
							if (b2.getGarageStatus()) {
								new MessageDialog(
										"Avregistreringen misslyckades! \nCykeln med streckkoden "
												+ barcode + " är incheckad.",
										"Avregistrering av cykel");
							} else {
								boolean check = manager.unregisterBicycle(b2
										.getBarcode());
								if (check) {
									new MessageDialog(
											"Avregistreringen lyckades! \nCykeln med streckkoden "
													+ barcode
													+ " är nu avregistrerad för användaren med personnummer "
													+ u.getPersonNumber()
													+ ". Om garaget är fullt bör användaren längst fram i platskön kontaktas.",
											"Avregistrering av cykel");
								} else {
									new MessageDialog(
											"Avregistreringen misslyckades! \nSystemfel.",
											"Avregistrering av cykel");
								}
							}
							frame.dispose();
						} else {
							new MessageDialog(
									"Avregistreringen misslyckades! \nAnvändaren med PIN-kod "
											+ u.getPinCode()
											+ " har ingen cykel med den angivna streckkoden "
											+ barcode + " registrerad.",
									"Avregistrering av cykel");
						}
						frame.dispose();
						break;
					}
				}
				if(!found) {
						new MessageDialog(
								"Avregistreringen misslyckades! \nDet finns ingen användare i systemet med den inslagna pinkoden "
										+ pincode + ".",
								"Avregistrering av cykel");
					}
				}
		});
		return okButton;
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


