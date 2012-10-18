package ClassAdminBackEnd;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;

public class EntityDetails {
	private EntityType type;
	private LinkedList<String> fields;
	private String Picture;
	private Color highlightColor;
	private Color textColor;
	private Boolean absentExcuse;
	
	/**
	 * @param type
	 * @param fields
	 * @param picture
	 * @param highlightColor
	 * @param textColor
	 * @param absentExcuse
	 */
	public EntityDetails(EntityType type, LinkedList<String> fields,
			 Color highlightColor, Color textColor,
			Boolean absentExcuse) {
		this.type = type;
		this.fields = fields;
		this.highlightColor = highlightColor;
		this.textColor = textColor;
		this.absentExcuse = absentExcuse;
	}
	public EntityDetails(int type){
		
	}

	/**
	 * @return
	 */
	public EntityType getType() {
		return type;
	}
	/**
	 * @param type
	 */
	public void setType(EntityType type) {
		this.type = type;
	}
	/**
	 * @return
	 */
	public LinkedList<String> getFields() {
		if(fields == null)
			fields = new LinkedList<String>();
		return fields;
	}

	/**
	 * @return
	 */
	public String getPicture() {
		return Picture;
	}
	/**
	 * @param picture
	 */
	public void setPicture(String picture) {
		Picture = picture;
	}
	/**
	 * @return
	 */
	public Color getHighlightColor() {
		return highlightColor;
	}
	/**
	 * @param highlightColor
	 */
	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}
	/**
	 * @return
	 */
	public Color getTextColor() {
		return textColor;
	}
	/**
	 * @param textColor
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	/**
	 * @return
	 */
	public Boolean getAbsentExcuse() {
		return absentExcuse;
	}
	/**
	 * @param absentExcuse
	 */
	public void setAbsentExcuse(Boolean absentExcuse) {
		this.absentExcuse = absentExcuse;
	}
	
	/**
	 * @param newValue
	 */
	public void setValue(String newValue){
		fields.set(0, newValue);
	}
	
}
