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
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.DatePicker;
import ClassAdminFrontEnd.TreeView;

public class FrmNewNode {
	private Tree activeTree = null;
	private JDialog frame = null;
	private Project activeProject = null;
	private TreeView activeTreeView = null;
	private LinkedList<EntityType> activeTreeLinkedList = null;
	private Table nodes;
	private JComboBox cmbParent;
	private JFrame parentF = null;
	private BackgroundGradientPanel backgroundPanel;
	final JSpinner Nspinner = new JSpinner(new SpinnerNumberModel(1.0, 1.0,
			100.0, 1.0));

	public FrmNewNode(Tree tree, Project project, JFrame parentFrame,
			TreeView treeView) {
		activeTree = tree;
		activeProject = project;
		activeTreeView = treeView;
		nodes = activeTree.getNodeTable();
		parentF = parentFrame;

		frame = new JDialog(parentFrame, true);
		frame.setSize(356, 268);
		frame.setLocation(parentF.getWidth() + parentF.getX() - 250,
				parentF.getY() + 40);
		frame.getContentPane().setLayout(null);

		frame.setTitle("Add New Node");

		JPanel contentPane = new JPanel();
		contentPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, contentPane.getWidth(),
				contentPane.getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		final JLabel lblName = new JLabel("Name:");
		cmbParent = new JComboBox();
		final JLabel lblParent = new JLabel("Parent:");
		final JTextField txtName = new JTextField();
		final ButtonGroup group = new ButtonGroup();
		final JLabel lblDate = new JLabel("Date of assesment:");
		final JTextArea txtDate = new JTextArea();
		final JButton btnDate = new JButton("change");
		final JLabel lblWeight = new JLabel("Weight");
		JButton btnAdd = new JButton("Add");
		JButton btnClose = new JButton("Close");
		final JLabel lblType = new JLabel("Type:");
		final JComboBox cmbType = new JComboBox();
		Nspinner.setVisible(false);

		SpinnerNumberModel snmWeight = new SpinnerNumberModel(new Double(1.00), // value
				new Double(0.00), // min
				new Double(100.00), // max
				new Double(0.01)); // step
		final JSpinner txtWeight = new JSpinner(snmWeight);

		txtName.setSize(120, 30);
		txtDate.setText(dateFormat.format(date));

		cmbType.addItem("Mark - Weighted Average");
		cmbType.addItem("Mark - Sum ");
		cmbType.addItem("Mark - Best N");
		cmbType.addItem("Text");
		cmbType.addItem("Mixed");

		cmbType.setSelectedIndex(0);

		cmbType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cmbType.getSelectedIndex() == 2) {
					Nspinner.setVisible(true);
				} else
					Nspinner.setVisible(false);
			}
		});

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

				// reset formatting
				lblParent.setForeground(new Color(0xEDEDED));
				lblName.setForeground(new Color(0xEDEDED));
				lblDate.setForeground(new Color(0xEDEDED));

				// validation of entered values
				int selectedIndex = cmbParent.getSelectedIndex();
				if (selectedIndex == 0) {
					b = false;
					lblParent.setForeground(Color.red);
				}

				if (txtName.getText() == null || txtName.getText().equals("")) {
					b = false;
					lblName.setForeground(Color.red);
				}

				Date d = null;
				try {
					d = dateFormat.parse(txtDate.getText());
				} catch (Exception e) {
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

					child.set("name", txtName.getText()); // update child node
					// information

					activeTree.addEdge(parent, child); // add edge between
														// parent
														// and child

					// add child to parent in back end
					EntityType newE = new EntityType(txtName.getText(),
							activeProject.getTreeLinkedList().get(
									cmbParent.getSelectedIndex()), isText, d,
							(Double) txtWeight.getValue());
					if (Nspinner.isVisible())
						newE.setN((int)Double.parseDouble(Nspinner.getValue()
								.toString()));

					// insert child into backend and create audit entry
					activeProject.getAudit().AddNode(
							(String) cmbParent.getSelectedItem(),
							txtName.getText());
					activeProject.getTreeLinkedList().add(newE);
					newE.populateTreeWithEntities();
					newE.setEntityTypeClass(cmbType.getSelectedIndex());

					// update front end information
					activeTreeView.getVisualization().run("filter");

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
					txtWeight.setValue(new Double(1.0));
					txtName.setText(null);
					txtDate.setText(dateFormat.format(new Date()));
					isText = false;
					txtName.requestFocus(true);
				}// if b
			}// actionListener
		});

		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		JPanel pnlDate = new JPanel(new GridLayout(1, 2));
		pnlDate.setOpaque(false);
		pnlDate.add(txtDate);
		pnlDate.add(btnDate);

		lblParent.setBounds(10, 45, 91, 30);
		cmbParent.setBounds(180, 45, 150, 30);
		txtName.setBounds(180, 79, 150, 30);
		lblName.setBounds(10, 79, 72, 30);
		lblDate.setBounds(10, 120, 120, 30);
		lblWeight.setBounds(10, 147, 130, 30);
		pnlDate.setBounds(180, 113, 150, 30);
		txtWeight.setBounds(180, 147, 150, 30);
		btnAdd.setBounds(10, 188, 150, 30);
		btnClose.setBounds(180, 188, 150, 30);
		lblType.setBounds(10, 11, 50, 30);
		cmbType.setBounds(180, 11, 150, 30);
		Nspinner.setBounds(125, 11, 50, 30);

		lblParent.setForeground(new Color(0xEDEDED));
		lblName.setForeground(new Color(0xEDEDED));
		lblDate.setForeground(new Color(0xEDEDED));
		lblWeight.setForeground(new Color(0xEDEDED));
		lblType.setForeground(new Color(0xEDEDED));

		backgroundPanel.add(lblParent);
		backgroundPanel.add(cmbParent);
		backgroundPanel.add(lblName);
		backgroundPanel.add(txtName);
		backgroundPanel.add(lblDate);
		backgroundPanel.add(pnlDate);
		backgroundPanel.add(lblWeight);
		backgroundPanel.add(txtWeight);
		backgroundPanel.add(btnAdd);
		backgroundPanel.add(btnClose);
		backgroundPanel.add(lblType);
		backgroundPanel.add(cmbType);
		backgroundPanel.add(Nspinner);
	}

	public void showFrmNewNode(int p) {
		if (p != -1)
			cmbParent.setSelectedIndex(p);
		else
			cmbParent.setSelectedIndex(0);
		frame.setVisible(true);
	}

}
