package ClassAdminBackEnd;

import java.util.ArrayList;

public class testMain
{
	public static void main(String[] args) 
	{
		CsvImport c = new CsvImport();
		if(c.fileExists("test.csv"))
		{
			ArrayList csv =  c.recordData();
			//c.print(csv);
			ArrayList h,rs,r;
			h = c.getHeaders(csv);
			//c.printHeaders(h);
			rs = c.getRecords(csv);
			//c.printRecords(rs);
			r = c.getRecord(csv, 4);
			//c.printRecord(r);
			System.out.println(c.getRecordFieldValue(csv, 3, 0));
			
		
		}
		
	}
}