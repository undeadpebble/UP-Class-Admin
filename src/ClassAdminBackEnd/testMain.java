package ClassAdminBackEnd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;



import org.tmatesoft.sqljet.core.SqlJetException;



import ClassAdminFrontEnd.EntityTypePanel;

import ClassAdminFrontEnd.Frame;

import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.TreeView;

import jxl.*;
public class testMain
{
	
	public static void main(String[] args) 
	{
		
		JFrame fr = new JFrame();
		
		fr.setVisible(true);
		fr.setSize(600, 600);
		
		EntityTypePanel temp = new EntityTypePanel(Global.getGlobal().getActiveProject());
		fr.add(temp);
	}
}
