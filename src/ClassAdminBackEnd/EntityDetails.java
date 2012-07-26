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
		this.type = Global.getGlobal().getActiveProject().getEntityTypes().get(type);
		this.fields = null;
		this.highlightColor = null;
		this.textColor = null;
		this.absentExcuse = null;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityDetails [type=");
		builder.append(type.getIndex());
		builder.append(", fields=");
		builder.append(pdatImport.linkedListToString(fields));
		builder.append(", Picture=");
		builder.append("\""+Picture+"\"");
		builder.append(", absentExcuse=");
		builder.append(absentExcuse);
		builder.append("]");
		return builder.toString();
	}
	public EntityType getType() {
		return type;
	}
	public void setType(EntityType type) {
		this.type = type;
	}
	public LinkedList<String> getFields() {
		if(fields == null)
			fields = new LinkedList<String>();
		return fields;
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
