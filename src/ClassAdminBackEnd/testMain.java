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
/*		XlsImport x = new XlsImport();
		if(x.fileExists("book1.xls"))
		{
			ArrayList arr = x.recordData();
			x.printHeaders(x.getHeaders(arr));
			x.printRecords(x.getRecords(arr));
			x.print(arr);
		}
		
		CsvImport c = new CsvImport();
		{
			if(c.fileExists("test.csv"))
			{
				ArrayList arr = c.recordData();
=======
>>>>>>> refs/remotes/origin/louis

<<<<<<< HEAD
				c.printHeaders(c.getHeaders(arr));
				c.printRecords(c.getRecords(arr));
				c.print(arr);
			}
			
		}*/

		
		FileHandler fh = FileHandler.get();
		
		try {

			fh.openFile("test.csv");

		} catch (UnsupportedFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(Global.getGlobal().getActiveProject().toString());

		PDatExport test = new PDatExport();
		try {
			test.exportFile(Global.getGlobal().getActiveProject(), "test.pdat");
		} catch (SqlJetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
		
		JFrame fr = new JFrame();
		
		fr.setVisible(true);
		fr.setSize(600, 600);
		
		/*FrmTable tble = new FrmTable(Global.getGlobal().getActiveProject().getHead().getHeaders(), Global.getGlobal().getActiveProject().getHead().getDataLinkedList(), Global.getGlobal().getActiveProject());
		
		fr.add(tble);
		fr.repaint();*/
		EntityTypePanel temp = new EntityTypePanel(Global.getGlobal().getActiveProject());
		fr.add(temp);
		


		

		
		

	}
}
