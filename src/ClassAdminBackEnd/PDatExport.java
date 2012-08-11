package ClassAdminBackEnd;

import java.io.File;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class PDatExport {
	public void exportFile(Project project, String filename) throws SqlJetException{
		File dbFile = new File(filename);
        dbFile.delete();
        
        // create database, table and two indices:
        SqlJetDb db = SqlJetDb.open(dbFile, true);
        // set DB option that have to be set before running any transactions: 
        db.getOptions().setAutovacuum(true);
        // set DB option that have to be set in a transaction: 
        db.runTransaction(new ISqlJetTransaction() {
            public Object run(SqlJetDb db) throws SqlJetException {
                db.getOptions().setUserVersion(1);
                return true;
            }
        }, SqlJetTransactionMode.WRITE);
	}
}
