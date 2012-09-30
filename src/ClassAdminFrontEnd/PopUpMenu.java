package ClassAdminFrontEnd;

import javax.swing.*;

import org.tmatesoft.sqljet.core.SqlJetException;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import Frames.Frame;
import Frames.FrmNewNode;

import prefuse.Display;
import prefuse.Visualization;
import prefuse.controls.ControlAdapter;
import prefuse.data.Edge;
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
	JDialog frame = null;
	LinkedList<EntityType> activeTreeLinkedList = null;
	FrmNewNode newNode;

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
			}
		});

		miRemoveWC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisualItem item = activeItem;
				int i = item.getRow();
				activeTree.removeNode(i);
				activeTreeLinkedList.get(i).removeDeletingChildren();
				item.getVisualization().repaint();
				activeProject.updateTables();
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
			}
		});

	}

	public void setTreeView(TreeView treeView, Tree tree, Project project) {
		activeProject = project;
		activeTreeLinkedList = activeProject.getTreeLinkedList();
		activeTree = tree;
		tview = treeView;
		newNode = new FrmNewNode(activeTree, activeProject, new JFrame(),tview);
		tview.addControlListener(new ControlAdapter() {
			public void itemReleased(VisualItem item, MouseEvent e) {
				activeItem = null;
				if (e.isPopupTrigger()) {
					if (item.canGetString("name")) {
						pMenu.show(e.getComponent(), e.getX(), e.getY());
						activeItem = item;
					}
				}
			}
		});
	}

}