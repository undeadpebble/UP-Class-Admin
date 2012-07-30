package ClassAdminBackEnd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import jxl.*;
public class testMain
{
	
	public static void main(String[] args) 
	{
		
		FileHandler fh = FileHandler.get();
		
		try {
			fh.openFile("book1.xls");
		} catch (UnsupportedFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Global.getGlobal().getActiveProject().toString());
		
	}
}
