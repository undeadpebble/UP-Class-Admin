package ClassAdminBackEnd;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class PDatExport {
	public static final String ENTITY_TYPE_TABLE = "EntityType";
	public static final String ENTITY_TABLE = "Entity";
	public static final String BEST_N_ENTITY_TABLE = "BestNEntity";
	public static final String STRING_ENTITY_TABLE = "StringEntity";
	public static final String IMG_ENTITY_TABLE = "ImageEntity";
	public static final String MARK_ENTITY_TABLE = "MarkEntity";
	public static final String BETWEEN_FORMAT_TABLE = "BetweenFormat";
	public static final String LESS_THAN_FORMAT_TABLE = "LessThanFormat";
	public static final String GREATER_THAN_FORMAT_TABLE = "GreaterThanFormat";
	public static final String BORDERCASE_TABLE = "BorderCase";
	
	
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
        			"defaultWeight float" +
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + ENTITY_TABLE + "(" +
        			"entityID int NOT NULL PRIMARY KEY," +
        			"parentID int ," +
        			"typeID int" +
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + BEST_N_ENTITY_TABLE + "(" +
        			"entityID int NOT NULL PRIMARY KEY," +
        			"N int"+
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + MARK_ENTITY_TABLE + "(" +
        			"entityID int NOT NULL PRIMARY KEY," +
        			"mark int"+
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + STRING_ENTITY_TABLE + "(" +
        			"entityID int NOT NULL PRIMARY KEY," +
        			"field varchar(1000)"+
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + IMG_ENTITY_TABLE + "(" +
        			"entityID int NOT NULL PRIMARY KEY," +
        			"address varchar(1000)"+
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + GREATER_THAN_FORMAT_TABLE + "(" +
        			"typeID int ," +
        			"value1 float " +
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + LESS_THAN_FORMAT_TABLE + "(" +
        			"typeID int ," +
        			"value1 float " +
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + BETWEEN_FORMAT_TABLE + "(" +
        			"typeID int ," +
        			"value1 float ," +
        			"value2 float " +
        			")";
        	db.createTable(createTableQuery);
        	
        	createTableQuery = "CREATE TABLE " + BORDERCASE_TABLE + "(" +
        			"typeID int ," +
        			"lowval float ," +
        			"highval float " +
        			")";
        	db.createTable(createTableQuery);
        	
        }catch (Exception e) {
				e.printStackTrace();
			}
        finally {
            db.commit();
        }
        
        project.saveToDB(db);
        
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
