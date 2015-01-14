package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

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
import garage.User;

public class BicycleInformation {
	private JFrame frame;
	private BicycleGarageManager manager;
	private Bicycle bicycle;

	private String bicycleIDB;
	private User ownerB;
	private boolean gStatusB;
	private boolean statusB;

	private JTextField bicycleIDI;
	private JTextField ownerI;
	private JComboBox<String> gStatusI;
	private JComboBox<String> statusI;

	public BicycleInformation(BicycleGarageManager manager, Bicycle bicycle) {

		this.bicycle = bicycle;
		bicycleIDB = bicycle.getBarcode();
		ownerB = bicycle.getOwner();
		gStatusB = bicycle.getGarageStatus();
		statusB = bicycle.getPaymentStatus();

		this.manager = manager;

		frame = new JFrame("Cykelinformation för " + bicycleIDB);
		frame.add(createMainPanel(), BorderLayout.CENTER);
		frame.add(createButtonPanel(), BorderLayout.SOUTH);

		frame.setSize(430, 295);
		frame.setMinimumSize(new Dimension(430, 295));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainPanel.setPreferredSize(new Dimension(450, 285));
		mainPanel.add(createTextPanel());
		return mainPanel;
	}

	private JPanel createTextPanel() {
		SpringLayout springLayout = new SpringLayout();
		JPanel textPanel = new JPanel();
		textPanel.setLayout(springLayout);
		textPanel.setPreferredSize(new Dimension(400, 245));

		JLabel bicycleID = new JLabel("Streckkod");
		textPanel.add(bicycleID);
		bicycleIDI = new JTextField(bicycleIDB, 100);
		textPanel.add(bicycleIDI);
		springLayout.putConstraint(SpringLayout.WEST, bicycleID, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, bicycleIDI, 185,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, bicycleIDI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, bicycleIDI, 30,
				SpringLayout.NORTH, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, bicycleID, 30,
				SpringLayout.NORTH, textPanel);

		JLabel owner = new JLabel("Ägarens personnummer");
		textPanel.add(owner);
		ownerI = new JTextField(ownerB.getPersonNumber(), 100);
		textPanel.add(ownerI);
		springLayout.putConstraint(SpringLayout.WEST, owner, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, ownerI, 185,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, ownerI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, ownerI, 30,
				SpringLayout.NORTH, bicycleID);
		springLayout.putConstraint(SpringLayout.NORTH, owner, 30,
				SpringLayout.NORTH, bicycleID);

		JLabel ownerInfo = new JLabel("Användarinfo");
		textPanel.add(ownerInfo);
		JButton ownerInfoI = new JButton("visa användarinfo");
		ownerInfoI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserInformation(manager, ownerB);
			}
		});
		textPanel.add(ownerInfoI);
		springLayout.putConstraint(SpringLayout.WEST, ownerInfo, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, ownerInfoI, 185,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, ownerInfoI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, ownerInfoI, 30,
				SpringLayout.NORTH, ownerI);
		springLayout.putConstraint(SpringLayout.NORTH, ownerInfo, 30,
				SpringLayout.NORTH, owner);

		String gStat[] = new String[2];
		if (gStatusB) {
			gStat[0] = "true";
			gStat[1] = "false";
		} else {
			gStat[0] = "false";
			gStat[1] = "true";
		}
		JLabel gStatus = new JLabel("Incheckad");
		textPanel.add(gStatus);
		gStatusI = new JComboBox<String>(gStat);
		textPanel.add(gStatusI);
		springLayout.putConstraint(SpringLayout.WEST, gStatus, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, gStatusI, 185,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, gStatusI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, gStatusI, 30,
				SpringLayout.NORTH, ownerInfo);
		springLayout.putConstraint(SpringLayout.NORTH, gStatus, 30,
				SpringLayout.NORTH, ownerInfo);

		String stat[] = new String[2];
		if (statusB) {
			stat[0] = "true";
			stat[1] = "false";
		} else {
			stat[0] = "false";
			stat[1] = "true";
		}
		JLabel status = new JLabel("Betald");
		textPanel.add(status);
		statusI = new JComboBox<String>(stat);
		textPanel.add(statusI);
		springLayout.putConstraint(SpringLayout.WEST, status, 30,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.WEST, statusI, 185,
				SpringLayout.WEST, textPanel);
		springLayout.putConstraint(SpringLayout.EAST, statusI, -30,
				SpringLayout.EAST, textPanel);
		springLayout.putConstraint(SpringLayout.NORTH, statusI, 30,
				SpringLayout.NORTH, gStatusI);
		springLayout.putConstraint(SpringLayout.NORTH, status, 30,
				SpringLayout.NORTH, gStatus);
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
				LinkedList<Bicycle> tempList = manager.getCheckedInBicycles();
				String newBarcode = bicycleIDI.getText();

				String statusCheck = (String) statusI.getSelectedItem();
				if (statusCheck.length() == 4) {
					bicycle.setPaymentStatus(true);
				} else if (statusCheck.length() == 5) {
					bicycle.setPaymentStatus(false);
				}

				statusCheck = (String) gStatusI.getSelectedItem();
				if (statusCheck.length() == 4) {
					if(!tempList.contains(bicycle))
					tempList.add(bicycle);
					bicycle.setGarageStatus(true);
				} else if (statusCheck.length() == 5) {
					if (tempList.contains(bicycle)) {
						tempList.remove(bicycle);
						bicycle.setGarageStatus(false);
					}
				}

				String pNumber = ownerI.getText();
				LinkedList<User> uList = manager.getUsers();
				User newOwner = null;
				for (User u : uList) {
					if (u.getPersonNumber().equals(pNumber)) {
						newOwner = u;
						break;
					}
				}
				
				boolean fail = false;
				String errMsg = "";
				
				if (!bicycle.getBarcode().equals(newBarcode) && !manager.changeBarcode(bicycle, newBarcode)) {
					errMsg += "Ny streckkod har inte sparats, om angiven kods format är korrekt är den redan upptagen av en annan cykel. ";
					fail = true;
				} 
				if (newOwner == null) {
					errMsg += "Det existerar ingen användare med angivet personnumret i systemet. ";
					fail = true;
				} else if (newOwner.getBicycleAmount() > 1 && newOwner != bicycle.getOwner()) {
					errMsg += "Den angivna användaren har max antal cyklar registrerade. ";
					fail = true;
				} else {
					bicycle.getOwner().removeBicycle(bicycle);
					newOwner.addBicycle(bicycle);
					bicycle.setOwner(newOwner);
				}
				if (!fail) {
					new MessageDialog("Ändringar har sparats!",
							"Ändringar sparade");
				} else {
					new MessageDialog (errMsg, "Sparning misslyckades");
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




