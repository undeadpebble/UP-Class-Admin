package ClassAdminBackEnd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import jxl.*;
public class testMain
{
	
	public static void main(String[] args) 
	{
		XlsImport importer = new XlsImport();
		
		importer.fileExists("C:/Users/Marco/Workspace/UP-Class-Admin/book1.xls");
		importer.setSheet(0);
		importer.setHeaderLine(0);
		ArrayList arr = importer.recordData();
		importer.print(arr);
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		CsvImport csv = new CsvImport();
		
		csv.fileExists("C:/Users/Marco/Workspace/UP-Class-Admin/test.csv");
		ArrayList arr2 = csv.recordData();
		csv.print(arr2);
		
		/*FileHandler fh = new FileHandler();
		
		try {
			fh.openFile("test.csv");
		} catch (UnsupportedFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
}
