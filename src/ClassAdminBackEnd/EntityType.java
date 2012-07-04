package ClassAdminBackEnd;

import java.util.Date;
import java.util.LinkedList;

public class EntityType {
	private String name;
	private LinkedList<String> fields;
	private LinkedList<String> fieldDefaults;
	private LinkedList<Format> formatting;
	private LinkedList<BorderCase> borderCasing;
	private LinkedList<MarkEntity> entityList;
	private Boolean isTextField;
	private Date date;
	private Boolean[] visibleFields;
	private Boolean isVisible; 
	private Double defaultWeight;
	
	public EntityType(String n){
		name = n;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<String> getFields() {
		return fields;
	}

	public void setFields(LinkedList<String> fields) {
		this.fields = fields;
	}

	public LinkedList<Format> getFormatting() {
		return formatting;
	}

	public void setFormatting(LinkedList<Format> formatting) {
		this.formatting = formatting;
	}

	public LinkedList<BorderCase> getBorderCasing() {
		return borderCasing;
	}

	public void setBorderCasing(LinkedList<BorderCase> borderCasing) {
		this.borderCasing = borderCasing;
	}

	public LinkedList<MarkEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(LinkedList<MarkEntity> entityList) {
		this.entityList = entityList;
	}

	public Boolean getIsTextField() {
		return isTextField;
	}

	public void setIsTextField(Boolean isTextField) {
		this.isTextField = isTextField;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean[] getVisibleFields() {
		return visibleFields;
	}

	public void setVisibleFields(Boolean[] visibleFields) {
		this.visibleFields = visibleFields;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public LinkedList<String> getFieldDefaults() {
		return fieldDefaults;
	}

	public void setFieldDefaults(LinkedList<String> fieldDefaults) {
		this.fieldDefaults = fieldDefaults;
	}

	public Double getDefaultWeight() {
		return defaultWeight;
	}

	public void setDefaultWeight(Double defaultWeight) {
		this.defaultWeight = defaultWeight;
	}
}
