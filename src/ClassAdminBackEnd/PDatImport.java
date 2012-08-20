package ClassAdminBackEnd;

import java.io.File;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class PDatImport {
	public void importFile(String filename) throws SqlJetException{
		File dbFile = new File(filename);
		SqlJetDb db = SqlJetDb.open(dbFile, true);
	}
}
