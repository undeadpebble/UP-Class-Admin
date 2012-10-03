package Frames;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.activity.ActivityCompletedException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.tmatesoft.sqljet.core.SqlJetException;

import prefuse.Visualization;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tree;
import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.DatePicker;
import ClassAdminFrontEnd.TreeView;

public class FrmNewNode {
	Tree activeTree = null;
	JDialog frame = null;
	Project activeProject = null;
	TreeView activeTreeView = null;
	LinkedList<EntityType> activeTreeLinkedList = null;
	Table nodes;
	JComboBox cmbParent;
	JFrame parentF = null;
	
	public FrmNewNode(Tree tree, Project project, JFrame parentFrame, TreeView treeView) {
		activeTree = tree;
		activeProject = project;
		activeTreeView = treeView;
		nodes = activeTree.getNodeTable();
		parentF = parentFrame;
		frame = new JDialog(parentFrame, true);
	
		JPanel pnlRad = new JPanel(new GridLayout(1, 2));
		frame.setSize(600, 600);
		frame.setLayout(new GridLayout(6, 2));

		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		final JLabel lblName = new JLabel("Name:");
		cmbParent = new JComboBox();
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
					txtDate.setText(new DatePicker(frame).setPickedDate());
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

					activeTree.addEdge(parent, child); // add edge between
														// parent
														// and child

					// add child to parent in back end
					EntityType newE = new EntityType(txtName.getText(), activeProject.getTreeLinkedList().get(cmbParent.getSelectedIndex()), isText, d, (Double) txtWeight.getValue());
					System.out.println(activeProject.getTreeLinkedList().get(cmbParent.getSelectedIndex()).getName());
					activeProject.getAudit().AddNode((String) cmbParent.getSelectedItem(), txtName.getText());
					//backend func//					
					activeProject.getTreeLinkedList().add(newE);
					newE.populateTreeWithEntities();
					//activeProject.updateTables();
					
					
					LinkedList<LinkedList<SuperEntity>> data = activeProject.getTreeLinkedList().get(cmbParent.getSelectedIndex()).getParentEntitytype()
							.getParentEntitytype().getEntityList().get(0)
							.getDataLinkedList();
					System.out.println();
					for (int x = 0; x < data.size(); ++x) {
						for (int y = 0; y < data.get(x).size(); ++y) {
							String g = "";
							SuperEntity s = data.get(x).get(y);
							while (s != null) {
								g += " ";
								s = s.getParentEntity();
							}
							System.out.println(g
									+ data.get(x).get(y).getType().getName());
						}
					}

					
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
					activeTreeView.getVisualization().run("filter");
					txtName.requestFocus(true);
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


		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				activeProject.updateTables();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
				txtName.requestFocus(true);
			}
		});

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
	}
	
	public void showFrmNewNode(int p){
		if (p != -1)
			cmbParent.setSelectedIndex(p);
		else
			cmbParent.setSelectedIndex(0);
		frame.setVisible(true);
	}

	
	
	
}