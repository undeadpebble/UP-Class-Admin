package ClassAdminBackEnd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import ClassAdminFrontEnd.FrmTable;

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
		


	}
}
