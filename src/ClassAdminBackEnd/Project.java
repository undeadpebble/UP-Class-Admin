/**
 * 
 */
package ClassAdminBackEnd;

import java.util.ArrayList;
import java.util.LinkedList;

import org.jfree.chart.JFreeChart;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import ClassAdminFrontEnd.ScatterPlotFrame;

/**
 * @author undeadpebble
 *
 */
public class Project {
	private SuperEntity head;
	private LinkedList<SuperEntity> selected;
	private EntityType headEntityType;

	private ArrayList selectedIndexes = new ArrayList();
	private ArrayList charts = new ArrayList();
	
	private LinkedList<Rule> rules = new LinkedList<Rule>();
	private LinkedList<EntityType> treeLinkedList;
	
	
	public void addscattercharts(ScatterPlotFrame x)
	{
		System.out.println("Charts selected");
		charts.add(x);
	}
	public void updatecharts()
	{
		System.out.println("ADASDDASDASD");
		((ScatterPlotFrame) charts.get(0)).update();
	}

	public void setSelected(int x)
	{
		System.out.println("Charts selected");
		selectedIndexes.add(x);
		
	}
	public ArrayList getSelectedIndexes()
	{
		return selectedIndexes;
	}

	
	public LinkedList<Rule> getRules() {
		return rules;
	}



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
	
	public LinkedList<EntityType> getTreeLinkedList()
	{
		if(treeLinkedList == null)
			treeLinkedList = new LinkedList<EntityType>();
		return treeLinkedList;
	}
}
