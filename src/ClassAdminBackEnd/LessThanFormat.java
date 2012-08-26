package ClassAdminBackEnd;

import java.awt.Color;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class LessThanFormat extends Format {
	public static int numberOfValues = 1;

	public LessThanFormat(Double value1, Color textColor, Color highlightColor,
			String description) {
		super(value1, textColor, highlightColor,
				description);
	}
	
	public Boolean evaluate(double mark){
		return this.getValue1() >= mark;
	}
	
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {
		db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
        	//TODO
        	ISqlJetTable table = db.getTable(PDatExport.GREATER_THAN_FORMAT_TABLE);
        	//insert statements
        	long id = super.saveToDB(db, parentID, idgen);
        	
        	table.insert(id+", "+this.getValue1());
        } finally {
            db.commit();
            
        }
        return 0;
	}

}
