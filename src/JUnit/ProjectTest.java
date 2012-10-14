package JUnit;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import ClassAdminBackEnd.AbsentException;
import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminBackEnd.UnsupportedFileTypeException;
import ClassAdminFrontEnd.HistogramFrame;

public class ProjectTest {

	@Test
	public void test() throws UnsupportedFileTypeException, AbsentException {
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		Project p = new Project();
		Global.getGlobal().addProject(p);
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());

		Global.getGlobal().getActiveProject().addhistogramcharts(new HistogramFrame(Global.getGlobal().getActiveProject()));
		Global.getGlobal().getActiveProject().incHistogramcount();
		
		assertEquals(1, Global.getGlobal().getActiveProject().getHistogramcount());
		p.setFileName("test.csv");
		assertEquals("test.csv", Global.getGlobal().getActiveProject().getFileName());
		
		assertEquals(26.375, Global.getGlobal().getActiveProject().getHead().doMarkMath(),0);
		
	}

}
