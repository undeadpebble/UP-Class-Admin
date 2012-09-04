package ClassAdminBackEnd;

import java.util.Date;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class AbsentLeafMarkEntity extends LeafMarkEntity{


	public AbsentLeafMarkEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity, 0);
		// TODO Auto-generated constructor stub
	}
	
	public AbsentLeafMarkEntity(SuperEntity replacedEntity) {
		super(replacedEntity,0);
		// TODO Auto-generated constructor stub
	}
	
	public SuperEntity unLeaf(){
		return new MarkEntity(this);
	}

	private Boolean isAbsent(){
		return true;
	}
	
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {
		long id = super.saveToDB(db, parentID, idgen);
		db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
        	//TODO
        	ISqlJetTable table = db.getTable(PDatExport.ABSENT_ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(id);
        } finally {
            db.commit();
            
        }
        return id;
	}
}
