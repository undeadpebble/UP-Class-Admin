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
	
				for(int i =0; i <headerCount; i++)
				{
					headers.add(reader.getHeader(i));
				}
				data.add(headers);
				records = new ArrayList();
	
				try
				{
					while (reader.readRecord())
					{
						ArrayList record = new ArrayList(headerCount);
						for(int i = 0; i < headerCount; i++)
						{
							try
							{
								String s = reader.get(i);
								record.add(s);
							}
							catch(IOException e)
							{
								e.printStackTrace();
							}
						}
						records.add(record);
						record = null;
					}
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				data.add(records);
				
				headers = null;
				records = null;
				
				return data;
			}
			else
			{
				System.out.println("ELSE");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return data;

	}
	public ArrayList getHeaders(ArrayList csv)
	{
		ArrayList headers = (ArrayList)csv.get(0);
		return headers;
	}
	public ArrayList getRecords(ArrayList csv)
	{
		ArrayList records = (ArrayList)csv.get(1);
		return records;		
	}
	public ArrayList getRecord(ArrayList csv, int index)
	{
		ArrayList records = (ArrayList)csv.get(1);
		ArrayList record = (ArrayList)records.get(index);
		return record;
	}
	public String getRecordFieldValue(ArrayList csv, int recordIndex, int fieldIndex)
	{
		ArrayList records = (ArrayList)csv.get(1);
		ArrayList record = (ArrayList)records.get(recordIndex);
		String field = (String)record.get(fieldIndex);
		return field;
	}
	public void printHeaders(ArrayList headers)
	{
		for (int j = 0; j < headers.size(); j++)
		{
			System.out.print(headers.get(j).toString() + "\t");
		}
		System.out.println();
	}
	public void printRecords(ArrayList records)
	{
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
	public void printRecord(ArrayList record)
	{
		for (int j = 0; j < record.size(); j++)
		{
			System.out.print(record.get(j).toString() + "\t");
		}
		System.out.println();
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