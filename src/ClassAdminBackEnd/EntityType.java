package ClassAdminBackEnd;

import java.util.Date;
import java.util.LinkedList;

public class EntityType {
	private String name;
	private LinkedList<String> fields;
	private Boolean[] visibleFields;
	private LinkedList<String> fieldDefaults;
	private LinkedList<Format> formatting;
	private LinkedList<BorderCase> borderCasing;
	private LinkedList<MarkEntity> entityList;
	private Boolean isTextField;
	private Date date;
	
	private Boolean isVisible; 
	private Double defaultWeight;
	private int index;
	
	public EntityType(String n){
		name = n;		
	}

	/**
	 * @param name
	 * @param fields
	 * @param fieldDefaults
	 * @param formatting
	 * @param borderCasing
	 * @param entityList
	 * @param isTextField
	 * @param date
	 * @param visibleFields
	 * @param isVisible
	 * @param defaultWeight
	 */
	public EntityType(String name, LinkedList<String> fields,Boolean[] visibleFields,
			LinkedList<String> fieldDefaults, LinkedList<Format> formatting,
			LinkedList<BorderCase> borderCasing,
			LinkedList<MarkEntity> entityList, Boolean isTextField, Date date,
			Boolean isVisible, Double defaultWeight) {
		this.name = name;
		this.fields = fields;
		this.fieldDefaults = fieldDefaults;
		this.formatting = formatting;
		this.borderCasing = borderCasing;
		this.entityList = entityList;
		this.isTextField = isTextField;
		this.date = date;
		this.visibleFields = visibleFields;
		this.isVisible = isVisible;
		this.defaultWeight = defaultWeight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<String> getFields() {
		if(fields == null)
			fields = new LinkedList<String>();
		return fields;
	}


	public LinkedList<Format> getFormatting() {
		if(formatting == null)
			formatting = new LinkedList<Format>();
		return formatting;
	}



	public LinkedList<BorderCase> getBorderCasing() {
		if(borderCasing == null)
			borderCasing = new LinkedList<BorderCase>();
		return borderCasing;
	}


	public LinkedList<MarkEntity> getEntityList() {
		if (entityList == null)
			entityList = new LinkedList<MarkEntity>();
		return entityList;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
