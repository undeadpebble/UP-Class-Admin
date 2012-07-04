package ClassAdminBackEnd;

import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;

public class EntityDetails {
	private EntityType type;
	private LinkedList<String> fields;
	private Image Picture;
	private Color highlightColor;
	private Color textColor;
	private Boolean absentExcuse;
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
	public Image getPicture() {
		return Picture;
	}
	public void setPicture(Image picture) {
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
