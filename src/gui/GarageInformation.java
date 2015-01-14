package gui;

import garage.BicycleGarageManager;
import garage.Entry;
import garage.User;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GarageInformation {

	private MouseAdapter mouseAdapter;
	private BicycleGarageManager manager;
	private JFrame frame;
	private Resizer resizer;
	private JTable userTable;
	private DefaultTableModel compareModel;
	private JLabel checkedInUsers, checkedInBicycles, registeredUsers, registeredBicycles, freeSpace, usedSpace;
	private JTextField totalSpace;
	private LinkedList<User> queue;
	private Timer t;

	public GarageInformation(BicycleGarageManager manager) {
		setupMouseAdapter();
		this.manager = manager;
		queue = manager.getQueue();
		frame = new JFrame();
		t = new Timer();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				t.cancel();
			}
		});
		resizer = new Resizer();
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resizer.resize(frame.getHeight());
			}
		});
		frame.add(mainPanelInfo(), BorderLayout.NORTH);
		frame.add(mainPanel(), BorderLayout.WEST);
		frame.add(infoPanel(), BorderLayout.EAST);
		frame.pack();
		frame.setMinimumSize(new Dimension(560, 170));
		resizer.addAll(frame.getHeight());
		frame.setVisible(true);
		updater();
	}

	private void setupMouseAdapter() {
		mouseAdapter = new MouseAdapter() {
			private Object lastSource;

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0 && e.getClickCount() % 2 == 0
						&& e.getSource() == lastSource) {
					User u = (User) ((DefaultTableModel) userTable.getModel())
							.getValueAt(userTable.getSelectedRow(), 0);
					Object[] options = { "ja", "nej" };
					Image image = new BufferedImage(1, 1,
							BufferedImage.TYPE_INT_ARGB);
					if (JOptionPane.showOptionDialog(frame, "Ta bort " + u
							+ " från platskön?", "Borttagning från kö",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, new ImageIcon(image),
							options, options[0]) == 0) {
						queue.remove(u);
					}
				}
				lastSource = e.getSource();

			}
		};
	}

	private JPanel mainPanelInfo() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel innerPanel = new JPanel();
		GroupLayout layout = new GroupLayout(innerPanel);
		innerPanel.setLayout(layout);
		JLabel label1 = new JLabel("Platskö");
		layout.setHorizontalGroup(layout.createSequentialGroup().addGap(10)
				.addComponent(label1));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.BASELINE).addComponent(label1));
		panel.add(innerPanel);
		panel.setBorder(new EmptyBorder(3, 3, 0, 3));
		return panel;
	}

	private JPanel mainPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(userPane());
		return panel;
	}

	private JScrollPane userPane() {
		JScrollPane pane = new JScrollPane(userTable());
		resizer.queueHeightRelatedComponent(pane);
		pane.setPreferredSize(new Dimension(300, 440));
		return pane;
	}

	private JTable userTable() {
		compareModel = new DefaultTableModel();
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = -251487253121077524L;

			@Override
			public boolean isCellEditable(int nRow, int nCol) {
				return false;
			}
		};
		model.setColumnCount(0);
		model.addColumn("Namn");
		model.addColumn("Personnummer");
		compareModel.setColumnCount(0);
		compareModel.addColumn("Namn");
		compareModel.addColumn("Personnummer");
		model.setRowCount(0);
		for (User u : queue) {
			model.insertRow(model.getRowCount(),
					new Object[] { u, u.getPersonNumber() });
		}
		JTable table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.addMouseListener(mouseAdapter);
		userTable = table;
		return table;
	}

	private JPanel infoPanel() {
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		Component b0 = new JLabel("Incheckade användare:");
		Component b1 = new JLabel("Incheckade cyklar:");
		Component b2 = new JLabel("Totalt antal platser:");
		Component b3 = new JLabel("Lediga platser:");
		Component b4 = new JLabel("Upptagna platser:");
		Component b5 = new JLabel("Registrerade användare:");
		Component b6 = new JLabel("Registrerade cyklar:");
		Component c0 = checkedInUsers();
		Component c1 = checkedInBicycles();
		Component c2 = totalSpace();
		Component c3 = freeSpace();
		Component c4 = usedSpace();
		Component c5 = registeredUsers();
		Component c6 = registeredBicycles();

		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.LEADING).addComponent(b5)
								.addComponent(b6).addComponent(b2)
								.addComponent(b0).addComponent(b1)
								.addComponent(b3).addComponent(b4))
				.addGap(5)
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.TRAILING)
								.addComponent(c5).addComponent(c6)
								.addComponent(c0).addComponent(c1)
								.addComponent(c2).addComponent(c3)
								.addComponent(c4)));

		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(b5).addComponent(c5))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(b6).addComponent(c6))
				.addGap(30)
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(b0).addComponent(c0))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(b1).addComponent(c1))
				.addGap(120)
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(b2).addComponent(c2))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(b3).addComponent(c3))
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(b4).addComponent(c4)));
		return panel;
	}
	
	private JLabel checkedInUsers() {
		checkedInUsers = new JLabel(String.valueOf(manager.getCheckedInUsers()
				.size()));
		return checkedInUsers;
	}

	private JLabel checkedInBicycles() {
		checkedInBicycles = new JLabel(String.valueOf(manager
				.getCheckedInBicycles().size()));
		return checkedInBicycles;
	}

	private JLabel registeredUsers() {
		registeredUsers = new JLabel(String.valueOf(manager.getUsers().size()));
		return registeredUsers;
	}

	private JLabel registeredBicycles() {
		registeredBicycles = new JLabel(String.valueOf(manager.getBicycles()
				.size()));
		return registeredBicycles;
	}

	private JTextField totalSpace() {
		totalSpace = new FocusTextField(String.valueOf(manager
				.getRegisterLimit()), 6);
		totalSpace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int n = Integer.parseInt(totalSpace.getText());
					if (manager.getRegisterLimit() <= Integer.parseInt(usedSpace.getText()) && n > Integer.parseInt(usedSpace.getText()) && queue.size() > 0) {
						new MessageDialog(
								"Första användaren i kön bör kontaktas angående ledig plats",
								"Ny plats ledig");
					}
					manager.setRegisterLimit(n);
				} catch (NumberFormatException e0) {
					totalSpace.setText(String.valueOf(manager
							.getRegisterLimit()));
				}
			}
		});
		return totalSpace;
	}

	private JLabel freeSpace() {
		freeSpace = new JLabel(String.valueOf(manager.getRegisterLimit()
				- manager.getCheckedInBicycles().size()));
		return freeSpace;
	}

	private JLabel usedSpace() {
		usedSpace = new JLabel(String.valueOf(manager.getCheckedInBicycles()
				.size()));
		return usedSpace;
	}
	
	private void rebuildTable() {
		DefaultTableModel model = ((DefaultTableModel) userTable.getModel());
		model.setRowCount(0);
		for (User u : queue) {
			model.insertRow(model.getRowCount(),
					new Object[] { u, u.getPersonNumber() });
		}
	}

	private void updater() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				String s = String.valueOf(manager.getCheckedInUsers().size());
				if (checkedInUsers.getText() != s) {
					checkedInUsers.setText(s);
				}
				s = String.valueOf(manager.getCheckedInBicycles().size());
				if (checkedInBicycles.getText() != s) {
					checkedInBicycles.setText(s);
				}
				s = String.valueOf(manager.getUsers().size());
				if (registeredUsers.getText() != s) {
					registeredUsers.setText(s);
				}
				s = String.valueOf(manager.getBicycles().size());
				if (registeredBicycles.getText() != s) {
					registeredBicycles.setText(s);
				}
				s = String.valueOf(manager.getRegisterLimit());
				if (totalSpace.getText() != s && !totalSpace.isFocusOwner()) {
					totalSpace.setText(s);
				}
				s = String.valueOf(manager.getRegisterLimit()
						- manager.getBicycles().size());
				if (freeSpace.getText() != s) {
					freeSpace.setText(s);
				}
				s = String.valueOf(manager.getBicycles().size());
				if (usedSpace.getText() != s) {
					usedSpace.setText(s);
				}
				DefaultTableModel oldModel = ((DefaultTableModel) userTable
						.getModel());
				compareModel = new DefaultTableModel();
				compareModel.setRowCount(0);
				for (User u : queue) {
					compareModel.insertRow(compareModel.getRowCount(),
							new Object[] { u, u.getPersonNumber() });
				}
				if (compareModel.getRowCount() != oldModel.getRowCount()) {
					rebuildTable();
				} else {
					boolean check = false;
					out: for (int y = 0; y < compareModel.getRowCount(); y++) {
						for (int x = 0; x < compareModel.getColumnCount(); x++) {
							if (compareModel.getValueAt(y, x) != oldModel.getValueAt(
									y, x)) {
								check = true;
								break out;
							}
						}
					}
					if (check) {
						rebuildTable();
					}
				}
			}
		}, 0, 500);
	}

	private class Resizer {
		private LinkedList<Entry<JComponent, Integer>> heightRelatedComponents;
		private LinkedList<JComponent> heightRelatedComponentsQueue;

		private Resizer() {
			heightRelatedComponents = new LinkedList<Entry<JComponent, Integer>>();
			heightRelatedComponentsQueue = new LinkedList<JComponent>();
		}

		private void queueHeightRelatedComponent(JComponent component) {
			heightRelatedComponentsQueue.add(component);
		}

		private void addAll(int relatedHeight) {
			for (JComponent c : heightRelatedComponentsQueue) {
				heightRelatedComponents.add(new Entry<JComponent, Integer>(c,
						relatedHeight - c.getHeight()));
			}
			frame.setSize(new Dimension(620, 400));
			frame.setLocationRelativeTo(null);
		}

		private void resize(int relatedHeight) {
			for (Entry<JComponent, Integer> e : heightRelatedComponents) {
				JComponent c = (JComponent) e.getA();
				c.setPreferredSize(new Dimension(c.getWidth(), relatedHeight
						- (Integer) e.getB()));
			}
		}
	}
}







