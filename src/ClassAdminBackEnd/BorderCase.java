package ClassAdminBackEnd;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class BorderCase {
	private Double lowVal;
	private Double highVal;
	

	/**
	 * @param lowVal
	 * @param highVal
	 */
	public BorderCase(Double lowVal, Double highVal) {
		if(lowVal > highVal){
			this.lowVal = highVal;
			this.highVal = lowVal;
		} else{
			this.lowVal = lowVal;
			this.highVal = highVal;
		}
	}

	/**
	 * @return
	 */
	public Double getLowVal() {
		return lowVal;
	}

	/**
	 * @param lowVal
	 */
	public void setLowVal(Double lowVal) {
		this.lowVal = lowVal;
	}

	/**
	 * @return
	 */
	public Double getHighVal() {
		return highVal;
	}

	/**
	 * @param highVal
	 */
	public void setHighVal(Double highVal) {
		this.highVal = highVal;
	}
	
	/**
	 * @param markE
	 * @return
	 */
	public boolean isBorderCase(SuperEntity markE){
		try {
			if((markE.getMark() < highVal) && (markE.getMark() >= lowVal)){
				return true;
			}
		} catch (AbsentException e) {
			
		}
		
		return false;
	}
	
	/**
	 * @param db
	 * @param parentID
	 * @param idgen
	 * @return
	 * @throws SqlJetException
	 * was used to save to an sql database, now deprecated
	 */
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {

		db.beginTransaction(SqlJetTransactionMode.WRITE);

        	ISqlJetTable table = db.getTable(PDatExport.ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(parentID,this.getLowVal(),this.getHighVal());

            
        
        return 0;
	}
}
