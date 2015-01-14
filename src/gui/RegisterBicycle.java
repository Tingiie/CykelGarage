package gui;

import garage.BicycleGarageManager;
import garage.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class RegisterBicycle {

	private BicycleGarageManager manager;
	private JFrame frame;
	private FocusTextField pinkodI;

	public RegisterBicycle(BicycleGarageManager manager) {
		this.manager = manager;
		frame = new JFrame("Registrera cykel");

		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.add(createButtonPanel(), BorderLayout.SOUTH);

		frame.setSize(430, 165);
		frame.setMinimumSize(new Dimension(430, 165));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.setPreferredSize(new Dimension(400, 150));
		mainPanel.add(createTextPanel());
		return mainPanel;
	}

	private JPanel createTextPanel() {
		SpringLayout springLayout = new SpringLayout();
		JPanel textPanel = new JPanel();
		textPanel.setLayout(springLayout);
		textPanel.setPreferredSize(new Dimension(400, 150));

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
				LinkedList<User> users = manager.getUsers();
				String pincode = pinkodI.getText();
				User user = null;
				for (User u : users) {
					if (u.getPinCode().equals(pincode)) {
						user = u;
					}
				}
				if (user == null) {
					new MessageDialog(
							"Registreringen misslyckades! \nDet finns ingen användare med angiven PIN-kod.",
							"Registrering av cykel");
				} else if (user.getBicycle(0) != null
						&& user.getBicycle(1) != null) {

					new MessageDialog(
							"Registreringen misslyckades! \nAnvändaren har redan max antal cyklar registrerade.",
							"Registrering av cykel");
					frame.dispose();
				} else {
					if (manager.getRegisterLimit()
							- manager.getBicycles().size() <= 0) {
						Object[] options = { "ja", "nej" };
						Image image = new BufferedImage(1, 1,
								BufferedImage.TYPE_INT_ARGB);
						int n = JOptionPane
								.showOptionDialog(
										frame,
										"Garaget är fullt! \n Ska användaren läggas in i platskön?",
										"Garaget är fullt",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE,
										new ImageIcon(image), options,
										options[0]);
						if (n == 0) {
							manager.moveToQueue(user);
						}
					} else {
				manager.registerBicycle(user);
						new MessageDialog(
								"Registreringen lyckades! \nCykeln är nu registrerad för användaren med personnumret "
										+ user.getPersonNumber() + ".",
								"Registrering av cykel");

					}
					frame.dispose();
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



