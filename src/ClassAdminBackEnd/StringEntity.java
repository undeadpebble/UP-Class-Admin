package ClassAdminBackEnd;

import java.util.LinkedList;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class StringEntity extends SuperEntity{

	/**
	 * @param type
	 * @param parentEntity
	 * @param text
	 */
	public StringEntity(EntityType type, SuperEntity parentEntity, String text) {
		super(type, parentEntity, 0);
		this.setField(text);
	}
	
	/**
	 * @param replacedEntity
	 * @param text
	 */
	public StringEntity(SuperEntity replacedEntity, String text){
		super(replacedEntity);
		this.setField(text);
	}
	

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#getValue()
	 */
	public String getValue(){
		//TODO
			return this.getField();
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String newValue) throws NumberFormatException{
		this.setField(newValue);
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#saveToDB(org.tmatesoft.sqljet.core.table.SqlJetDb, long, ClassAdminBackEnd.PDatIDGenerator)
	 * was used to save to an sql database, now deprecated
	 */
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {
		long id = super.saveToDB(db, parentID, idgen);
		db.beginTransaction(SqlJetTransactionMode.WRITE);

        	ISqlJetTable table = db.getTable(PDatExport.STRING_ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(id,this.getField());

        return id;
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#findStrings(java.util.LinkedList, int)
	 * was used to save to an sql database, now deprecated
	 */
	@Override
	public void findStrings(LinkedList<String> list, int depth) {
		if(list.size()>= 3)
			return;
		list.add(this.getType().getName()+": "+getField());
		super.findStrings(list, depth);
	}
	

	
}
