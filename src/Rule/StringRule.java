package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class StringRule extends Rule {
	StringRule child1;
	StringRule child2;
	BooleanRule boolRule;
	String value;	
	private EntityType references = null;
	
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
