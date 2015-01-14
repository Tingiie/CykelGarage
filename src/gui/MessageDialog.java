package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class MessageDialog {
	private JFrame frame;

	public MessageDialog(String message, String title) {
		frame = new JFrame(title);
		
		frame.add(createMessagePanel(message), BorderLayout.CENTER);
		frame.add(createButtonPanel(), BorderLayout.SOUTH);

		int height = 145 + 20 * message.length() / 35;
		frame.setSize(340, height);
		frame.setMinimumSize(new Dimension(250, 100));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createMessagePanel(String message) {
		JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JTextArea textArea = new JTextArea(message);
		textArea.setBackground(new Color(237, 237, 237));
		textArea.setFocusable(false);
		int areaHeight = 50 + 25 * message.length() / 50;
		textArea.setSize(245, areaHeight);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));

		messagePanel.add(textArea);
		messagePanel.setBorder(new EmptyBorder(30, 0, 15, 0));
		int panelHeight = 50 + 25 * message.length() / 50;
		messagePanel.setPreferredSize(new Dimension(150, panelHeight));
		return messagePanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
		buttonPanel.add(createOkButton());
		return buttonPanel;
	}
	
	private JButton createOkButton() {
		JButton okButton = new JButton("ok");
		okButton.setFocusPainted(false);
		okButton.setBackground(Color.white);
		okButton.setPreferredSize(new Dimension(90, 25));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		return okButton;
	}
}
