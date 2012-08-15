package ClassAdminBackEnd;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class EntityType {
	private String name;
	private LinkedList<Format> formatting;
	private LinkedList<BorderCase> borderCasing;
	private LinkedList<SuperEntity> entityList;
	private Boolean isTextField;
	private Date date;	 
	private Double defaultWeight;
	
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
	public EntityType(String name, LinkedList<Format> formatting,
			LinkedList<BorderCase> borderCasing,
			LinkedList<SuperEntity> entityList, Boolean isTextField, Date date, Double defaultWeight) {
		this.name = name;
		this.formatting = formatting;
		this.borderCasing = borderCasing;
		this.entityList = entityList;
		this.isTextField = isTextField;
		this.date = date;
		this.defaultWeight = defaultWeight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Double getDefaultWeight() {
		return defaultWeight;
	}

	public void setDefaultWeight(Double defaultWeight) {
		this.defaultWeight = defaultWeight;
	}

}
