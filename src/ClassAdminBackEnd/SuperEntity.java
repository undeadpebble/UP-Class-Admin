package ClassAdminBackEnd;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class SuperEntity {


	private SuperEntity parentEntity;
	private LinkedList<SuperEntity> subEntity = new LinkedList<SuperEntity>();
	private double weight;
	private double mark;
	private int rowFollowCount = 0;
	private EntityType type;
	private String field = "";
	/**
	 * @return the type
	 */
	public EntityType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(EntityType type) {
		if(this.type != null){
			this.type.getEntityList().remove(this);
		}
		this.type = type;
		this.type.getEntityList().add(this);
	}
	/**
	 * @return the fields
	 */
	public String getField() {
		return field;
	}
	
	public void setField(String field){
		this.field=field;
	}
	/**
	 * @return the picture
	 */
	public String getPicture() {
		return Picture;
	}
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		Picture = picture;
	}
	/**
	 * @return the highlightColor
	 */
	public Color getHighlightColor() {
		return highlightColor;
	}
	/**
	 * @param highlightColor the highlightColor to set
	 */
	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}
	/**
	 * @return the textColor
	 */
	public Color getTextColor() {
		return textColor;
	}
	/**
	 * @param textColor the textColor to set
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	private String Picture;
	private Color highlightColor;
	private Color textColor;

	/**
	 * @param parentEntity
	 * @param subEntity
	 * @param subEntityWeight
	 * @param details
	 * @param mark
	 */
	
	public SuperEntity(EntityType type, SuperEntity parentEntity, double mark) {
		this.setType(type);
		this.parentEntity = parentEntity.unLeaf();
		this.mark = mark;
		this.parentEntity.getSubEntity().add(this);
		this.weight = this.getType().getDefaultWeight();

	}
	
	public SuperEntity(SuperEntity replacedEntity){
		this.setType(replacedEntity.getType());
		replacedEntity.getType().getEntityList().remove(replacedEntity);
		
		this.parentEntity = replacedEntity.getParentEntity();
		this.mark = replacedEntity.getMark();
		this.field = replacedEntity.getField();
		this.subEntity = replacedEntity.getSubEntity();
		this.weight = replacedEntity.getWeight();

		int index = this.getParentEntity().getSubEntity().indexOf(replacedEntity);
		this.getParentEntity().getSubEntity().set(index, this);
		
	}
	public SuperEntity(EntityType type, double mark){
		this.setType(type);
		this.mark = mark;

	}


	/**
	 * @return the rowFollowCount
	 */
	public int getRowFollowCount() {
		return rowFollowCount;
	}

	/**
	 * increases the rowFollowCount
	 */
	public void increaseRowFollowCount(){
		this.rowFollowCount++;
	}

	/**
	 * @return the parentEntity
	 */
	public SuperEntity getParentEntity() {
		return parentEntity;
	}

	/**
	 * @param parentEntity
	 *            the parentEntity to set
	 */
	public void setParentEntity(SuperEntity parentEntity) {
		this.parentEntity = parentEntity;
	}

	/**
	 * @return the subEntity
	 */
	public LinkedList<SuperEntity> getSubEntity() {
		if(subEntity == null)
			this.subEntity = new LinkedList<SuperEntity>();
		return subEntity;
	}


	/**
	 * @return the subEntityWeight
	 */
	public double getWeight() {
		
		return weight;
	}
	
	public void setWeight(double weight){
		this.weight = weight;
	}

	/**
	 * @return the mark
	 */
	public double getMark() {
		return mark;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(double mark) {
		this.mark = mark;
	}

	/**
	 * @return the details
	 */
	public SuperEntity getDetails() {
		return this;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	
	private Boolean isAbsent(){
		return this.getType().getDate() != null && this.getType().getDate().after(new Date());
	}
	
	public SuperEntity unLeaf(){
		return this;
	}
	
	private Double doMarkMath() throws AbsentException{
		double mTotal = 0;
		double wTotal = 0;
		Boolean hasval = false;
		for (int i = 0; i < subEntity.size(); ++i) {
			try {
				mTotal += subEntity.get(i).calcMark()
						* subEntity.get(i).getWeight();
				wTotal += subEntity.get(i).getWeight();
				
				hasval = true;
			} catch (Exception e) {
			}

		}

		
		if(!hasval){
			throw new AbsentException();
		}
		
		if (wTotal != 0)
			return mTotal / wTotal;
		else
			return mTotal;
	}

	/**
	 * @return
	 * @throws AbsentException
	 */
	
	public double calcMark() throws Exception {
		if (this.isAbsent()) {
			throw new AbsentException();
		} else {
			this.mark = doMarkMath();
			return this.mark;
		}

	}
	
	
	public String[] getHeaders(){
		/*int max = -1;
		int maxe =-1;
		for(int x =0; x < subEntity.size();x++){
			if(subEntity.get(x).getRowFollowCount() > max){
				max = subEntity.get(x).getRowFollowCount();
				maxe = x;
			}
		}*/
		String heads = subEntity.get(0).getHeadersString();		
		//System.out.println(heads);
		
		//System.out.print(heads);
		String[] s = heads.split("bn f3hjjm3734n  5f6 34h 35g635 346n34f f g46345f");
		return s; 
			
	}
	
	private String getHeadersString(){
		String str = this.getDetails().getType().getName();
		
		for(int x = 0; x < this.subEntity.size();x++){
			str = str + "bn f3hjjm3734n  5f6 34h 35g635 346n34f f g46345f" + this.subEntity.get(x).getHeadersString();
		}
		
		return str;
	}
	
	

	
	public LinkedList<SuperEntity> getHeadersLinkedList(){
		LinkedList<SuperEntity> lEntity = new LinkedList<SuperEntity>();
		
		lEntity.add(this);
		
		for(int x = 0; x < this.subEntity.size();x++){
			this.subEntity.get(x).getLinkedListData(lEntity);
		}
		
		return lEntity;
	}
	
	
	private void getLinkedListData(LinkedList<SuperEntity> lEntity){		
		lEntity.add(this);
		
		for(int x = 0; x < this.subEntity.size();x++){
			this.subEntity.get(x).getLinkedListData(lEntity);
		}
			
	}
	
	public LinkedList<LinkedList<SuperEntity>> getDataLinkedList(){
		LinkedList<LinkedList<SuperEntity>> linkLinkEntity = new LinkedList<LinkedList<SuperEntity>>();
		
		for(int x = 0; x < this.getSubEntity().size();x++){
			linkLinkEntity.add(new LinkedList<SuperEntity>());
			this.getSubEntity().get(x).addDataToLinkedList(linkLinkEntity.get(x));
		}
		
		return linkLinkEntity;
	}
	
	private void addDataToLinkedList(LinkedList<SuperEntity> linkLinkEntity){
		linkLinkEntity.add(this);
		for(int x = 0; x < this.getSubEntity().size();x++){
			this.getSubEntity().get(x).addDataToLinkedList(linkLinkEntity);
		}
	}
	
	public String getValue(){
		//TODO
		return "";
	}
	
	public void setValue(String str){
		this.setMark(Double.parseDouble(str));
	}
	
	public String[][] getData(){		
		String[][] sData = new String[subEntity.size()][]; 
		
		for(int x = 0; x < subEntity.size();x++){
			String heads = subEntity.get(x).getDataString();
			//System.out.println(heads);
			
			String[] s = heads.split("qwerpoiu");
			
			sData[x] = s;
		}
		
		
		return sData; 
			
	}
	
	private String getDataString(){
		String str;
		
		str = this.getValue();	
		
		for(int x = 0; x < this.subEntity.size();x++){
			str = str + "qwerpoiu" + this.subEntity.get(x).getDataString();
		}
		
		return str;
	}
	
	public int saveToDB(SqlJetDb db, int parentID, PDatIDGenerator idgen) throws SqlJetException{
		db.beginTransaction(SqlJetTransactionMode.WRITE);
		int id = idgen.getID();
        try {
        	//TODO
        	ISqlJetTable table = db.getTable(PDatExport.ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(id+", "+parentID+", "+this.type.getID());
        } finally {
            db.commit();
            
        }
        for(int x = 0;x<this.getSubEntity().size();++x){
        	this.getSubEntity().get(x).saveToDB(db, id, idgen);
        }
        return id;
	}
	
	public LinkedList<SuperEntity> getColumn(LinkedList<LinkedList<SuperEntity>> data,int kolumn){
		return(data.get(kolumn));
	}
	
	public String[] getNumberHeaders(){
		LinkedList<SuperEntity> list = this.getHeadersLinkedList();
		LinkedList<String> strlst = new LinkedList<String>();
		
		for(int x = 0; x < list.size(); x++){
			if(!list.get(x).getType().getIsTextField()){
				strlst.add(list.get(x).getType().getName());
			}
		}
		
		String[] str = new String[strlst.size()];
		
		for(int x = 0; x < strlst.size(); x++){
			str[x] = strlst.get(x);
		}
		
		return(str);
	}
	
	public String createTreeFromHead()
	{
		String str = "";
		str += "<branch>" +
				"<attribute name = \"name\" value= \"" + this.getValue() + "\" />";
		for (int i = 0; i < this.getSubEntity().size(); i++)
		{
			str += this.getSubEntity().get(i).createTreeFromHead();
		}
		str +="</branch>";
		return str;
	}
}
