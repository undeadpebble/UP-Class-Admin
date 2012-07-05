package ClassAdminBackEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import jxl.*;
import jxl.read.biff.BiffException;

public class XslImport {

	File reader;
	Workbook w;
	Sheet sheet;

	public XslImport() {

	}

	public boolean fileExists(String in) {
		try 
		{
			reader = new File(in);
			System.out.print("File found");
			createWorkbook(reader);
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	private void createWorkbook(File f)
	{
		try {
			w = Workbook.getWorkbook(f);
		} catch (BiffException e) {
			System.out.println("1");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("2");
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