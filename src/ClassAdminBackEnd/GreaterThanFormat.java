package ClassAdminBackEnd;

import java.awt.Color;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class GreaterThanFormat extends Format {
	public static int numberOfValues = 1;

	/**
	 * @param value1
	 * @param textColor
	 * @param highlightColor
	 * @param description
	 */
	public GreaterThanFormat(/*int condition, /*int priority,*/ Double value1, Color textColor, Color highlightColor,
			String description) {
		super(/* priority, */value1, textColor, highlightColor,
				description);
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.Format#evaluate(double)
	 * evaluate the mark to see if it should be formatted
	 */
	public Boolean evaluate(double mark){
		return (this.getValue1() < mark);
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.Format#saveToDB(org.tmatesoft.sqljet.core.table.SqlJetDb, long, ClassAdminBackEnd.PDatIDGenerator)
	 * was used to save to an sql database, now deprecated
	 */
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {
		db.beginTransaction(SqlJetTransactionMode.WRITE);

        	ISqlJetTable table = db.getTable(PDatExport.GREATER_THAN_FORMAT_TABLE);
        	//insert statements
        	long id = super.saveToDB(db, parentID, idgen);
        	table.insert(id,this.getValue1());

        return 0;
	}
}
