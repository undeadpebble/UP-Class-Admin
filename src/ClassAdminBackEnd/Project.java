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
	private MarkEntity head;
	private LinkedList<MarkEntity> selected;
	private LinkedList<EntityType> entityTypes;
	
	public MarkEntity getHead() {
		return head;
	}
	public void setHead(MarkEntity head) {
		this.head = head;
	}
	public LinkedList<MarkEntity> getSelected() {
		if(selected==null)
			selected = new LinkedList<MarkEntity>();
		return selected;
	}
	public LinkedList<EntityType> getEntityTypes() {
		if (entityTypes==null)
			entityTypes = new LinkedList<EntityType>();
		return entityTypes;
	}
}
