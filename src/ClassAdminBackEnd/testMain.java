package ClassAdminBackEnd;

import java.util.ArrayList;

public class testMain
{
	
	public static void main(String[] args) 
	{
<<<<<<< HEAD
		XslImport c = new XslImport();
		if(c.fileExists("C:/Users/Marco/Downloads/test.xsl"))
=======
		FileHandler fHandler = new FileHandler();
		try{
		fHandler.openFile("test.csv");
		}
		catch(UnsupportedFileTypeException e){
			System.out.println("Unsupported filetype");
		}
		/*CsvImport c = new CsvImport();
		FileHandler fHandler = new FileHandler();
		try{
		fHandler.openFile("test.csv");
		}
		catch(UnsupportedFileTypeException e){
			System.out.println("Unsupported filetype");
		}
		/*CsvImport c = new CsvImport();
		if(c.fileExists("test.csv"))
>>>>>>> refs/remotes/origin/master
		{
/*			ArrayList csv =  c.recordData();
			//c.print(csv);
			ArrayList h,rs,r;
			h = c.getHeaders(csv);
			//c.printHeaders(h);
			rs = c.getRecords(csv);
			//c.printRecords(rs);
			r = c.getRecord(csv, 4);
			//c.printRecord(r);
			System.out.println(c.getRecordFieldValue(csv,0 , 0));
<<<<<<< HEAD
*/

=======
			
		
		}*/
		
		Global g = Global.getGlobal();
		MarkEntity head = Global.getGlobal().getActiveProject().getHead();
		for (int i = 0;i<head.getSubEntity().size();++i){
			try{
			System.out.println(i+" "+head.getSubEntity().get(i).getDetails().getFields().get(0));
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(i+" "+head.getSubEntity().get(i).getMark());
			}
			for (int j = 0;j<head.getSubEntity().get(i).getSubEntity().size();++j){
				try {
					System.out.println(j+" "+head.getSubEntity().get(i).getSubEntity().get(j).getDetails().getFields().get(0));
				} catch (IndexOutOfBoundsException e) {
					System.out.println(j+" "+head.getSubEntity().get(i).getSubEntity().get(j).getMark());
				}
			}
>>>>>>> refs/remotes/origin/master

		}*/
		
		Global g = Global.getGlobal();
		MarkEntity head = Global.getGlobal().getActiveProject().getHead();
		for (int i = 0;i<head.getSubEntity().size();++i){
			try{
			System.out.println(i+" "+head.getSubEntity().get(i).getDetails().getFields().get(0));
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(i+" "+head.getSubEntity().get(i).getMark());
			}
			for (int j = 0;j<head.getSubEntity().get(i).getSubEntity().size();++j){
				try {
					System.out.println(j+" "+head.getSubEntity().get(i).getSubEntity().get(j).getDetails().getFields().get(0));
				} catch (IndexOutOfBoundsException e) {
					System.out.println(j+" "+head.getSubEntity().get(i).getSubEntity().get(j).getMark());
				}
			}
		}
<<<<<<< HEAD

=======
		System.out.println();
		System.out.println();
		
>>>>>>> refs/remotes/origin/master
	}
}
