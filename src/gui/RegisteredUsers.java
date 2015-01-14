package gui;

import garage.BicycleGarageManager;
import garage.Entry;
import garage.User;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RegisteredUsers {

	private MouseAdapter mouseAdapter;
	private BicycleGarageManager manager;
	private JFrame frame;
	private Resizer resizer;
	private JTable userTable;
	private DefaultTableModel compareModel;
	private Timer t;

	public RegisteredUsers(BicycleGarageManager manager) {
		setupMouseAdapter();
		this.manager = manager;
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

		frame.add(mainPanelInfo());

		frame.pack();
		resizer.addAll(frame.getHeight());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		updater();
	}

	private JPanel mainPanelInfo() {
		JPanel panel = new JPanel();
		panel.add(userPane());
		return panel;
	}

	private JScrollPane userPane() {
		JScrollPane pane = new JScrollPane(userTable());
		resizer.queueHeightRelatedComponent(pane);
		pane.setPreferredSize(new Dimension(1100, 440));
		return pane;
	}

	private JTable userTable() {
		compareModel = new DefaultTableModel();
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 8149854095318817484L;

			@Override
			public boolean isCellEditable(int nRow, int nCol) {
				return false;
			}
		};
		model.setColumnCount(0);
		model.addColumn("Namn");
		model.addColumn("Personnummer");
		model.addColumn("Telefonnummer");
		model.addColumn("Mailadress");
		model.addColumn("PIN-kod");
		model.addColumn("Cykel 1");
		model.addColumn("Cykel 2");
		model.addColumn("Incheckningsstatus");
		compareModel.setColumnCount(0);
		compareModel.addColumn("Namn");
		compareModel.addColumn("Personnummer");
		compareModel.addColumn("Telefonnummer");
		compareModel.addColumn("Mailadress");
		compareModel.addColumn("PIN-kod");
		compareModel.addColumn("Cykel 1");
		compareModel.addColumn("Cykel 2");
		compareModel.addColumn("Incheckningsstatus");
		model.setRowCount(0);
		for (User u : manager.getUsers()) {
			model.insertRow(
					model.getRowCount(),
					new Object[] { u, u.getPersonNumber(), u.getPhoneNumber(),
							u.getMailAddress(), u.getPinCode(),
							u.getBicycle(0), u.getBicycle(1),
							u.getGarageStatus() });
		}
		JTable table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		table.getColumnModel().getColumn(6).setPreferredWidth(60);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.addMouseListener(mouseAdapter);
		userTable = table;
		return table;
	}

	private void setupMouseAdapter() {
		mouseAdapter = new MouseAdapter() {
			private Object lastSource;

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0 && e.getClickCount() % 2 == 0
						&& e.getSource() == lastSource) {
					new UserInformation(manager,
							(User) ((DefaultTableModel) userTable.getModel())
									.getValueAt(userTable.getSelectedRow(), 0));
				}
				lastSource = e.getSource();
			}
		};
	}

	private void rebuildTable() {
		DefaultTableModel model = ((DefaultTableModel) userTable.getModel());
		model.setRowCount(0);
		for (User u : manager.getUsers()) {
			model.insertRow(
					model.getRowCount(),
					new Object[] { u, u.getPersonNumber(), u.getPhoneNumber(),
							u.getMailAddress(), u.getPinCode(),
							u.getBicycle(0), u.getBicycle(1),
							u.getGarageStatus() });
		}
	}

	private void updater() {
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				DefaultTableModel oldModel = ((DefaultTableModel) userTable
						.getModel());
				compareModel.setRowCount(0);
				for (User u : manager.getUsers()) {
					compareModel.insertRow(
							compareModel.getRowCount(),
							new Object[] { u, u.getPersonNumber(),
									u.getPhoneNumber(), u.getMailAddress(),
									u.getPinCode(), u.getBicycle(0),
									u.getBicycle(1), u.getGarageStatus() });
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
			frame.setSize(new Dimension(1120, 500));
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


