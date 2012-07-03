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
		ArrayList data = new ArrayList(2);
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
				//no headers in file;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return data;

	}
	public void print(ArrayList in)
	{
		ArrayList headers = new ArrayList(in.indexOf(0));
		ArrayList recors = new ArrayList(in.indexOf(1));
		for(int i = 0; i < headers.size(); i++)
		{
			System.out.print(headers.indexOf(i) + "\t");
		}
	}
}