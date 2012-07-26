package ClassAdminBackEnd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import jxl.*;
public class testMain
{
	
	public static void main(String[] args) 
	{
		FileHandler fh = new FileHandler();
		
		try {
			fh.openFile("test.csv");
		} catch (UnsupportedFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Project p = Global.getGlobal().getActiveProject();
		p.getEntityTypes().get(0).getBorderCasing().add(new BorderCase(5.0,10.0));
		p.getEntityTypes().get(0).getFormatting().add(new Format(0,0,1.0,2.0,new Color(1.0f,0.2f,0.3f),new Color(1.0f,0.2f,0.3f),"test conditional formatting"));
		p.getEntityTypes().get(0).getFields().add("test");
		p.getEntityTypes().get(0).getFields().add("test1");
		p.getEntityTypes().get(0).getFields().add("test2");
		p.getEntityTypes().get(0).getFieldDefaults().add("test");
		p.getEntityTypes().get(0).getFieldDefaults().add("test2");
		p.getEntityTypes().get(0).getFieldDefaults().add("test3");
		p.getEntityTypes().get(0).setDate(new Date());
		Boolean[] b = {true,true,false};
		p.getEntityTypes().get(0).setVisibleFields(b);
		p.getEntityTypes().get(0).getEntityList().get(0).getDetails().getFields().add("test5");
		p.getEntityTypes().get(0).getEntityList().get(0).getDetails().getFields().add("test6");
		p.getEntityTypes().get(0).getEntityList().get(0).getDetails().getFields().add("test7");
		pdatImport PI = new pdatImport();
		String str = Global.getGlobal().getActiveProject().toString();
		PI.write(str.getBytes(), "test.pdat");
		System.out.println(Global.getGlobal().getActiveProject());
		PI.read("test.pdat");
		System.out.println(Global.getGlobal().getActiveProject());
		
		
	}
}
