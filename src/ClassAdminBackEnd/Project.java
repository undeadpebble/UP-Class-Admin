/**
 * 
 */
package ClassAdminBackEnd;

import java.util.ArrayList;
import java.util.LinkedList;

import org.jfree.chart.JFreeChart;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import ClassAdminFrontEnd.BoxPlotFrame;
import ClassAdminFrontEnd.HistogramFrame;
import ClassAdminFrontEnd.ScatterPlotFrame;

import ClassAdminFrontEnd.FrmTable;
import Rule.Rule;

/**
 * @author undeadpebble
 *
 */
public class Project {
	private SuperEntity head;
	private LinkedList<SuperEntity> selected;
	private EntityType headEntityType;

	private ArrayList selectedIndexes = new ArrayList();
	private ArrayList scattercharts = new ArrayList();
	private ArrayList histogramcharts = new ArrayList();
	private ArrayList boxplotcharts = new ArrayList();
	
	private LinkedList<Rule> rules = new LinkedList<Rule>();
	private LinkedList<EntityType> treeLinkedList;
	private LinkedList<FrmTable> tables = new LinkedList<FrmTable>();
	

	public void clearselected()
	{
		selectedIndexes = new ArrayList();
	}
	
	public void addscattercharts(ScatterPlotFrame x)
	{
		
		scattercharts.add(x);
		
	}
	public void addboxplotcharts(BoxPlotFrame x)
	{
		
		scattercharts.add(x);
		
	}
	public void addhistogramcharts(HistogramFrame x)
	{
		
		histogramcharts.add(x);
		
	}
	public void updatecharts()
	{
		for(int i =0 ; i < scattercharts.size();i++)
		((ScatterPlotFrame) scattercharts.get(i)).update();
		for(int i =0 ; i < histogramcharts.size();i++)
		{
			//System.out.println("Ek update histogram");
		((HistogramFrame) histogramcharts.get(i)).update();
		}
	}

	public void setSelected(int x)
	{
		
		selectedIndexes.add(x);
		System.out.println("Set selected" +selectedIndexes.size());
		
	}
	public ArrayList getSelectedIndexes()
	{
		return selectedIndexes;
	}


	public void updateTables(){
		for(int x = 0; x < tables.size();x++){
			tables.get(x).redraw();
		}
	}
	
	public LinkedList<FrmTable> getTables(){
		return tables;
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
