package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.TreeView;

public class Rule extends EntityType{
	
	protected String name;
	private Project project;
	
	public Rule(String n, Project project) {
		super(n);
		this.name = n;
		this.project = project;
	}

	public String getName(){
		return name;
	}
	
	public Boolean evaluateBool(SuperEntity sE){
		return false;
	}
	
	public Double evaluateDouble(SuperEntity sE){
		return 0.0;
	}
	
	public String evaluateString(SuperEntity sE){
		return "";
	}
	
	public int getType(){
		return 0;
	}
	
	//TreeView.createpanelentitytypeTreeview("name",head);

}
