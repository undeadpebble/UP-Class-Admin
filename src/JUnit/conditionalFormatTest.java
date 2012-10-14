package JUnit;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.LinkedList;

import org.junit.Test;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Format;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.GreaterThanFormat;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.UnsupportedFileTypeException;

public class conditionalFormatTest {

	@Test
	public void test() throws UnsupportedFileTypeException{
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		Project p = new Project();
		Global.getGlobal().addProject(p);
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());
		LinkedList<Format> temp = new LinkedList<Format>();
		temp.add(new GreaterThanFormat(1.0, Color.green, null, "asdf"));
		
		Global.getGlobal().getActiveProject().getHead().getDataLinkedList().get(0).get(2).getType().setFormatting(temp);
		
		assertEquals(1.0,Global.getGlobal().getActiveProject().getHead().getDataLinkedList().get(0).get(2).getType().getFormatting().get(0).getValue1(),0);
		assertEquals(Color.green,Global.getGlobal().getActiveProject().getHead().getDataLinkedList().get(0).get(2).getType().getFormatting().get(0).getTextColor());
	}

}
