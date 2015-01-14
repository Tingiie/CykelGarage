package gui;

import garage.BicycleGarageManager;
import garage.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class RegisterUser {

	private BicycleGarageManager manager;
	private FocusTextField surnameI;
	private FocusTextField lastNameI;
	private FocusTextField personNumberI;
	private FocusTextField phoneNumberI;
	private FocusTextField mailI;
	private JFrame frame;

	public RegisterUser(BicycleGarageManager manager) {
		this.manager = manager;
		frame = new JFrame("Registrera användare");

		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.add(createButtonPanel(), BorderLayout.SOUTH);

		frame.setSize(430, 285);
		frame.setMinimumSize(new Dimension(430, 285));
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

		JLabel surname = new JLabel("Förnamn");
		textPanel.add(surname);
		surnameI = new FocusTextField("Skriv förnamn", 100);
		textPanel.add(surnameI);
		springLayout.putConstraint(SpringLayout.WEST, surname, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, surnameI, 140,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, surnameI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, surnameI, 30,
				SpringLayout.NORTH, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, surname, 30,
				SpringLayout.NORTH, textPanel);

		JLabel lastName = new JLabel("Efternamn");
		textPanel.add(lastName);
		lastNameI = new FocusTextField("Skriv efternamn", 200);
		textPanel.add(lastNameI);
		springLayout.putConstraint(SpringLayout.WEST, lastName, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, lastNameI, 140,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, lastNameI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, lastNameI, 30,
				SpringLayout.NORTH, surnameI);
		springLayout.putConstraint(SpringLayout.NORTH, lastName, 30,
				SpringLayout.NORTH, surname);

		JLabel personNumber = new JLabel("Personnummer");
		textPanel.add(personNumber);
		personNumberI = new FocusTextField("Skriv personnummer", 200);
		textPanel.add(personNumberI);
		springLayout.putConstraint(SpringLayout.WEST, personNumber, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, personNumberI, 140,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, personNumberI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, personNumberI, 30,
				SpringLayout.NORTH, lastNameI);
		springLayout.putConstraint(SpringLayout.NORTH, personNumber, 30,
				SpringLayout.NORTH, lastName);

		JLabel phoneNumber = new JLabel("Telefonnummer");
		textPanel.add(phoneNumber);
		phoneNumberI = new FocusTextField("Skriv telefonnummer", 200);
		textPanel.add(phoneNumberI);
		springLayout.putConstraint(SpringLayout.WEST, phoneNumber, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, phoneNumberI, 140,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, phoneNumberI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, phoneNumberI, 30,
				SpringLayout.NORTH, personNumberI);
		springLayout.putConstraint(SpringLayout.NORTH, phoneNumber, 30,
				SpringLayout.NORTH, personNumber);

		JLabel mail = new JLabel("Mailadress");
		textPanel.add(mail);
		mailI = new FocusTextField("Skriv mailadress", 200);
		textPanel.add(mailI);
		springLayout.putConstraint(SpringLayout.WEST, mail, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, mailI, 140,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, mailI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, mailI, 30,
				SpringLayout.NORTH, phoneNumberI);
		springLayout.putConstraint(SpringLayout.NORTH, mail, 30,
				SpringLayout.NORTH, phoneNumber);
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
				String pNumber = personNumberI.getText();
				if (pNumber.length() != 10 || !pNumber.matches("[0-9]+")) {
					new MessageDialog(
							"Registreringen misslyckades! \nFel format på personnummer.",
							"Registrering av användare");
				} else {
					String sname = surnameI.getText();
					String lname = lastNameI.getText();
					String pincode = null;
					boolean reg = manager.registerUser(sname, lname, pNumber,
							phoneNumberI.getText(), mailI.getText());
					if (reg) {
						LinkedList<User> users = manager.getUsers();
						for (User u : users) {
							if (u.getPersonNumber().equals(pNumber)) {
								pincode = u.getPinCode();
							}
						}
						if (pincode == null) {
							new MessageDialog(
									"Registreringen lyckades... \nMEN användaren har inte blivit tilldelad en PIN-kod.",
									"Registrering av användare");
							frame.dispose();
						} else {
							new MessageDialog(
									"Registreringen lyckades! \n"
											+ sname
											+ " "
											+ lname
											+ " finns nu tillagd i systemet.\nAnvändaren har blivit tilldelad PIN-koden "
											+ pincode + ".",
									"Registrering av användare");
							frame.dispose();
						}
					} else if (!reg) {
						new MessageDialog(
								"Registreringen misslyckades! \nDet finns redan en användare med det angivna personnumret "
										+ pNumber + ".",
								"Registrering av användare");
					}
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



