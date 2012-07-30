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

public class XlsImport extends FileImport{

	File reader;
	Workbook w;
	Sheet sheet;
	int headerLine = 0;

	public XlsImport() {

	}

	public Boolean fileExists(String in) {
		try {
			reader = new File(in);
			createWorkbook(reader);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void createWorkbook(File f) throws IOException {
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

	public void setHeaderLine(int s) {
		headerLine = s;
	}

	public int getLines() {
		return sheet.getRows();
	}

	public ArrayList recordData() {
		ArrayList data = new ArrayList();
		ArrayList headers = null;
		ArrayList records = null;

		headers = new ArrayList();// get headers
		for (int i = 0; i < sheet.getColumns(); i++) {
			Cell cell = sheet.getCell(i, headerLine);
			headers.add(cell.getContents());
			// System.out.println(cell.getContents());
			cell = null;
		}
		data.add(headers);

		records = new ArrayList(); // get records
		for (int j = headerLine + 1; j < sheet.getRows(); j++) {
			ArrayList record = new ArrayList();
			for (int i = 0; i < sheet.getColumns(); i++) {
				Cell cell = sheet.getCell(i, j);
				String pp = cell.getContents();
				record.add(pp);
				//System.out.print(pp + '\t');
			}
			records.add(record);
			record = null;
			 //System.out.println();
		}
		data.add(records);
		records = null;

		return data;
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

	public ArrayList getHeaders(ArrayList arr) // return header arraylist from
	// arraylist
	{
		ArrayList headers = (ArrayList) arr.get(0);
		return headers;
	}

	public ArrayList getRecords(ArrayList arr) // return record arraylist from
	// arraylist
	{
		ArrayList records = (ArrayList) arr.get(1);
		return records;
	}

	public ArrayList getRecord(ArrayList arr, int index) // return specified
	// record from
	// arraylist
	{
		ArrayList records = (ArrayList) arr.get(1); // get records arraylist
		ArrayList record = (ArrayList) records.get(index); // get specified
		// record in record
		// arraylist
		return record;
	}

	public String getRecordFieldValue(ArrayList arr, int recordIndex,
			int fieldIndex) // return specified field from record in file
	{
		ArrayList records = (ArrayList) arr.get(1); // get records arraylist
		ArrayList record = (ArrayList) records.get(recordIndex); // get
		// specified
		// record in
		// records
		// arraylist
		String field = (String) record.get(fieldIndex); // get specified field
		// in record
		return field;
	}

	public void printHeaders(ArrayList headers) // print all headers from
	// headers arraylist
	{
		for (int j = 0; j < headers.size(); j++) {
			System.out.print(headers.get(j).toString() + "\t");
		}
		System.out.println();
	}

	public void printRecords(ArrayList records) // print all records from
	// records arraylist
	{
		for (int i = 0; i < records.size(); i++) {
			ArrayList record = (ArrayList) records.get(i); // get record
			// arraylist from
			// records

			for (int j = 0; j < record.size(); j++) // get field from record
			// arraylist
			{
				System.out.print(record.get(j).toString() + "\t"); // print
				// field
				// data
			}
			System.out.println(); // new line for next record

		}
	}

	public void printRecord(ArrayList record) // print all information from
	// specified record
	{
		for (int j = 0; j < record.size(); j++) {
			System.out.print(record.get(j).toString() + "\t"); // print field
			// data
		}
		System.out.println(); // new line XD
	}

	public void print(ArrayList in) // print entire structure
	{

		ArrayList headers = (ArrayList) in.get(0); // get header arraylist
		ArrayList records = (ArrayList) in.get(1); // get records arraylist

		for (int j = 0; j < headers.size(); j++) // print all headers on header
		// arraylist
		{
			System.out.print(headers.get(j).toString() + "\t");
		}
		System.out.println();

		for (int i = 0; i < records.size(); i++) // get each record arraylist in
		// records arraylist
		{
			ArrayList record = (ArrayList) records.get(i); // specify record

			for (int j = 0; j < record.size(); j++) // print all field data from
			// record arraylist
			{
				System.out.print(record.get(j).toString() + "\t");
			}
			System.out.println(); // next record to follow

		}// get records
	}
}