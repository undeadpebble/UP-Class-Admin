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
	
	public StringRule getChild1() {
		return child1;
	}

	public void setChild1(StringRule child1) {
		this.child1 = child1;
	}

	public StringRule getChild2() {
		return child2;
	}

	public void setChild2(StringRule child2) {
		this.child2 = child2;
	}

	public BooleanRule getBoolRule() {
		return boolRule;
	}

	public void setBoolRule(BooleanRule boolRule) {
		this.boolRule = boolRule;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EntityType getReferences() {
		return references;
	}

	public void setReferences(EntityType references) {
		this.references = references;
	}

	public StringRule(StringRule child1, StringRule child2, BooleanRule boolRule, String name, Project project) {
		super(name,project);
		this.child1 = child1;
		this.child2 = child2;
		this.boolRule = boolRule;
		
		this.setIsRule(true);
		this.setIsTextField(true);
	}
	
	public StringRule(String value, String name, Project project){
		super(name, project);
		this.value = value;
		boolRule = null;
		
		this.setIsRule(true);
		this.setIsTextField(true);
	}
	
	public StringRule(String n, Project project, EntityType references) {
		super(n, project);
		this.references = references;
	}

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
	
	@Override
	public int getType(){
		return 4;
	}

}
