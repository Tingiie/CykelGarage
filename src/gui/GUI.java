package gui;

import garage.Bicycle;
import garage.BicycleGarageManager;
import garage.Entry;
import garage.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GUI {

	private MouseAdapter mouseAdapter;
	private BicycleGarageManager manager;
	private JFrame frame;
	private Resizer resizer;
	private JTable userTable, bicycleTable;
	private DefaultTableModel userCompareModel, bicycleCompareModel;
	private int lastSelectedUserRow;
	private Timer t;

	public GUI(final BicycleGarageManager manager) {
		setupMouseAdapter();
		this.manager = manager;
		frame = new JFrame();
		t = new Timer();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				t.cancel();
				manager.saveFiles();
			}
		});
		resizer = new Resizer();
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resizer.resize(frame.getHeight());
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar());
		frame.add(mainPanelInfo(), BorderLayout.NORTH);
		frame.add(mainPanel());
		frame.pack();
		frame.setMinimumSize(new Dimension(550, 170));
		resizer.addAll(frame.getHeight());
		frame.setVisible(true);
		updater();
	}

	private void setupMouseAdapter() {
		mouseAdapter = new MouseAdapter() {
			private Object lastSource;

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == userTable) {
					if (userTable.getSelectedRow() >= 0) {
						lastSelectedUserRow = userTable.getSelectedRow();
					}
				}
				if (e.getClickCount() > 0 && e.getClickCount() % 2 == 0
						&& e.getSource() == lastSource) {
					if (e.getSource() == userTable) {
						new UserInformation(manager,
								(User) ((DefaultTableModel) userTable
										.getModel()).getValueAt(
										lastSelectedUserRow, 0));
					} else if (e.getSource() == bicycleTable) {
						new BicycleInformation(manager,
								(Bicycle) ((DefaultTableModel) bicycleTable
										.getModel()).getValueAt(
										bicycleTable.getSelectedRow(), 0));
					}
				}
				lastSource = e.getSource();

			}
		};
	}

	private JPanel mainPanelInfo() {
		JPanel panel = new JPanel();
		JPanel innerPanel = new JPanel();
		GroupLayout layout = new GroupLayout(innerPanel);
		innerPanel.setLayout(layout);
		JLabel label1 = new JLabel("Incheckade användare");
		JLabel label2 = new JLabel("Incheckade cyklar");
		layout.setHorizontalGroup(layout.createSequentialGroup().addGap(10)
				.addComponent(label1).addGap(135).addComponent(label2));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(label1).addComponent(label2));
		panel.add(innerPanel);
		panel.setBorder(new EmptyBorder(3, 3, 0, 3));
		return panel;
	}

	private JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.add(userPane());
		panel.add(bicyclePane());
		return panel;
	}

	private JScrollPane userPane() {
		JScrollPane pane = new JScrollPane(userTable());
		resizer.queueHeightRelatedComponent(pane);
		pane.setPreferredSize(new Dimension(260, 440));
		return pane;
	}

	private JTable userTable() {
		userCompareModel = new DefaultTableModel();
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 6459215293160921435L;

			@Override
			public boolean isCellEditable(int nRow, int nCol) {
				return false;
			}
		};
		model.setColumnCount(0);
		model.addColumn("Namn");
		model.addColumn("Tid");
		userCompareModel.setColumnCount(0);
		userCompareModel.addColumn("Namn");
		userCompareModel.addColumn("Tid");
		model.setRowCount(0);
		long t = System.currentTimeMillis();
		for (Entry<User, Date> e : manager.getCheckedInUsers()) {
		long timeLeft = 900000 - t + e.getB().getTime();
			model.insertRow(model.getRowCount(), new Object[] { e.getA(),
					millisToTime(timeLeft) });
		}
		JTable table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.addMouseListener(mouseAdapter);
		userTable = table;
		return table;
	}

	private JScrollPane bicyclePane() {
		JScrollPane pane = new JScrollPane(bicycleTable());
		pane.setPreferredSize(new Dimension(260, 440));
		resizer.queueHeightRelatedComponent(pane);
		return pane;
	}

	private JTable bicycleTable() {
		bicycleCompareModel = new DefaultTableModel();
		DefaultTableModel model = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -8282859371862543876L;

			@Override
			public boolean isCellEditable(int nRow, int nCol) {
				return false;
			}
		};
		model.setColumnCount(0);
		model.addColumn("Cykel");
		model.addColumn("Ägare");
		bicycleCompareModel.setColumnCount(0);
		bicycleCompareModel.addColumn("Cykel");
		bicycleCompareModel.addColumn("Ägare");
		model.setRowCount(0);
		for (Bicycle b : manager.getCheckedInBicycles()) {
			model.insertRow(model.getRowCount(),
					new Object[] { b, b.getOwner() });
		}
		JTable table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.addMouseListener(mouseAdapter);
		bicycleTable = table;
		return table;
	}

	private JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuView());
		menuBar.add(menuSearch());
		menuBar.add(menuRegister());
		return menuBar;
	}

	private JMenu menuView() {
		JMenu menu = new JMenu("Visa");
		JMenuItem item;
		menu.setMnemonic(KeyEvent.VK_V);
		item = new JMenuItem("Garageinformation");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GarageInformation(manager);
			}
		});
		menu.add(item);
		item = new JMenuItem("Registrerade Användare");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegisteredUsers(manager);
			}
		});
		menu.add(item);
		item = new JMenuItem("Registrerade Cyklar");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegisteredBicycles(manager);
			}
		});
		menu.add(item);
		return menu;
	}

	private JMenu menuSearch() {
		JMenu menu = new JMenu("Sök");
		JMenuItem item;
		menu.setMnemonic(KeyEvent.VK_S);
		item = new JMenuItem("Sök Användare");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SearchUser(manager);
			}
		});
		menu.add(item);
		item = new JMenuItem("Sök Cykel");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SearchBicycle(manager);
			}
		});
		menu.add(item);
		return menu;
	}

	private JMenu menuRegister() {
		JMenu menu = new JMenu("Registrera");
		JMenuItem item;
		menu.setMnemonic(KeyEvent.VK_R);
		item = new JMenuItem("Registrera Användare");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegisterUser(manager);
			}
		});
		menu.add(item);
		item = new JMenuItem("Registrera Cykel");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegisterBicycle(manager);
			}
		});
		menu.add(item);
		item = new JMenuItem("Avregistrera Användare");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UnregisterUser(manager);
			}
		});
		menu.add(item);
		item = new JMenuItem("Avregistrera Cykel");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UnregisterBicycle(manager);

			}
		});
		menu.add(item);
		menu.add(item);
		return menu;
	}

	

	private void rebuildUserTable() {
		DefaultTableModel model = ((DefaultTableModel) userTable.getModel());
		model.setRowCount(0);
		long t = System.currentTimeMillis();
		for (Entry<User, Date> e : manager.getCheckedInUsers()) {
		long timeLeft = 900000 - t + e.getB().getTime();
			model.insertRow(model.getRowCount(), new Object[] { e.getA(),
					millisToTime(timeLeft) });
		}
	}

	private void rebuildBicycleTable() {

		DefaultTableModel model = ((DefaultTableModel) bicycleTable.getModel());
		model.setRowCount(0);
		for (Bicycle b : manager.getCheckedInBicycles()) {
			model.insertRow(model.getRowCount(),
					new Object[] { b, b.getOwner() });
		}
	}

	private void updater() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				DefaultTableModel oldModel = ((DefaultTableModel) userTable
						.getModel());
				userCompareModel.setRowCount(0);
				for (Entry<User, Date> e : manager.getCheckedInUsers()) {
					userCompareModel.insertRow(userCompareModel.getRowCount(),
							new Object[] { e.getA(), e.getB() });
				}
				if (userCompareModel.getRowCount() != 0
						|| oldModel.getRowCount() != 0) {
					rebuildUserTable();
				}

				oldModel = ((DefaultTableModel) bicycleTable.getModel());
				bicycleCompareModel.setRowCount(0);
				for (Bicycle b : manager.getCheckedInBicycles()) {
					bicycleCompareModel.insertRow(
							bicycleCompareModel.getRowCount(), new Object[] {
									b, b.getOwner() });
				}
				if (bicycleCompareModel.getRowCount() != oldModel.getRowCount()) {
					rebuildBicycleTable();
				} else {
					boolean check = false;
					out: for (int y = 0; y < bicycleCompareModel.getRowCount(); y++) {
						for (int x = 0; x < bicycleCompareModel
								.getColumnCount(); x++) {
							if (bicycleCompareModel.getValueAt(y, x) != oldModel
									.getValueAt(y, x)) {
								check = true;
								break out;
							}
						}
					}
					if (check) {
						rebuildBicycleTable();
					}
				}
			}
		}, 0, 500);
	}

	private String millisToTime(long millis) {
		int seconds = (int) (millis / 1000l);
		int minutes = seconds / 60;
		seconds %= 60;
		int hours = minutes / 60;
		minutes %= 60;
		String time = (hours > 9 ? String.valueOf(hours) : "0"
				+ (hours > 0 ? String.valueOf(hours) : "0"))
				+ ":"
				+ (minutes > 9 ? String.valueOf(minutes) : "0"
						+ (minutes > 0 ? String.valueOf(minutes) : "0"))
				+ ":"
				+ (seconds > 9 ? String.valueOf(seconds) : "0"
						+ (seconds > 0 ? String.valueOf(seconds) : "0"));
		return time;
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
			frame.setSize(new Dimension(550, 500));
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


