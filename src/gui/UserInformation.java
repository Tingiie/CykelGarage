package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import garage.Bicycle;
import garage.BicycleGarageManager;
import garage.Entry;
import garage.User;

public class UserInformation {

	private JFrame frame;
	private BicycleGarageManager manager;
	private User user;

	private String surnameU;
	private String lastNameU;
	private String personNumberU;
	private String phoneNumberU;
	private String mailAdressU;
	private String pincodeU;
	private boolean statusU;
	private Bicycle bicycle1U;
	private Bicycle bicycle2U;

	private JTextField surnameI;
	private JTextField lastNameI;
	private JTextField personNumberI;
	private JTextField phoneNumberI;
	private JTextField mailI;
	private JTextField pincodeI;
	private JComboBox<String> statusI;

	private Timer t;

	public UserInformation(BicycleGarageManager manager, User user) {

		this.user = user;
		surnameU = user.getSurname();
		lastNameU = user.getLastName();
		personNumberU = user.getPersonNumber();
		phoneNumberU = user.getPhoneNumber();
		mailAdressU = user.getMailAddress();
		pincodeU = user.getPinCode();
		statusU = user.getGarageStatus();
		bicycle1U = user.getBicycle(0);
		bicycle2U = user.getBicycle(1);

		this.manager = manager;
		frame = new JFrame("Användarinformation för " + surnameU + " "
				+ lastNameU);

		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.add(createButtonPanel(), BorderLayout.SOUTH);

		frame.setSize(430, 415);
		frame.setMinimumSize(new Dimension(430, 415));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		t = new Timer();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				t.cancel();
			}
		});
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.setPreferredSize(new Dimension(400, 365));
		mainPanel.add(createTextPanel());
		return mainPanel;
	}

	private JPanel createTextPanel() {
		SpringLayout springLayout = new SpringLayout();
		JPanel textPanel = new JPanel();
		textPanel.setLayout(springLayout);
		textPanel.setPreferredSize(new Dimension(400, 475));

		JLabel surname = new JLabel("Förnamn");
		textPanel.add(surname);
		surnameI = new JTextField(surnameU, 100);
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
		lastNameI = new JTextField(lastNameU, 200);
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
		personNumberI = new JTextField(personNumberU, 200);
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
		phoneNumberI = new JTextField(phoneNumberU, 200);
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
		mailI = new JTextField(mailAdressU, 200);
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

		JLabel pincode = new JLabel("PIN-kod");
		textPanel.add(pincode);
		pincodeI = new JTextField(pincodeU, 200);
		textPanel.add(pincodeI);
		springLayout.putConstraint(SpringLayout.WEST, pincode, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, pincodeI, 140,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, pincodeI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, pincodeI, 30,
				SpringLayout.NORTH, mailI);
		springLayout.putConstraint(SpringLayout.NORTH, pincode, 30,
				SpringLayout.NORTH, mail);

		String stat[] = new String[2];
		if (statusU) {
			stat[0] = "true";
			stat[1] = "false";
		} else {
			stat[0] = "false";
			stat[1] = "true";
		}

		JLabel status = new JLabel("Incheckad");
		textPanel.add(status);
		statusI = new JComboBox<String>(stat);
		textPanel.add(statusI);
		springLayout.putConstraint(SpringLayout.WEST, status, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, statusI, 140,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, statusI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, statusI, 30,
				SpringLayout.NORTH, pincodeI);
		springLayout.putConstraint(SpringLayout.NORTH, status, 30,
				SpringLayout.NORTH, pincode);

		JLabel bicycleInfo1 = new JLabel("Cykel 1");
		textPanel.add(bicycleInfo1);
		if (bicycle1U != null) {
			JButton bicycle1InfoI = new JButton("visa cykelinfo");
			bicycle1InfoI.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new BicycleInformation(manager, bicycle1U);
				}
			});
			textPanel.add(bicycle1InfoI);
			springLayout.putConstraint(SpringLayout.WEST, bicycleInfo1, 30,
					SpringLayout.WEST, textPanel);
			springLayout.putConstraint(SpringLayout.WEST, bicycle1InfoI, 140,
					SpringLayout.WEST, textPanel);
			springLayout.putConstraint(SpringLayout.EAST, bicycle1InfoI, -30,
					SpringLayout.EAST, textPanel);
			springLayout.putConstraint(SpringLayout.NORTH, bicycle1InfoI, 30,
					SpringLayout.NORTH, status);
			springLayout.putConstraint(SpringLayout.NORTH, bicycleInfo1, 30,
					SpringLayout.NORTH, status);
		} else {
			JLabel bicycleInfo1I = new JLabel("Ej registrerad");
			textPanel.add(bicycleInfo1I);
			springLayout.putConstraint(SpringLayout.WEST, bicycleInfo1, 30,
					SpringLayout.WEST, textPanel);
			springLayout.putConstraint(SpringLayout.WEST, bicycleInfo1I, 140,
					SpringLayout.WEST, textPanel);
			springLayout.putConstraint(SpringLayout.EAST, bicycleInfo1I, -30,
					SpringLayout.EAST, textPanel);
			springLayout.putConstraint(SpringLayout.NORTH, bicycleInfo1I, 30,
					SpringLayout.NORTH, status);
			springLayout.putConstraint(SpringLayout.NORTH, bicycleInfo1, 30,
					SpringLayout.NORTH, status);
		}

		JLabel bicycleInfo2 = new JLabel("Cykel 2");
		textPanel.add(bicycleInfo2);
		if (bicycle2U != null) {
			JButton bicycle2InfoI = new JButton("visa cykelinfo");
			bicycle2InfoI.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new BicycleInformation(manager, bicycle2U);
				}
			});
			textPanel.add(bicycle2InfoI);
			springLayout.putConstraint(SpringLayout.WEST, bicycleInfo2, 30,
					SpringLayout.WEST, textPanel);
			springLayout.putConstraint(SpringLayout.WEST, bicycle2InfoI, 140,
					SpringLayout.WEST, textPanel);
			springLayout.putConstraint(SpringLayout.EAST, bicycle2InfoI, -30,
					SpringLayout.EAST, textPanel);
			springLayout.putConstraint(SpringLayout.NORTH, bicycle2InfoI, 30,
					SpringLayout.NORTH, bicycleInfo1);
			springLayout.putConstraint(SpringLayout.NORTH, bicycleInfo2, 30,
					SpringLayout.NORTH, bicycleInfo1);
		} else {
			JLabel bicycle2InfoI = new JLabel("Ej registrerad");
			textPanel.add(bicycle2InfoI);
			springLayout.putConstraint(SpringLayout.WEST, bicycleInfo2, 30,
					SpringLayout.WEST, textPanel);
			springLayout.putConstraint(SpringLayout.WEST, bicycle2InfoI, 140,
					SpringLayout.WEST, textPanel);
			springLayout.putConstraint(SpringLayout.EAST, bicycle2InfoI, -30,
					SpringLayout.EAST, textPanel);
			springLayout.putConstraint(SpringLayout.NORTH, bicycle2InfoI, 30,
					SpringLayout.NORTH, bicycleInfo1);
			springLayout.putConstraint(SpringLayout.NORTH, bicycleInfo2, 30,
					SpringLayout.NORTH, bicycleInfo1);
		}
		return textPanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(new EmptyBorder(0, 0, 8, 8));
		buttonPanel.add(createSaveButton());
		buttonPanel.add(createCloseButton());
		return buttonPanel;
	}

	private JButton createSaveButton() {
		JButton okButton = new JButton("spara ändringar");
		okButton.setFocusPainted(false);
		okButton.setBackground(Color.white);
		okButton.setPreferredSize(new Dimension(150, 25));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.setSurname(surnameI.getText());
				user.setLastName(lastNameI.getText());

				String pNumber = personNumberI.getText();
				String errMsg = "";
				boolean fail = false;
				if (pNumber.length() != 10 || !pNumber.matches("[0-9]+")) {
					errMsg += "Nytt personnummer har inte sparats då det har fel format.";
					fail = true;
				} else {
					boolean unique = true;
					for (User u : manager.getUsers()) {
						if (u.getPersonNumber().equals(pNumber) && u != user) {
							unique = false;
						}
					}
					if (!unique) {
						errMsg += "Nytt personnummer är redan sparat för en annan användare. ";
						fail = true;
					} else {
						user.setPersonNumber(personNumberI.getText());
					}
				}
				if (!user.getPinCode().equals(pincodeI.getText()) && !manager.changePinCode(user, pincodeI.getText())) {
					errMsg += "PIN-koden har ej sparats, kontrollera att formatet är korrekt, om så är fallet är koden redan upptagen av en annan användare.";
					fail = true;
				}
				
				if (!fail) {
					new MessageDialog("Ändringar har sparats!.",
							"Ändringar sparade");
				} else {
					new MessageDialog (errMsg, "Sparning misslyckades");
				}
				user.setPhoneNumber(phoneNumberI.getText());
				user.setMailAddress(mailI.getText());
				
				
				String statusCheck = (String) statusI.getSelectedItem();

				if (statusCheck.length() == 4) {
					if (!user.getGarageStatus()) {
						LinkedList<garage.Entry<User, Date>> users = manager
								.getCheckedInUsers();
						Entry<User, Date> entry = new Entry<User, Date>(user,
								new Date());
						users.add(entry);
						user.setGarageStatus(true);
					}

				} else if (statusCheck.length() == 5) {
					if (user.getGarageStatus()) {
						LinkedList<garage.Entry<User, Date>> users = manager
								.getCheckedInUsers();
						Entry<User, Date> entry = new Entry<User, Date>(user,
								null);
						if (users.remove(entry)) {
							user.setGarageStatus(false);
						}
					}
				}

				frame.dispose();
			}
		});
		return okButton;
	}

	private JButton createCloseButton() {
		JButton cancelButton = new JButton("stäng");
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


