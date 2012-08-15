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
	private LinkedList<SuperEntity> treeViewSelected;
	
	
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

	public void setTreeViewSelected(LinkedList<SuperEntity> superEntity)
	{
		treeViewSelected = superEntity;
	}
	public LinkedList<SuperEntity> getTreeViewSelected()
	{
		return treeViewSelected;
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
}
