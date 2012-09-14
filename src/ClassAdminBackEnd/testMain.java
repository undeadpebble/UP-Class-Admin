package ClassAdminBackEnd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.FileHandler;

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

import jxl.*;
public class testMain
{
	
	public static void main(String[] args) {
		ClassAdminBackEnd.FileHandler fh = ClassAdminBackEnd.FileHandler.get();
		
		try {
			fh.openFile("test.csv", Global.getGlobal().getActiveProject());
			
			/*Global.getGlobal().getActiveProject().getHeadEntityType().getSubEntityType().get(0).getSubEntityType().get(2).changeParent(Global.getGlobal().getActiveProject().getHeadEntityType().getSubEntityType().get(0).getSubEntityType().get(1));
		
			fh.saveFile("test.pdat",Global.getGlobal().getActiveProject());*/
			
			JFrame temp = new JFrame();
			temp.setVisible(true);
			temp.setSize(1300, 600);
			
			FrmTable test = new FrmTable(Global.getGlobal().getActiveProject().getHead().getHeaders(), Global.getGlobal().getActiveProject().getHead().getDataLinkedList(), Global.getGlobal().getActiveProject());
			
			temp.add(test);

			
		} catch (UnsupportedFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
