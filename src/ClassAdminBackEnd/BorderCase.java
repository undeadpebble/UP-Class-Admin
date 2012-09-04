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
	
	public boolean isBorderCase(SuperEntity markE){
		if((markE.getMark() < highVal) && (markE.getMark() >= lowVal)){
			return true;
		}
		
		return false;
	}
	
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {

		db.beginTransaction(SqlJetTransactionMode.WRITE);

        	ISqlJetTable table = db.getTable(PDatExport.ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(parentID,this.getLowVal(),this.getHighVal());

            db.commit();
            
        
        return 0;
	}
}
