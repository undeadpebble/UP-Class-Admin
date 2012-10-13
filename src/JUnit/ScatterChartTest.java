package JUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.UnsupportedFileTypeException;
import ClassAdminFrontEnd.Histogram;
import ClassAdminFrontEnd.ScatterPlotFrame;

public class ScatterChartTest  {

	@Test
	public void test() throws UnsupportedFileTypeException {
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		File file = null;
		Project p = new Project();
		Global.getGlobal().addProject(p);
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());
		p.setFileName("test.csv");
		
	ScatterPlotFrame x = new ScatterPlotFrame(Global.getGlobal().getActiveProject());
	
		assertEquals(5,x.doensorteer(2, 4)[5],0);
		assertEquals(2,x.doensorteer(2, 4)[2],0);
	}

}
