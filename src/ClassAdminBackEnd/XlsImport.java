package ClassAdminBackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.read.biff.WorkbookParser;

public class XlsImport {

	File reader;
	Workbook w;
	Sheet sheet;

	public XlsImport() {

	}

	public boolean fileExists(String in) {
		try 
		{
			reader = new File(in);
			System.out.println("File found");
			w = Workbook.getWorkbook(reader);
			System.out.println("FAIL");
			
			
			//createWorkbook(reader);
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	private void createWorkbook(File f) throws IOException
	{
		try {
			w = Workbook.getWorkbook(f);
		} catch (BiffException e) {
			System.out.println("1");
			e.printStackTrace();
		} 
	}
	public void setSheet(int s) {
		sheet = w.getSheet(s);
	}

	public int getLines() {
		return 0;
	}

	public void recordData() {
		ArrayList data = new ArrayList();
		ArrayList headers = null;
		ArrayList records = null;
		ArrayList record = null;
	}

	public void printSheet() {
		for (int j = 0; j < sheet.getColumns(); j++) {
			for (int i = 0; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(j, i);
				System.out.print(cell.getContents() + "\t");
			}
			System.out.println();
		}
	}
}