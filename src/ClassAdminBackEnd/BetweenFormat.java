package ClassAdminBackEnd;

import java.awt.Color;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class BetweenFormat extends Format {
	Double value2;
	public static int numberOfValues = 1;

	/**
	 * @return the value2
	 */
	public Double getValue2() {
		return value2;
	}

	/**
	 * @param value2 the value2 to set
	 */
	public void setValue2(Double value2) {
		this.value2 = value2;
	}

	public BetweenFormat(/*int priority, */Double value1, Double value2, Color textColor,
			Color highlightColor, String description) {
		super(/*priority, */value1, textColor, highlightColor, description);
		if(value1 < value2)
			this.value2=value2;
		else{
			this.value2 = value1;
			this.setValue1(value2);
		}
		
		// TODO Auto-generated constructor stub
	}
	
	public Boolean evaluate(double mark){
		return (this.getValue1() <= mark && mark <= this.getValue2());
	}	
	
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {

		db.beginTransaction(SqlJetTransactionMode.WRITE);

        	ISqlJetTable table = db.getTable(PDatExport.GREATER_THAN_FORMAT_TABLE);
        	//insert statements
        	long id = super.saveToDB(db, parentID, idgen);
        	
        	table.insert(id,this.getValue1(),this.getValue2());

        return 0;
	}

}
