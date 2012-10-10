package Rule;

import java.util.LinkedList;

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
		System.out.println("ooooooooo");
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
	
	@Override
	public void findEntities(LinkedList<EntityType> list){
		for (int x = 0; x < this.getSubEntityType().size(); ++x) {
			this.getSubEntityType().get(x).findEntities(list);
		}
		
	}
	
	//TreeView.createpanelentitytypeTreeview("name",head);

}
