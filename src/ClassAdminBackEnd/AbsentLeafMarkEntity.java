package ClassAdminBackEnd;

import java.util.Date;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 * @author undeadpebble
 *
 */
public class AbsentLeafMarkEntity extends LeafMarkEntity{


	/**
	 * @param type
	 * @param parentEntity
	 */
	public AbsentLeafMarkEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity, 0);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param replacedEntity
	 */
	public AbsentLeafMarkEntity(SuperEntity replacedEntity) {
		super(replacedEntity,0);
		// TODO Auto-generated constructor stub
	}
	
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.LeafMarkEntity#unLeaf()
	 * Replace this entity with a nen-leaf version
	 */
	public SuperEntity unLeaf(){
		return new MarkEntity(this);
	}

	public double getMark() throws AbsentException{
		throw new AbsentException();
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.MarkEntity#saveToDB(org.tmatesoft.sqljet.core.table.SqlJetDb, long, ClassAdminBackEnd.PDatIDGenerator)
	 * was used to save to an sql database, now deprecated
	 */
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {
		long id = super.saveToDB(db, parentID, idgen);
		db.beginTransaction(SqlJetTransactionMode.WRITE);


        	ISqlJetTable table = db.getTable(PDatExport.ABSENT_ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(id);
        
        return id;
	}
}
