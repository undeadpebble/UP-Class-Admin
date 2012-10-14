package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class StringRule extends Rule {
	private StringRule child1;
	private StringRule child2;
	private BooleanRule boolRule;
	private String value;	
	private EntityType references = null;
	
	/**
	 * @return
	 * returns the first child of the rule
	 */
	public StringRule getChild1() {
		return child1;
	}

	/**
	 * @param child1
	 * sets the first child of the rule
	 */
	public void setChild1(StringRule child1) {
		this.child1 = child1;
	}

	/**
	 * @return
	 * returns the second child of the rule
	 */
	public StringRule getChild2() {
		return child2;
	}

	/**
	 * @param child2
	 * sets the second child of the rule
	 */
	public void setChild2(StringRule child2) {
		this.child2 = child2;
	}

	/**
	 * @return
	 * returns the boolean rule of the rule
	 */
	public BooleanRule getBoolRule() {
		return boolRule;
	}

	/**
	 * @param boolRule
	 * sets the boolean rule of the rule
	 */
	public void setBoolRule(BooleanRule boolRule) {
		this.boolRule = boolRule;
	}

	/**
	 * @return
	 * returns the value of the rule
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 * sets the value of the rule
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return
	 * returns the references of the rule
	 */
	public EntityType getReferences() {
		return references;
	}

	/**
	 * @param references
	 * sets the references of the rule
	 */
	public void setReferences(EntityType references) {
		this.references = references;
	}

	/**
	 * @param child1
	 * @param child2
	 * @param boolRule
	 * @param name
	 * @param project
	 * creates a string rule with the gives parameters
	 */
	public StringRule(StringRule child1, StringRule child2, BooleanRule boolRule, String name, Project project) {
		super(name,project);
		this.child1 = child1;
		this.child2 = child2;
		this.boolRule = boolRule;
		
		this.setIsRule(true);
		this.setIsTextField(true);
	}
	
	/**
	 * @param value
	 * @param name
	 * @param project
	 * creates a string rule with the gives parameters
	 */
	public StringRule(String value, String name, Project project){
		super(name, project);
		this.value = value;
		boolRule = null;
		
		this.setIsRule(true);
		this.setIsTextField(true);
	}
	
	/**
	 * @param n
	 * @param project
	 * @param references
	 * creates a string rule with the gives parameters
	 */
	public StringRule(String n, Project project, EntityType references) {
		super(n, project);
		this.references = references;
	}

	/* (non-Javadoc)
	 * @see Rule.Rule#evaluateString(ClassAdminBackEnd.SuperEntity)
	 */
	@Override
	public String evaluateString(SuperEntity sE) {
		if (boolRule != null) {
			if (boolRule.evaluateBool(sE)) {
				value = child1.evaluateString(sE);
			} else {
				value = child2.evaluateString(sE);
			}
		}
		
		if(references != null){
			value = sE.findEntityOfTypeUpDown(references).getValue();
		}
		
		return value;
	}
	
	/* (non-Javadoc)
	 * @see Rule.Rule#getType()
	 */
	@Override
	public int getType(){
		return 4;
	}

}
