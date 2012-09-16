package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.TreeView;

public class Rule extends EntityType{
	
	protected String name;
	
	public Rule(String n) {
		super(n);
		this.name = n;
	}

	public String getName(){
		return name;
	}
	
	public Boolean evaluateBool(){
		return false;
	}
	
	public Double evaluateDouble(){
		return 0.0;
	}
	
	public String evaluateString(){
		return "";
	}
	
	public int getType(){
		return 0;
	}
	
	//TreeView.createpanelentitytypeTreeview("name",head);

}
