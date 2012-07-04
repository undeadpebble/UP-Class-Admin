package ClassAdminBackEnd;

import java.awt.Color;
import java.awt.Image;
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
	public EntityType getType() {
		return type;
	}
	public void setType(EntityType type) {
		this.type = type;
	}
	public LinkedList<String> getFields() {
		return fields;
	}
	public void setFields(LinkedList<String> fields) {
		this.fields = fields;
	}
	public String getPicture() {
		return Picture;
	}
	public void setPicture(String picture) {
		Picture = picture;
	}
	public Color getHighlightColor() {
		return highlightColor;
	}
	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}
	public Color getTextColor() {
		return textColor;
	}
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	public Boolean getAbsentExcuse() {
		return absentExcuse;
	}
	public void setAbsentExcuse(Boolean absentExcuse) {
		this.absentExcuse = absentExcuse;
	}
	
}
