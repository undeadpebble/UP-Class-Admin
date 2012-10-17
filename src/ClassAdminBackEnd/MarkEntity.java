package ClassAdminBackEnd;

import java.text.DecimalFormat;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class MarkEntity extends SuperEntity {

	/**
	 * @param type
	 * @param parentEntity
	 */
	public MarkEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity, 0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param replacedEntity
	 */
	public MarkEntity(SuperEntity replacedEntity) {
		super(replacedEntity);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#getValue()
	 */
	public String getValue() {

		try {
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			return Double.toString(Double.valueOf(twoDForm.format(this
					.getMark())));
		} catch (AbsentException e) {
			return "N/A";
		}

	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#saveToDB(org.tmatesoft.sqljet.core.table.SqlJetDb, long, ClassAdminBackEnd.PDatIDGenerator)
	 * was used to save to an sql database, now deprecated
	 */
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen)
			throws SqlJetException {
		long id = super.saveToDB(db, parentID, idgen);
		db.beginTransaction(SqlJetTransactionMode.WRITE);

		ISqlJetTable table = db.getTable(PDatExport.MARK_ENTITY_TABLE);
		// insert statements

		try {
			table.insert(id, this.getMark());
		} catch (AbsentException e) {
			table.insert(id, 0);
		}

		return id;
	}

}
