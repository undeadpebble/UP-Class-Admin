/**
 * 
 */
package ClassAdminBackEnd;

import java.util.LinkedList;

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
	public void setHead(SuperEntity mE) {
		this.head = mE;
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
}
