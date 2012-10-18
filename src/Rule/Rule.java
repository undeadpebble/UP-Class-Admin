package Rule;

import java.util.LinkedList;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.TreeView;

public class Rule extends EntityType{
	
	protected String name;
	private Project project;
	
	/**
	 * @param n
	 * @param project
	 * creates a rule with the parameters
	 */
	public Rule(String n, Project project) {
		super(n);
		this.name = n;
		this.project = project;
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.EntityType#getName()
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @param sE
	 * @return
	 * evalueates a bool rule
	 */
	public Boolean evaluateBool(SuperEntity sE){
		return false;
	}
	
	/**
	 * @param sE
	 * @return
	 * evaluates a double rule
	 */
	public Double evaluateDouble(SuperEntity sE){
		return 0.0;
	}
	
	/**
	 * @param sE
	 * @return
	 * evaluates a string rule
	 */
	public String evaluateString(SuperEntity sE){
		return "";
	}
	
	/**
	 * @return
	 * returns the type of the rule
	 */
	public int getType(){
		return 0;
	}

	
	@Override
	public void findEntities(LinkedList<EntityType> list){
		for (int x = 0; x < this.getSubEntityType().size(); ++x) {
			this.getSubEntityType().get(x).findEntities(list);
		}
		
	}
	
	@Override
	public String createTreeFromHead(LinkedList<EntityType> studentLinkedList) {
		return "";
	}


}
