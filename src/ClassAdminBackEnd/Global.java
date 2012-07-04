package ClassAdminBackEnd;

import java.util.LinkedList;

public class Global {
	private MarkEntity head;
	private LinkedList<MarkEntity> selected;
	private LinkedList<EntityType> entityTypes;
	private LinkedList<GraphType> graphTypes;
	private static Global instance;
	private Global(){
		
	
	}
	
	public static Global getGlobal(){
		if (instance == null)
			instance = new Global();
		
		return instance;
	}
	
	public MarkEntity getHead() {
		return head;
	}
	public void setHead(MarkEntity head) {
		this.head = head;
	}
	public LinkedList<MarkEntity> getSelected() {
		return selected;
	}
	public void setSelected(LinkedList<MarkEntity> selected) {
		this.selected = selected;
	}
	public LinkedList<GraphType> getGraphTypes() {
		return graphTypes;
	}
	public void setGraphTypes(LinkedList<GraphType> graphTypes) {
		this.graphTypes = graphTypes;
	}

}
