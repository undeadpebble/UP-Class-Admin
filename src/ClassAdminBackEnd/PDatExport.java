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
	final String BETWEEN_FORMAT_TABLE = "BetweenFormat";
	final String LESS_THAN_FORMAT_TABLE = "LessThanFormat";
	final String GREATER_THAN_FORMAT_TABLE = "GreaterThanFormat";
	final String BORDERCASE_TABLE = "BorderCase";
	
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
        	String createTableQuery = "CREATE TABLE " + ENTITY_TYPE_TABLE +
        			"(typeID int NOT NULL PRIMARY KEY," +
        			"name varchar(255) ," +
        			"isText Boolean," +
        			"date Date," +
        			"defaultWeight float," +
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + ENTITY_TABLE + "(" +
        			"entityID int NOT NULL PRIMARY KEY," +
        			"parentID int ," +
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + BEST_N_ENTITY_TABLE + "(" +
        			"N int,"+
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + STRING_ENTITY_TABLE + "(" +
        			"field varchar(1000),"+
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + IMG_ENTITY_TABLE + "(" +
        			"address varchar(1000),"+
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + GREATER_THAN_FORMAT_TABLE + "(" +
        			"typeID int ," +
        			
        			")";
        	db.createTable(createTableQuery);
        	createTableQuery = "CREATE TABLE " + LESS_THAN_FORMAT_TABLE + "(" +
        			"typeID int ," +
        			
        			")";
        	db.createTable(createTableQuery);
        	createTableQuery = "CREATE TABLE " + BETWEEN_FORMAT_TABLE + "(" +
        			"typeID int ," +
        			
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + BORDERCASE_TABLE + "(" +
        			"typeID int ," +
        			")";
        	db.createTable(createTableQuery);
        	
        }catch (Exception e) {
				e.printStackTrace();
			}
        finally {
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
