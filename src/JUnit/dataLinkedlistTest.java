package JUnit;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;

import org.junit.Test;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminBackEnd.UnsupportedFileTypeException;

public class dataLinkedlistTest {

	@Test
	public void test() throws UnsupportedFileTypeException {
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());

		LinkedList<LinkedList<SuperEntity>> data = Global.getGlobal().getActiveProject().getHead().getDataLinkedList();
		
		assertEquals("2927733", data.get(0).get(0).getValue());
		assertEquals("BALENI PJ MS", data.get(1).get(1).getValue());
		assertEquals("3.0", data.get(2).get(2).getValue());
		assertEquals("E", data.get(3).get(3).getValue());
		assertEquals("100.0", data.get(4).get(4).getValue());
		assertEquals("V", data.get(5).get(5).getValue());
		assertEquals("S01", data.get(6).get(6).getValue());
		assertEquals("A", data.get(7).get(7).getValue());
		assertEquals("BIng: (Chemiese Ingenieurswese)", data.get(8).get(8).getValue());
		assertEquals("s27000410@tuks.co.za", data.get(9).get(9).getValue());
	}
}
