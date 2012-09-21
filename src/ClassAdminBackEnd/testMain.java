package ClassAdminBackEnd;

import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.FileHandler;


import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.tmatesoft.sqljet.core.SqlJetException;

import ClassAdminFrontEnd.Frame;

import ClassAdminFrontEnd.EntityTypePanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.RapidAssessmentCanvas;
import ClassAdminFrontEnd.TreeView;

import Rule.frmRule;

import ClassAdminFrontEnd.RapidAssessmentCanvas.MyMarkPoint;


import jxl.*;
public class testMain
{
	
	public static void main(String[] args) {
		ClassAdminBackEnd.FileHandler fh = ClassAdminBackEnd.FileHandler.get();
		
		try {
			fh.openFile("test.csv", Global.getGlobal().getActiveProject());
			
			JFrame temp = new JFrame();
			temp.setVisible(true);
			temp.setSize(1300, 600);
			
			final FrmTable test = new FrmTable(Global.getGlobal().getActiveProject().getHead().getHeaders(), Global.getGlobal().getActiveProject().getHead().getDataLinkedList(), Global.getGlobal().getActiveProject());
			
			

			temp.setLayout(new FlowLayout());
			JButton adder = new JButton("Add");
			temp.add(test);
			temp.add(adder);
			
			adder.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub\
					test.redraw();
					frmRule rule = new frmRule(Global.getGlobal().getActiveProject());
					rule.setVisible(true);
					
				}
			});

		} catch (UnsupportedFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

