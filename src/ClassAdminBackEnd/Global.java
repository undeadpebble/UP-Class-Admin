package ClassAdminBackEnd;

import java.util.LinkedList;

public class Global {
	private MarkEntity head;
	private LinkedList<MarkEntity> selected;
	private LinkedList<GraphType> graphTypes;
	
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
