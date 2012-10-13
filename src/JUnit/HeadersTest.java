package JUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.UnsupportedFileTypeException;

public class HeadersTest {

	@Test
	public void test() throws UnsupportedFileTypeException{
		FileHandler fileHandler;
		fileHandler = FileHandler.get();
		fileHandler.openFile("test.csv", Global.getGlobal().getActiveProject());
		
		String[] headers = Global.getGlobal().getActiveProject().getHead().getHeaders();
		
		assertEquals("STUDENTE NR",headers[0]);
		assertEquals("NAAM",headers[1]);
		assertEquals("JAARPUNT",headers[2]);
		assertEquals("TAAL",headers[3]);
		assertEquals("EKSAMENSENTRUM",headers[4]);
		assertEquals("AANBIEDMODUS",headers[5]);
		assertEquals("REGISTRASIE-PERIODE",headers[6]);
		assertEquals("TAALVOORKEUR",headers[7]);
		assertEquals("STUDIERIGTING",headers[8]);
		assertEquals("EMAIL",headers[9]);
		assertEquals("SELFOON",headers[10]);
	}

}
