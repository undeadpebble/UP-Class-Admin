package ClassAdminBackEnd;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class StringEntity extends SuperEntity{

	public StringEntity(EntityType type, SuperEntity parentEntity, String text) {
		super(type, parentEntity, 0);
		this.setField(text);
		
		// TODO Auto-generated constructor stub
	}
	
	public StringEntity(SuperEntity replacedEntity, String text){
		super(replacedEntity);
		this.setField(text);
	}
	

	public String getValue(){
		//TODO
			return this.getField();
	}
	
	public void setValue(String newValue){
		this.setField(newValue);
	}
	
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {
		long id = super.saveToDB(db, parentID, idgen);
		db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
        	//TODO
        	ISqlJetTable table = db.getTable(PDatExport.STRING_ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(id+", "+this.getField());
        } finally {
            db.commit();
            
        }
        return id;
	}

	
}
