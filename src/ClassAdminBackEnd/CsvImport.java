/* this is the csvImport Class */

package ClassAdminBackEnd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


import com.csvreader.CsvReader;

public class CsvImport 
{

	CsvReader reader;
	
	public CsvImport() 
	{
	}


	public boolean fileExists(String in)
	{
		try 
		{
			reader = new CsvReader(in);
			return true;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	public ArrayList recordData()
	{
		ArrayList data = new ArrayList();
		ArrayList headers = null;
		ArrayList records = null;
		try
		{
			
			if(reader.readHeaders())
			{
				int headerCount = reader.getHeaderCount();
				
				headers = new ArrayList(headerCount);
				//System.out.println(headerCount);
	
				for(int i =0; i <headerCount; i++)
				{
					headers.add(reader.getHeader(i));
					//System.out.print(reader.getHeader(i) + "\t");
				}
				data.add(headers);
				//System.out.println();
				records = new ArrayList();
	
				try
				{
					while (reader.readRecord())
					{
						ArrayList record = new ArrayList(headerCount);
						//String ss = "";
						for(int i = 0; i < headerCount; i++)
						{
							try
							{
								String s = reader.get(i);
								record.add(s);
								//ss+=s+"\t";
							
							}
							catch(IOException e)
							{
								e.printStackTrace();
							}
						}
						//System.out.println(ss);
						records.add(record);
						//System.out.println("record added");
						record = null;
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				data.add(records);
				//System.out.println("adds records");
				
				headers = null;
				records = null;
				//System.out.println("SUCCESS");
				
				return data;
			}
			else
			{
			//	System.out.println("ELSE");
			}
		}
		catch (IOException e)
		{
//			System.out.println("FAIL");
			e.printStackTrace();
		}
		//System.out.println("FAIL2");
		return data;

	}
	public void print(ArrayList in)
	{

		ArrayList headers = (ArrayList)in.get(0);
		ArrayList records = (ArrayList)in.get(1);

		for (int j = 0; j < headers.size(); j++)
		{
			System.out.print(headers.get(j).toString() + "\t");
		}
		System.out.println();

		for(int i = 0; i < records.size(); i++)
		{
			ArrayList record = (ArrayList)records.get(i);

			for (int j = 0; j < record.size(); j++)
			{
				System.out.print(record.get(j).toString() + "\t");
			}
			System.out.println();
			
		}
	}
}