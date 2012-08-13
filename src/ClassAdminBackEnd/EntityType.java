package ClassAdminBackEnd;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class EntityType {
	private String name;
	private String field;
	private Boolean[] visibleFields;
	private String fieldDefault;
	private LinkedList<Format> formatting;
	private LinkedList<BorderCase> borderCasing;
	private LinkedList<SuperEntity> entityList;
	private Boolean isTextField;
	private Date date;
	
	private Boolean isVisible; 
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */

	private Double defaultWeight;
	private int index;
	
	public EntityType(String n){
		name = n;		
	}

	/**
	 * @param name
	 * @param fields
	 * @param visibleFields
	 * @param fieldDefaults
	 * @param formatting
	 * @param borderCasing
	 * @param entityList
	 * @param isTextField
	 * @param date
	 * @param isVisible
	 * @param defaultWeight
	 */
	public EntityType(String name, String field,Boolean[] visibleFields,
			String fieldDefault, LinkedList<Format> formatting,
			LinkedList<BorderCase> borderCasing,
			LinkedList<SuperEntity> entityList, Boolean isTextField, Date date,
			Boolean isVisible, Double defaultWeight) {
		this.name = name;
		this.field = field;
		this.fieldDefault = fieldDefault;
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

	public String getField() {

		return field;
	}
	
	public void setField(String field){
		this.field = field;
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


	public LinkedList<SuperEntity> getEntityList() {
		if (entityList == null)
			entityList = new LinkedList<SuperEntity>();
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

	public String getFieldDefault() {
		return fieldDefault;
	}
	
	public void setFieldDefault(String fieldDefault){
		this.fieldDefault = fieldDefault;
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
