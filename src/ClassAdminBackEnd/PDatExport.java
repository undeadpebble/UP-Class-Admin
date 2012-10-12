package ClassAdminBackEnd;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import ClassAdminFrontEnd.RecentDocument;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.query.QueryComparator;

public class PDatExport {
	public static final String ENTITY_TYPE_TABLE = "EntityType";
	public static final String ENTITY_TABLE = "Entity";
	public static final String BEST_N_ENTITY_TABLE = "BestNEntity";
	public static final String STRING_ENTITY_TABLE = "StringEntity";
	public static final String ABSENT_ENTITY_TABLE = "AbsentEntity";
	public static final String IMG_ENTITY_TABLE = "ImageEntity";
	public static final String MARK_ENTITY_TABLE = "MarkEntity";
	public static final String BETWEEN_FORMAT_TABLE = "BetweenFormat";
	public static final String LESS_THAN_FORMAT_TABLE = "LessThanFormat";
	public static final String GREATER_THAN_FORMAT_TABLE = "GreaterThanFormat";
	public static final String FORMAT_TABLE = "Format";
	public static final String BORDERCASE_TABLE = "BorderCase";

	public void exportFileDB4o(Project project, String filename) {
		
		File f = new File(filename);
		f.delete();
		ObjectContainer db = Db4o.openFile(filename);

		db.store(project);

		db.commit();
		db.close();

	}

	public void exportFile(Project project, String filename)
			throws SqlJetException {
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
			// create tables
			String createTableQuery = "CREATE TABLE " + ENTITY_TYPE_TABLE
					+ "(typeID INTEGER NOT NULL PRIMARY KEY," + "name TEXT ,"
					+ "parentID INTEGER," + "isText Boolean," + "date Date,"
					+ "defaultWeight FLOAT" + ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + ENTITY_TABLE + "("
					+ "entityID INTEGER NOT NULL PRIMARY KEY,"
					+ "parentID INTEGER ," + "typeID INTEGER" + ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + BEST_N_ENTITY_TABLE + "("
					+ "entityID int NOT NULL PRIMARY KEY," + "N INTEGER" + ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + ABSENT_ENTITY_TABLE + "("
					+ "entityID int NOT NULL PRIMARY KEY" + ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + MARK_ENTITY_TABLE + "("
					+ "entityID INTEGER NOT NULL PRIMARY KEY," + "mark FLOAT"
					+ ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + STRING_ENTITY_TABLE + "("
					+ "entityID INTEGER NOT NULL PRIMARY KEY,"
					+ "field varchar(1000)" + ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + IMG_ENTITY_TABLE + "("
					+ "entityID INTEGER NOT NULL PRIMARY KEY,"
					+ "address varchar(1000)" + ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + FORMAT_TABLE + "("
					+ "formatID INTEGER NOT NULL PRIMARY KEY,"
					+ "typeID INTEGER ," + "textColor INTEGER ,"
					+ "highlightColor INTEGER ," + "description varchar(1000) "
					+ ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + GREATER_THAN_FORMAT_TABLE
					+ "(" + "formatID INTEGER NOT NULL PRIMARY KEY,"
					+ "value1 float " + ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + LESS_THAN_FORMAT_TABLE + "("
					+ "formatID INTEGER NOT NULL PRIMARY KEY,"
					+ "value1 float " + ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + BETWEEN_FORMAT_TABLE + "("
					+ "formatID INTEGER NOT NULL PRIMARY KEY,"
					+ "value1 float ," + "value2 float " + ")";
			db.createTable(createTableQuery);

			createTableQuery = "CREATE TABLE " + BORDERCASE_TABLE + "("
					+ "typeID INTEGER NOT NULL PRIMARY KEY," + "lowval float ,"
					+ "highval float " + ")";
			db.createTable(createTableQuery);

			project.saveToDB(db);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.commit();
		}

	}

	private static void printRecords(ISqlJetCursor cursor)
			throws SqlJetException {
		try {
			if (!cursor.eof()) {
				do {
					System.out.println(cursor.getRowId() + " : "
							+ cursor.getString("") + " " + cursor.getString("")
							+ " was born on ");
					// formatDate(cursor.getInteger("")));
				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

}
