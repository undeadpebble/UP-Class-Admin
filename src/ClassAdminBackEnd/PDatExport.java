package ClassAdminBackEnd;

import java.io.File;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class PDatExport {
	final String ENTITY_TYPE_TABLE = "EntityType";
	final String ENTITY_TABLE = "Entity";
	final String BEST_N_ENTITY_TABLE = "BestNEntity";
	final String STRING_ENTITY_TABLE = "StringEntity";
	final String IMG_ENTITY_TABLE = "ImageEntity";
	final String FORMAT_TABLE = "ImageEntity";
	final String BORDERCASE_TABLE = "ImageEntity";
	
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
        
        db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
        	//create tables
        	String createTableQuery = "CREATE TABLE " + ENTITY_TYPE_TABLE
        			+"()";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + ENTITY_TABLE
        			+"()";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + BEST_N_ENTITY_TABLE
        			+"()";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + STRING_ENTITY_TABLE
        			+"()";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + IMG_ENTITY_TABLE
        			+"()";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + FORMAT_TABLE
        			+"()";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + BORDERCASE_TABLE
        			+"()";
        	db.createTable(createTableQuery);
        	
        } finally {
            db.commit();
        }
        
        db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
        	//TODO
        	ISqlJetTable table = db.getTable("table name");
        	//insert statements
        	 table.insert("");
        } finally {
            db.commit();
        }
        
	}
	 private static void printRecords(ISqlJetCursor cursor) throws SqlJetException {
	        try {
	            if (!cursor.eof()) {
	                do {
	                    System.out.println(cursor.getRowId() + " : " + 
	                            cursor.getString("") + " " + 
	                            cursor.getString("") + " was born on " );
	                            //formatDate(cursor.getInteger("")));
	                } while(cursor.next());
	            }
	        } finally {
	            cursor.close();
	        }
	    }
}
