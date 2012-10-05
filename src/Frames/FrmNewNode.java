package Frames;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.DatePicker;
import ClassAdminFrontEnd.TreeView;

import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tree;

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
	
	public FrmNewNode(Tree tree, Project project, JFrame parentFrame, TreeView treeView) {
		activeTree = tree;
		activeProject = project;
		activeTreeView = treeView;
		nodes = activeTree.getNodeTable();
		parentF = parentFrame;
		frame = new JDialog(parentFrame, true);
	
		JPanel pnlRad = new JPanel(new GridLayout(1, 2));
		pnlRad.setOpaque(false);
		frame.setSize(356, 273);
		frame.setLocation(parentF.getWidth()+parentF.getX()-250, parentF.getY()+40);
		frame.getContentPane().setLayout(null);
		
		frame.setTitle("Add New Node");
		
		JPanel contentPane = new JPanel();
		contentPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, contentPane.getWidth(), contentPane.getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);

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
					
					//create audit entry
					activeProject.getAudit().AddNode((String) cmbParent.getSelectedItem(), txtName.getText());
					//backend func//					
					activeProject.getTreeLinkedList().add(newE);
					newE.populateTreeWithEntities();
					activeProject.updateTables();
					
					
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
		pnlDate.setOpaque(false);
		pnlDate.add(txtDate);
		pnlDate.add(btnDate);
		
		lblParent.setBounds(10, 11, 91, 30);
		cmbParent.setBounds(180, 11, 150, 30);
		txtName.setBounds(180, 45, 150, 30);
		lblName.setBounds(10, 43, 72, 30);
		lblText.setBounds(10, 80, 54, 30);
		pnlRad.setBounds(180, 80, 150, 30);
		lblDate.setBounds(10, 116, 120, 30);
		lblWeight.setBounds(10, 157, 130, 30);
		pnlDate.setBounds(180,115,150,30);
		txtWeight.setBounds(180, 150, 150, 30);
		btnAdd.setBounds(10, 198, 150, 30);
		btnClose.setBounds(180, 198, 150, 30);
		
		lblParent.setForeground(new Color(0xEDEDED));
		lblName.setForeground(new Color(0xEDEDED));
		lblText.setForeground(new Color(0xEDEDED));
		rblYes.setForeground(new Color(0xEDEDED));
		rblNo.setForeground(new Color(0xEDEDED));
		lblDate.setForeground(new Color(0xEDEDED));
		lblWeight.setForeground(new Color(0xEDEDED));
		
		backgroundPanel.add(lblParent);
		backgroundPanel.add(cmbParent);
		backgroundPanel.add(lblName);
		backgroundPanel.add(txtName);
		backgroundPanel.add(lblText);
		pnlRad.add(rblYes);
		pnlRad.add(rblNo);
		backgroundPanel.add(pnlRad);
		backgroundPanel.add(lblDate);
		backgroundPanel.add(pnlDate);
		backgroundPanel.add(lblWeight);
		backgroundPanel.add(txtWeight);
		backgroundPanel.add(btnAdd);
		backgroundPanel.add(btnClose);
	}
	
	public void showFrmNewNode(int p){
		if (p != -1)
			cmbParent.setSelectedIndex(p);
		else
			cmbParent.setSelectedIndex(0);
		frame.setVisible(true);
	}

	
	
	
}