package ClassAdminFrontEnd;

import javax.swing.*;

import org.tmatesoft.sqljet.core.SqlJetException;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import Frames.Frame;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.controls.ControlAdapter;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tree;
import prefuse.visual.VisualItem;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PopUpMenu {

	JPopupMenu pMenu;
	TreeView tview;
	VisualItem activeItem = null;
	Tree activeTree = null;
	Project activeProject;
	JDialog frame = null;

	public PopUpMenu() {
		activeProject = Global.getGlobal().getActiveProject();
		pMenu = new JPopupMenu();
		JMenuItem miAddChild = new JMenuItem("Add Child");
		pMenu.add(miAddChild);
		JMenuItem miEdit = new JMenuItem("Edit");
		pMenu.add(miEdit);
		JMenuItem miRemoveWC = new JMenuItem("Remove with children");
		pMenu.add(miRemoveWC);
		JMenuItem miRemoveWOC = new JMenuItem("Remove without children");
		pMenu.add(miRemoveWOC);

		miAddChild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewNode(activeItem.getRow());
			}
		});

		miEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		miRemoveWC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTree.removeNode(activeItem.getRow());
			}
		});

		miRemoveWOC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTree.removeNode(activeItem.getRow());
			}
		});

	}

	public void setTreeView(TreeView treeView, Tree tree) {
		activeTree =  tree;
		tview = treeView;
		tview.addControlListener(new ControlAdapter() {
			public void itemReleased(VisualItem item, MouseEvent e) {
				activeItem = null;
				if (e.isPopupTrigger()) {
					if (item.canGetString("name")) {
						pMenu.show(e.getComponent(), e.getX(), e.getY());
						activeItem = item;
						System.out.println(item.getString("name"));
					}
				}
			}
		});
	}

	private void createNewNode(int iParent){
		final Table nodes = activeTree.getNodeTable();
		try {
			frame = new JDialog(new Frame(), true);
		} catch (SqlJetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JPanel pnlRad = new JPanel(new GridLayout(1, 2));
		frame.setSize(600, 600);

		frame.setLayout(new GridLayout(6, 2));

		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		final JLabel lblName = new JLabel("Name:");
		final JComboBox cmbParent = new JComboBox();
		final JLabel lblParent = new JLabel("Parent:");
		final JTextField txtName = new JTextField();
		final JLabel lblText = new JLabel("Text field");
		final JRadioButton rblYes = new JRadioButton("Yes");
		final JRadioButton rblNo = new JRadioButton("No");
		final ButtonGroup group = new ButtonGroup();
		final JLabel lblDate = new JLabel("Date of assesment:");
		final JTextArea txtDate = new JTextArea();
		final JButton btnDate = new JButton("change");
		final JLabel lblWeight = new JLabel("Weight");
		JButton btnAdd = new JButton("Add");
		JButton btnClose = new JButton("Close");
		SpinnerNumberModel snmWeight = new SpinnerNumberModel(new Double(1.00), // value
				new Double(0.00), // min
				new Double(100.00), // max
				new Double(0.01)); // step
		final JSpinner txtWeight = new JSpinner(snmWeight);

		txtName.setSize(120, 30);
		txtDate.setText(dateFormat.format(date));
		rblNo.setSelected(true);
		group.add(rblYes);
		group.add(rblNo);

		for (int r = 0; r < nodes.getRowCount(); r++) {
			for (int c = 0; c < nodes.getColumnCount(); c++) {
				cmbParent.addItem(nodes.getString(r, c));
			}
		}

		btnDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					txtDate.setText(new DatePicker(new Frame()).setPickedDate());
				} catch (SqlJetException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean b = true;
				boolean isText = false;
				Node parent = null;
				Node child = null;
				String assDate = "";
				double weight = 1.0;

				int selectedIndex = cmbParent.getSelectedIndex();
				if (selectedIndex == 0) {
					b = false;
					lblParent.setForeground(Color.red);
				}

				if (txtName.getText() == null || txtName.getText().equals("")) {
					b = false;
					lblName.setForeground(Color.red);
				}

				if (rblYes.isSelected())
					isText = true;

				Date d = null;
				try {
					d = dateFormat.parse(txtDate.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (d == null) {
					b = false;
					lblDate.setForeground(Color.RED);
				}

				if (b) {
					parent = activeTree.getNode(cmbParent.getSelectedIndex()); // get
																			// parent
					child = activeTree.addNode(); // create child

					for (int c = 0; c < child.getColumnCount() - 1; c++)
						// copy parent data
						child.set(c, parent.get(c));

					child.set("name", txtName.getText()); // edit child to fit
															// new child

					activeTree.addEdge(parent, child); // add edge between parent
													// and child

					// add child to parent in back end
					EntityType newE = new EntityType(txtName.getText(), activeProject
							.getTreeLinkedList().get(
									cmbParent.getSelectedIndex()),
							isText, d, (Double) txtWeight.getValue());

					activeProject.getTreeLinkedList().add(newE);
					newE.populateTreeWithEntities();

					// refresh cmbParent content
					cmbParent.removeAllItems();
					for (int r = 0; r < nodes.getRowCount(); r++) {
						for (int c = 0; c < nodes.getColumnCount(); c++) {
							cmbParent.addItem(nodes.getString(r, c));
						}
					}

					// set selected index to what it was
					cmbParent.setSelectedIndex(selectedIndex);

					// reset all values
					rblNo.setSelected(true);
					txtWeight.setValue(new Double(1.0));
					txtName.setText(null);
					txtDate.setText(dateFormat.format(new Date()));
					lblParent.setForeground(Color.BLACK);
					lblName.setForeground(Color.BLACK);
					lblDate.setForeground(Color.BLACK);
					isText = false;
				}// if b
			}// actionListener
		});

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activeProject.updateTables();
				frame.dispose();
			}
		});

		if (iParent != -1)
			cmbParent.setSelectedIndex(iParent);
		else
			cmbParent.setSelectedIndex(0);

		JPanel pnlDate = new JPanel(new GridLayout(1, 2));
		pnlDate.add(txtDate);
		pnlDate.add(btnDate);
		frame.add(lblParent);
		frame.add(cmbParent);
		frame.add(lblName);
		frame.add(txtName);
		frame.add(lblText);
		pnlRad.add(rblYes);
		pnlRad.add(rblNo);
		frame.add(pnlRad);
		frame.add(lblDate);
		frame.add(pnlDate);
		frame.add(lblWeight);
		frame.add(txtWeight);
		frame.add(btnAdd);
		frame.add(btnClose);
		frame.pack();
		frame.setVisible(true);

	}
}