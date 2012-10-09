/**
 * 
 */
package ClassAdminBackEnd;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.imageio.ImageIO;

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
	private int[] scatterIndexes;
	private ArrayList scatterArrayListIndexes = new ArrayList();
	private LinkedList<Rule> rules = new LinkedList<Rule>();
	private LinkedList<EntityType> treeLinkedList;
	private LinkedList<SuperEntity> studentLinkedList;
	private LinkedList<FrmTable> tables = new LinkedList<FrmTable>();
	private boolean cleared = false;
	private String fileName;
	private Audit audit;
	private int histogramdatacount = -1;
	private int scatterdatacount = -1;

	public void load(Project p) {
		this.audit = p.audit;
		this.boxplotcharts = p.boxplotcharts;
		this.cleared = p.cleared;
		this.fileName = p.fileName;
		this.head = p.head;
		this.headEntityType = p.headEntityType;
		this.histogramcharts = p.histogramcharts;
		this.histogramdatacount = p.histogramdatacount;
		this.rules = p.rules;
		this.scatterArrayListIndexes = p.scatterArrayListIndexes;
		this.scattercharts = p.scattercharts;
		this.scatterdatacount = p.scatterdatacount;
		this.scatterIndexes = p.scatterIndexes;
		this.selected = p.selected;
		this.selectedIndexes = p.selectedIndexes;
		this.studentLinkedList = p.studentLinkedList;
		this.tables = p.tables;
		this.treeLinkedList = p.treeLinkedList;
	}

	public int getHistogramcount() {
		return histogramdatacount;
	}

	public void incHistogramcount() {
		int modgetal = this.getHead().getNumberHeaders().length;

		histogramdatacount = histogramdatacount + 1;

		histogramdatacount = histogramdatacount % modgetal;

	}

	public int getscattercount() {
		return scatterdatacount;
	}

	public void incscattercount() {
		int modgetal = this.getHead().getNumberHeaders().length;

		scatterdatacount = scatterdatacount + 1;

		scatterdatacount = scatterdatacount % (modgetal - 1);

	}

	public void clearselected() {

		this.getSelectedIndexes().clear();
		this.getSelected().clear();
		updatecharts();
		for (int y = 0; y < tables.size(); y++)
			tables.get(y).repaint();
	}

	public boolean getCleared() {
		return cleared;
	}

	public void setCleared(boolean x) {

		cleared = x;
	}

	public void addscattercharts(ScatterPlotFrame x) {

		scattercharts.add(x);

	}

	public void addboxplotcharts(BoxPlotFrame x) {

		boxplotcharts.add(x);

	}

	public void addhistogramcharts(HistogramFrame x) {

		histogramcharts.add(x);

	}

	public void updatecharts() {
		for (int i = 0; i < scattercharts.size(); i++)
			((ScatterPlotFrame) scattercharts.get(i)).update();
		for (int i = 0; i < histogramcharts.size(); i++) {
			((HistogramFrame) histogramcharts.get(i)).update();
		}
	}

	public void setSelected(int x, boolean toetso) {

		boolean duplicate = false;
		for (int i = 0; i < selectedIndexes.size(); i++) {

			if ((Integer) selectedIndexes.get(i) == x)
				duplicate = true;
		}
		if (duplicate == false) {
			selectedIndexes.add(x);

			for (int w = 0; w < this.getHead().getDataLinkedList().get(0)
					.size(); w++)
				this.getSelected().add(
						this.getHead().getDataLinkedList().get(x).get(w));
			if (scatterIndexes != null) {
				scatterArrayListIndexes.add(scatterIndexes[x]);

			}
			updatecharts();
			if (toetso == true)
				for (int y = 0; y < tables.size(); y++) {

					tables.get(y).getTable().clearSelection();

					tables.get(y).getTable().repaint();
				}
		}

	}

	public ArrayList getSelectedIndexes() {
		return selectedIndexes;
	}

	public void setScatterSelect(int[] x) {
		scatterIndexes = x;

	}

	public int[] getScatterIndexes() {
		return scatterIndexes;
	}

	public void updateTables() {
		for (int x = 0; x < tables.size(); x++) {
			tables.get(x).redraw();
		}
	}

	public LinkedList<FrmTable> getTables() {
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
	 * @param headEntityType
	 *            the headEntityType to set
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
		if (selected == null)
			selected = new LinkedList<SuperEntity>();
		return selected;
	}

	public void saveToDB(SqlJetDb db) throws SqlJetException {
		PDatIDGenerator idgen = new PDatIDGenerator();
		this.headEntityType.saveToDB(db, new Long(0), idgen);

		this.head.saveToDB(db, 0, idgen);

	}

	public LinkedList<EntityType> getTreeLinkedList() {
		if (treeLinkedList == null)
			treeLinkedList = new LinkedList<EntityType>();
		return treeLinkedList;
	}

	public LinkedList<SuperEntity> getStudentLinkedList() {
		if (studentLinkedList == null)
			studentLinkedList = new LinkedList<SuperEntity>();
		return studentLinkedList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fname) {
		fileName = fname;
	}

	public void createAudit() {
		try {
			audit = new Audit(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Audit getAudit() {
		return audit;
	}

	public void setPictures(String dir) {
		String files;
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();

		EntityType images = new EntityType("Pictures", this.getHeadEntityType()
				.getSubEntityType().get(0), true, null, 0.0);
		images.setIsImg(true);
		images.setIsTextField(true);
		images.populateTreeWithEntities();

		LinkedList<LinkedList<SuperEntity>> data = getHead()
				.getDataLinkedList();

		int where = -1;

		for (int y = 0; y < data.get(0).size(); y++) {
			if (data.get(0).get(y).getType().getIsImg()) {
				where = y;
				break;
			}
		}



		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				String filesFull = listOfFiles[i].getName();
				if (files.endsWith(".png") || files.endsWith(".PNG")
						|| files.endsWith(".jpg") || files.endsWith(".JPG")) {
					files = files.substring(0, files.lastIndexOf('.'));

					if (where != -1)
						for (int x = 0; x < data.size(); x++) {
							for (int y = 0; y < data.get(0).size(); y++) {
								SuperEntity temp = data.get(x).get(y);
								if (temp.getType().getIsTextField()) {
									if (temp.getValue().equals(files)) {
										try{
										((IMGEntity)data.get(x).get(where))
												.setImage(ImageIO.read(new File(dir + "\\" + filesFull)));
										data.get(x).get(where).setField(filesFull);
										data.get(x).get(where).setPicture(dir + "\\" + filesFull);
										}
										catch (Exception e) {
										}
										if(this.tables.size() > 0)
											this.tables.get(0).redraw();
									}
								}
							}
						}
				}
			}
		}
	}
}
