package ClassAdminBackEnd;

import java.awt.Color;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class GreaterThanFormat extends Format {
	public static int numberOfValues = 1;

	public GreaterThanFormat(/*int condition, /*int priority,*/ Double value1, Color textColor, Color highlightColor,
			String description) {
		super(/* priority, */value1, textColor, highlightColor,
				description);
		super.type = 2;
		// TODO Auto-generated constructor stub
	}

	public Boolean evaluate(double mark){
		return (this.getValue1() < mark);
	}

	public int saveToDB(SqlJetDb db, int parentID, PDatIDGenerator idgen) throws SqlJetException {
		db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
        	//TODO
        	ISqlJetTable table = db.getTable(PDatExport.GREATER_THAN_FORMAT_TABLE);
        	//insert statements
        	
        	table.insert(parentID+", "+this.getValue1());
        } finally {
            db.commit();
            
        }
        return 0;
	}
}
