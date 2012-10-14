package JUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.UnsupportedFileTypeException;

public class MaxValTest {

	@Test
	public void test() throws UnsupportedFileTypeException{
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		Project p = new Project();
		Global.getGlobal().addProject(p);
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());
		
		Global.getGlobal().getActiveProject().getHead().getDataLinkedList().get(0).get(2).getType().setMaxValue(1.0);
		
		assertEquals(1.0,Global.getGlobal().getActiveProject().getHead().getDataLinkedList().get(0).get(2).getType().getMaxValue(),0);
	}

}
