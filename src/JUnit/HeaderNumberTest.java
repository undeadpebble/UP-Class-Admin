package JUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.UnsupportedFileTypeException;

public class HeaderNumberTest {

	@Test
	public void test() throws UnsupportedFileTypeException{
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());
		
		String[] headers = Global.getGlobal().getActiveProject().getHead().getNumberHeaders();
		
		assertEquals("JAARPUNT",headers[0]);
		assertEquals("EKSAMENSENTRUM",headers[1]);
		
	}

}
