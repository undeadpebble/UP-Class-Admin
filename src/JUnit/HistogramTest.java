package JUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.UnsupportedFileTypeException;
import ClassAdminFrontEnd.Histogram;
import ClassAdminFrontEnd.HistogramFrame;

public class HistogramTest {

	@Test
	public void test() throws UnsupportedFileTypeException{
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		File file = null;
		Project p = new Project();
		Global.getGlobal().addProject(p);
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());
		p.setFileName("test.csv");
		
		Histogram  x = new Histogram(Global.getGlobal().getActiveProject());
	
		x.setWidthBar(20);
		assertEquals(20,x.getWidthBar(),0);
		
	}

}
