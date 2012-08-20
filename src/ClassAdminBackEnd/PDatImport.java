package ClassAdminBackEnd;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.internal.table.SqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class PDatImport {
	LinkedList<SuperEntity> entityList = new LinkedList<SuperEntity>();
	LinkedList<Integer> entityList_ID = new LinkedList<Integer>();
	
	LinkedList<EntityType> entityTypeList = new LinkedList<EntityType>();
	LinkedList<Integer> entityTypeList_ID = new LinkedList<Integer>();
	
	public void importFile(String filename) throws SqlJetException{
		File dbFile = new File(filename);
		
		SqlJetDb db = SqlJetDb.open(dbFile, true);
	}
	
	private void readTypes(SqlJetDb db) throws SqlJetException{
		ISqlJetTable table = db.getTable(PDatExport.ENTITY_TYPE_TABLE);
		ISqlJetCursor cursor = table.open();
		try {
		      if (!cursor.eof()) {
		        do {
		          System.out.println(cursor.getRowId() + " : " + 
		                             cursor.getInteger("typeID") + " " + 
		                             cursor.getString("name") + " was born on " + 
		                             cursor.getBoolean("isText") + " was born on " + 
		                             cursor.getFloat("defaultWeight") + " was born on " + 
		                             new Date(cursor.getInteger("date")));
		          
		       
		         } while(cursor.next());
		      }
		    } finally {
		      cursor.close();
		    }

	}
}
