/*package ClassAdminBackEnd;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.FileHandler;

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
import ClassAdminFrontEnd.TreeView;
import Rule.frmRule;

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
			
			FrmTable test = new FrmTable(Global.getGlobal().getActiveProject().getHead().getHeaders(), Global.getGlobal().getActiveProject().getHead().getDataLinkedList(), Global.getGlobal().getActiveProject());
			
			

			//temp.add(test);
			
			JButton adder = new JButton("Add");
			
			temp.add(adder);
			
			adder.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
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
*/