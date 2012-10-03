package ClassAdminFrontEnd;

import javax.swing.*;

import org.tmatesoft.sqljet.core.SqlJetException;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import Frames.Frame;
import Frames.FrmNewNode;
import Frames.FrmUpdateNode;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.controls.ControlAdapter;
import prefuse.data.Edge;
import prefuse.data.Graph;
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
import java.util.LinkedList;

public class PopUpMenu {

	JPopupMenu pMenu;
	TreeView tview;
	VisualItem activeItem = null;
	Tree activeTree = null;
	Project activeProject;
	EntityType activeEntity;
	JDialog frame = null;
	LinkedList<EntityType> activeTreeLinkedList = null;
	FrmNewNode newNode;
	FrmUpdateNode updateNode;
	JFrame parentFrame;

	public PopUpMenu() {
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
				VisualItem item = activeItem;
				if (item.toString().contains("ode")) {
					int p = item.getRow();
					newNode.showFrmNewNode(p);
				}
			}
		});

		miEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(activeItem.canSetString("name"))
				{
					updateNode.showFrmUpdateNode(0);
					activeItem.getVisualization().run("filter");
				}
			}
		});

		miRemoveWC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualItem item = activeItem;

				int i = item.getRow();
				activeTree.removeNode(i);

				activeTreeLinkedList.get(i).removeDeletingChildren();
				activeProject.updateTables();
				
				activeProject.getAudit().closedProject();
				//Project();
				//(item.getString("name"),true);
				//toString());
				//.RemoveNode(item.getString("name"), true);
				
				parentFrame.dispose();
				TreeView.createEntityTypeFrm("name",activeProject);
			}
		});

		miRemoveWOC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualItem item = activeItem;
				int i = item.getRow();
				int source = -1, target = -1;
				Table edgeTable = activeTree.getEdgeTable();
				for (int r = 0; r < edgeTable.getRowCount(); r++) {
					if (edgeTable.getInt(r, 1) == i) {
						source = edgeTable.getInt(r, 0);
					}
				}
				for (int r = 0; r < edgeTable.getRowCount(); r++) {
					if (edgeTable.getInt(r, 0) == i) {
						target = edgeTable.getInt(r, 1);
						activeTree.removeEdge(activeTree.getEdge(edgeTable.getInt(r, 0), edgeTable.getInt(r, 1)));
						activeTree.addEdge(source, target);
					}
				}
				activeTree.removeNode(i);
				
				activeTreeLinkedList.get(i).removeSavingChildren();
				item.getVisualization().repaint();
				activeProject.updateTables();
	//			activeProject.getAudit().RemoveNode(item.getString("name"), false);
				parentFrame.dispose();
				TreeView.createEntityTypeFrm("name",activeProject);
				
			}
		});

	}

	public void setTreeView(TreeView treeView, Tree tree, Project project, JFrame pFrame) {
		activeProject = project;
		activeTreeLinkedList = activeProject.getTreeLinkedList();
		activeTree = tree;
		tview = treeView;
		parentFrame = pFrame;
		tview.addControlListener(new ControlAdapter() {
			public void itemReleased(VisualItem item, MouseEvent e) {
				activeItem = null;
				if (e.isPopupTrigger()) {
					if (item.canGetString("name")) {
						pMenu.show(e.getComponent(), e.getX(), e.getY());
						activeItem = item;
						activeEntity = activeTreeLinkedList.get(activeItem.getRow());
						newNode = new FrmNewNode(activeTree, activeProject, new JFrame(),tview);
						updateNode = new FrmUpdateNode(activeProject, new JFrame(), activeEntity,activeItem);
					}
				}
			}
		});
	}

}