package ClassAdminBackEnd;

import java.awt.Color;

public class Format {
	private int priority;
	private Double value1;
	private Color textColor;
	private Color highlightColor;
	private String description;
	
	
	/**
	 * @param condition
	 * @param priority
	 * @param value1
	 * @param value2
	 * @param textColor
	 * @param highlightColor
	 * @param description
	 */
	public Format(int priority, Double value1, 
			Color textColor, Color highlightColor, String description) {
		this.priority = priority;
		this.value1 = value1;
		this.textColor = textColor;
		this.highlightColor = highlightColor;
		this.description = description;
	}
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Double getValue1() {
		return value1;
	}
	public void setValue1(Double value1) {
		this.value1 = value1;
	}
	public Color getTextColor() {
		return textColor;
	}
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	public Color getHighlightColor() {
		return highlightColor;
	}
	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean evaluate(double mark){
		return false;
	}

}
