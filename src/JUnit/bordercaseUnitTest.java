package JUnit;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import ClassAdminBackEnd.BorderCase;
import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.UnsupportedFileTypeException;

public class bordercaseUnitTest {

	@Test
	public void test()throws UnsupportedFileTypeException {
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		Project p = new Project();
		Global.getGlobal().addProject(p);
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());
		
		LinkedList<BorderCase> temp = new LinkedList<BorderCase>();
		temp.add(new BorderCase(0.0, 1.0));
		Global.getGlobal().getActiveProject().getHead().getDataLinkedList().get(0).get(2).getType().setBorderCasing(temp);
		
		assertEquals(0,Global.getGlobal().getActiveProject().getHead().getDataLinkedList().get(0).get(2).getType().getBorderCasing().get(0).getLowVal(),0);
		assertEquals(1,Global.getGlobal().getActiveProject().getHead().getDataLinkedList().get(0).get(2).getType().getBorderCasing().get(0).getHighVal(),0);
	}

}
