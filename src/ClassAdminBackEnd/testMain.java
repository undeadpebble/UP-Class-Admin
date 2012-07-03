package ClassAdminBackEnd;

import java.util.ArrayList;

public class testMain
{
	public static void main(String[] args) 
	{
		CsvImport c = new CsvImport();
		c.fileExists("test.csv");
		ArrayList CSV =  c.recordData();
		c.print(CSV);
		
	}
}