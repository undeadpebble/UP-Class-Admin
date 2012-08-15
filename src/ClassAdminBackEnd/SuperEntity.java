package ClassAdminBackEnd;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;

public class SuperEntity {


	private SuperEntity parentEntity;
	private LinkedList<SuperEntity> subEntity = new LinkedList<SuperEntity>();
	private LinkedList<Double> subEntityWeight = new LinkedList<Double>();
	private double mark;
	private int rowFollowCount = 0;
	private EntityType type;
	private LinkedList<String> fields;
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
		this.type = type;
	}
	/**
	 * @return the fields
	 */
	public LinkedList<String> getFields() {
		if(fields == null)
			fields = new LinkedList<String>();
		return fields;
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
		this.type = type;
		this.parentEntity = parentEntity.unLeaf();
		this.mark = mark;
		this.parentEntity.getSubEntity().add(this);
		this.parentEntity.getSubEntityWeight().add(this.getType().getDefaultWeight());

	}
	
	public SuperEntity(SuperEntity replacedEntity){
		this.type = replacedEntity.getType();
		this.parentEntity = replacedEntity.getParentEntity();
		this.mark = replacedEntity.getMark();
		this.fields = replacedEntity.getFields();
		this.subEntity = replacedEntity.getSubEntity();
		this.subEntityWeight = replacedEntity.getSubEntityWeight();
		int index = replacedEntity.getParentEntity().getSubEntity().indexOf(replacedEntity);
		replacedEntity.getParentEntity().getSubEntity().set(index, this);
		replacedEntity.getParentEntity().getSubEntityWeight().set(index, this.getType().getDefaultWeight());
	}
	public SuperEntity(EntityType type, double mark){
		this.type = type;
		this.mark = mark;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		
		builder.append("MarkEntity [mark=");
		builder.append(mark);
		builder.append(", subEntityWeight=");
		builder.append(subEntityWeight);
		builder.append(", subEntity=");
		builder.append(subEntity);
		builder.append("]");
		
		return builder.toString();
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
	public LinkedList<Double> getSubEntityWeight() {
		if(this.subEntityWeight == null)
			this.subEntityWeight = new LinkedList<Double>();
		return subEntityWeight;
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
	
	private Double doMarkMath(){
		double mTotal = 0;
		double wTotal = 0;
		for (int i = 0; i < subEntity.size(); ++i) {
			try {
				mTotal += subEntity.get(i).calcMark()
						* subEntityWeight.get(i);
				wTotal += subEntityWeight.get(i);
			} catch (Exception e) {
			}

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
		String heads = subEntity.get(0).getHeadersString();		

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
	
	

	
	public LinkedList<EntityType> getHeadersLinkedList(){
		LinkedList<EntityType> lEntity = new LinkedList<EntityType>();
		
		//lEntity.add(this.getType());
		
		this.subEntity.get(0).getLinkedListData(lEntity);
		
		return lEntity;
	}
	
	
	private void getLinkedListData(LinkedList<EntityType> lEntity){		
		lEntity.add(this.getType());
		
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
	
	public LinkedList<SuperEntity> getColumn(LinkedList<LinkedList<SuperEntity>> data,int kolumn){
		return(data.get(kolumn));
	}
	
	public String[] getNumberHeaders(){
		LinkedList<EntityType> list = this.getHeadersLinkedList();
		LinkedList<String> strlst = new LinkedList<String>();
		
		for(int x = 0; x < list.size(); x++){
			if(!list.get(x).getIsTextField()){
				strlst.add(list.get(x).getName());
			}
		}
		
		String[] str = new String[strlst.size()];
		
		for(int x = 0; x < strlst.size(); x++){
			str[x] = strlst.get(x);
		}
		
		return(str);
	}
}
