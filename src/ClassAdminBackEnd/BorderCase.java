package ClassAdminBackEnd;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class BorderCase {
	private Double lowVal;
	private Double highVal;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BorderCase [lowVal=");
		builder.append(lowVal);
		builder.append(", highVal=");
		builder.append(highVal);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @param lowVal
	 * @param highVal
	 */
	public BorderCase(Double lowVal, Double highVal) {
		this.lowVal = lowVal;
		this.highVal = highVal;
	}

	public Double getLowVal() {
		return lowVal;
	}

	public void setLowVal(Double lowVal) {
		this.lowVal = lowVal;
	}

	public Double getHighVal() {
		return highVal;
	}

	public void setHighVal(Double highVal) {
		this.highVal = highVal;
	}
	
	/**
	 * Returns if the MarkEntity is a borderCase or not
	 * @param markEntity
	 * @return boolean
	 */
	public boolean isBorderCase(SuperEntity markE){
		if((markE.getMark() < highVal) && (markE.getMark() > lowVal)){
			return true;
		}
		
		return false;
	}
	
	public int saveToDB(SqlJetDb db, int parentID, PDatIDGenerator idgen) throws SqlJetException {

		db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
        	//TODO
        	ISqlJetTable table = db.getTable(PDatExport.ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(parentID+", "+this.getLowVal()+", "+this.getHighVal());
        } finally {
            db.commit();
            
        }
        return 0;
	}
}
