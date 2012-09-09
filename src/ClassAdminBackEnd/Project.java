/**
 * 
 */
package ClassAdminBackEnd;

import java.util.LinkedList;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 * @author undeadpebble
 *
 */
public class Project {
	private SuperEntity head;
	private LinkedList<SuperEntity> selected;
	private EntityType headEntityType;
	
	
	/**
	 * @return the headEntityType
	 */
	public EntityType getHeadEntityType() {
		return headEntityType;
	}


	/**
	 * @param headEntityType the headEntityType to set
	 */
	public void setHeadEntityType(EntityType headEntityType) {
		this.headEntityType = headEntityType;
	}


	public SuperEntity getHead() {
		return head;
	}
	

	public void setHead(SuperEntity head) {
		this.head = head;

	}
	public LinkedList<SuperEntity> getSelected() {
		if(selected==null)
			selected = new LinkedList<SuperEntity>();
		return selected;
	}

	public void saveToDB(SqlJetDb db) throws SqlJetException{
		PDatIDGenerator idgen = new PDatIDGenerator();
		this.headEntityType.saveToDB(db, new Long(0), idgen);

		
		this.head.saveToDB(db, 0, idgen);
		
	}
}
