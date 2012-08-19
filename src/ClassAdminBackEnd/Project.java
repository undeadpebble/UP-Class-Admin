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
	private LinkedList<EntityType> entityTypes;
	
	public SuperEntity getHead() {
		return head;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [entityTypes=");
		builder.append(entityTypes);
		builder.append(", head=");
		builder.append(head);
		builder.append("]");
		return builder.toString();
	}

	public void setHead(SuperEntity head) {
		this.head = head;

	}
	public LinkedList<SuperEntity> getSelected() {
		if(selected==null)
			selected = new LinkedList<SuperEntity>();
		return selected;
	}
	public LinkedList<EntityType> getEntityTypes() {
		if (entityTypes==null)
			entityTypes = new LinkedList<EntityType>();
		return entityTypes;
	}
	
	public void saveToDB(SqlJetDb db) throws SqlJetException{
		PDatIDGenerator idgen = new PDatIDGenerator();
		for(int x = 0;x<this.getEntityTypes().size();++x){
			this.getEntityTypes().get(x).saveToDB(db, 0, idgen);
		}
		
		this.head.saveToDB(db, 0, idgen);
	}
}
