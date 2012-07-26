package ClassAdminBackEnd;

import java.awt.Color;

public class Format {
	private int condition;
	private int priority;
	private Double value1;
	private Double value2;
	private Color textColor;
	private Color highlightColor;
	private String description;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Format [condition=");
		builder.append(condition);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", value1=");
		builder.append(value1);
		builder.append(", value2=");
		builder.append(value2);
		builder.append(", textColor=");
		builder.append(textColor);
		builder.append(", highlightColor=");
		builder.append(highlightColor);
		builder.append(", description=");
		builder.append("\""+description+"\"");
		builder.append("]");
		return builder.toString();
	}
	/**
	 * @param condition
	 * @param priority
	 * @param value1
	 * @param value2
	 * @param textColor
	 * @param highlightColor
	 * @param description
	 */
	public Format(int condition, int priority, Double value1, Double value2,
			Color textColor, Color highlightColor, String description) {
		this.condition = condition;
		this.priority = priority;
		this.value1 = value1;
		this.value2 = value2;
		this.textColor = textColor;
		this.highlightColor = highlightColor;
		this.description = description;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
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
	public Double getValue2() {
		return value2;
	}
	public void setValue2(Double value2) {
		this.value2 = value2;
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

}
