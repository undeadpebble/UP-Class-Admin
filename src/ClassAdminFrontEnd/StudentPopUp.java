
package ClassAdminFrontEnd;

import javax.swing.*;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminBackEnd.AbsentLeafMarkEntity;
import ClassAdminBackEnd.MarkEntity;
import Frames.FrmNewNode;
import Frames.FrmUpdateNode;

import prefuse.Visualization;
import prefuse.controls.ControlAdapter;
import prefuse.data.Table;
import prefuse.data.Tree;
import prefuse.visual.VisualItem;

import java.awt.event.*;
import java.util.LinkedList;

public class StudentPopUp {

	JPopupMenu pMenu;
	TreeView tview;
	VisualItem activeItem = null;
	Tree activeTree = null;
	Project activeProject;
	SuperEntity activeEntity;
	JDialog frame = null;
	LinkedList<SuperEntity> activeStudentTreeLinkedList = null;
	JFrame parentFrame;
	JTextArea txtChange;
	JButton btnChange;

	public StudentPopUp() {
		pMenu = new JPopupMenu();
		JMenuItem miEdit = new JMenuItem("Edit");
		pMenu.add(miEdit);
		JMenuItem miAbsent = new JMenuItem("Mark Absent");
		pMenu.add(miAbsent);
		JMenuItem miNotAbsent = new JMenuItem("Mark not Absent");
		pMenu.add(miNotAbsent);
		JMenuItem miRemove = new JMenuItem("Remove Student");
		pMenu.add(miRemove);

		miEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualItem item = activeItem;
				if (item.canGetString("name"))
				{
					String id = item.getString("name");
						tview.setSelectedEntity(item.getRow());
						txtChange.setVisible(true);
						btnChange.setVisible(true);
						txtChange.setText(id.substring(id.indexOf(":") + 2));
						txtChange.requestFocus(true);
						txtChange.selectAll();
				}
			}
		});

		miAbsent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualItem item = activeItem;
				if (item.canGetString("name")) {
					AbsentLeafMarkEntity newM = new AbsentLeafMarkEntity(tview.getSuperEntity(item.getRow()));
					tview.setSuperEntity(item.getRow(),newM);
					activeProject.getSelected().add(newM);
					activeProject.updateTables();
					newM.getParentEntity().updateMark();
					tview.updateNode(item.getRow());
					String nodeName = item.getString("name");
					nodeName = nodeName.substring(0, nodeName.indexOf(":") + 2) + "*ABSENT";
					item.set("name", nodeName);
					item.getVisualization().run("filter");
					activeProject.getAudit().updateStudent(activeProject.getStudentLinkedList().get(0).getValue(), activeProject.getStudentLinkedList().get(item.getRow()).getValue(), "*ABSENT", true);
					
				}
			}
		});

		miNotAbsent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualItem item = activeItem;
				if (item.canGetString("name")) {
					System.out.println("**********************" + item.getRow());
					MarkEntity newM = new MarkEntity(tview.getSuperEntity(item.getRow()));
					tview.setSuperEntity(item.getRow(),newM);
					activeProject.getSelected().add(newM);
					newM.setMark(0.0);
					activeProject.updateTables();
					String nodeName = item.getString("name");
					nodeName = nodeName.substring(0, nodeName.indexOf(":") + 2) + "N/A";
					item.set("name", nodeName);
					item.getVisualization().run("filter");
					activeProject.getAudit().updateStudent(activeProject.getStudentLinkedList().get(0).getValue(), activeProject.getStudentLinkedList().get(item.getRow()).getValue(), "NOT ABSENT", true);
					tview.updateNode(item.getRow());
				}
			}
		});
		miRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualItem item = activeItem;
				if (item.canGetString("name")) {
					if(item.getRow() == 0)
					{
						int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete " + item.getString("name") + "?", "Remove node", JOptionPane.OK_CANCEL_OPTION);
						if (confirm == 0) {
							activeEntity.deleteStudent();
							activeProject.updatecharts();
							activeProject.updateTables();
							parentFrame.dispose();
						}						

					}
				}
			}
		});

	}

	public void setTreeView(TreeView treeView, Tree tree, Project project, JFrame pFrame,JTextArea tChange, JButton bChange) {
		activeProject = project;
		activeStudentTreeLinkedList = activeProject.getStudentLinkedList();
		activeTree = tree;
		tview = treeView;
		parentFrame = pFrame;
		txtChange = tChange;
		btnChange = bChange;
		// add control listener for nodes on front end treeview
		tview.addControlListener(new ControlAdapter() {
			public void itemReleased(VisualItem item, MouseEvent e) {
				activeItem = null;
				if (e.isPopupTrigger()) {
					if (item.canGetString("name")) {
						pMenu.show(e.getComponent(), e.getX(), e.getY());
						activeItem = item;
						activeItem.getVisualization().run("filter");
						activeEntity = activeStudentTreeLinkedList.get(activeItem.getRow());
					}
				}
			}
		});
	}
}

