package JUnit;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.Stats;
import ClassAdminBackEnd.UnsupportedFileTypeException;

public class StatsTest {

	@SuppressWarnings("null")
	@Test
	public void test() throws UnsupportedFileTypeException {
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		File file = null;
		Project p = new Project();
		Global.getGlobal().addProject(p);
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());
		p.setFileName("test.csv");
		Stats testdummy = new Stats(Global.getGlobal().getActiveProject());
		assertEquals(10.0, testdummy.hoogstepunt(2), 0);
	
		assertEquals(0.0,testdummy.laagstepunt(2),0);
		assertEquals(5.5, testdummy.gemidpunt(2),0);
		assertEquals(10, testdummy.fails(2),0);
		assertEquals( 0, testdummy.her(2),0);
		assertEquals( 0, testdummy.slaag(2),0);
		assertEquals( 0, testdummy.distinction(2),0);
		assertEquals(10.0, testdummy.totalnrstd(),0);
		assertEquals( 6.0, testdummy.median(2),0);
		
	}

}
