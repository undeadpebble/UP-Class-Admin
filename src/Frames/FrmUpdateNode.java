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

import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tree;
import prefuse.visual.VisualItem;
import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminFrontEnd.DatePicker;
import ClassAdminFrontEnd.TreeView;

public class FrmUpdateNode {
	Tree activeTree = null;
	JDialog frame = null;
	Project activeProject = null;
	TreeView activeTreeView = null;
	LinkedList<EntityType> activeTreeLinkedList = null;
	Table nodes;
	JFrame parentF = null;
	EntityType activeentity;
	VisualItem activeItem;
	
	public FrmUpdateNode(Project project, JFrame parentFrame,EntityType entity, VisualItem visualItem) {
		activeProject = project;
		parentF = parentFrame;
		activeentity = entity;
		activeItem = visualItem;
		
		
		frame = new JDialog(parentFrame, true);
		
		JPanel pnlRad = new JPanel(new GridLayout(1, 2));
		frame.setSize(600, 600);
		frame.setLayout(new GridLayout(6, 2));

		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		final JLabel lblName = new JLabel("Name:");
		final JTextField txtName = new JTextField(activeentity.getName());
		final JLabel lblText = new JLabel("Text field");
		final JRadioButton rblYes = new JRadioButton("Yes");
		final JRadioButton rblNo = new JRadioButton("No");
		final ButtonGroup group = new ButtonGroup();
		final JLabel lblDate = new JLabel("Date of assesment:");
		final JTextArea txtDate = new JTextArea();
		final JButton btnDate = new JButton("change");
		final JLabel lblWeight = new JLabel("Weight");
		JButton btnUpdate = new JButton("Update");
		JButton btnClose = new JButton("Close");

		SpinnerNumberModel snmWeight = new SpinnerNumberModel(new Double(1.00), // value
				new Double(0.00), // min
				new Double(100.00), // max
				new Double(0.01)); // step
		final JSpinner txtWeight = new JSpinner(snmWeight);
		txtWeight.setValue(activeentity.getWeight());
		txtName.setSize(120, 30);
		
		if(activeentity.getIsTextField())
		{
			rblYes.setSelected(true);			
		}
		else
		{
			rblNo.setSelected(true);
		}
		group.add(rblYes);
		group.add(rblNo);


		btnDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					txtDate.setText(new DatePicker(frame).setPickedDate());
			}
		});

		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean b = true;
				boolean isText = false;
				String assDate = "";
				double weight = 1.0;


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
					
					//REQUIRED NEEDS FIXING//					
		//			activeProject.getTreeLinkedList().add(newE);
					//newE.populateTreeWithEntities();
					activeentity.updateEntity(txtName.getText(), isText, d, (Double) txtWeight.getValue());
					activeItem.setString("name",txtName.getText());

					

					// reset all values
					rblNo.setSelected(true);
					txtWeight.setValue(new Double(1.0));
					txtName.setText(null);
					txtDate.setText(dateFormat.format(new Date()));
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
		frame.add(btnUpdate);
		frame.add(btnClose);
		frame.pack();
	}
	
	public void showFrmUpdateNode(int p){
		frame.setVisible(true);
	}

	
	
}
