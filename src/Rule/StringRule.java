package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;

public class StringRule extends Rule {
	StringRule child1;
	StringRule child2;
	BooleanRule boolRule;
	String value;	
	
	public StringRule(StringRule child1, StringRule child2, BooleanRule boolRule, String name, Project project) {
		super(name,project);
		this.child1 = child1;
		this.child2 = child2;
		this.boolRule = boolRule;
		
		//Global.getGlobal().getActiveProject().getHeadEntityType().getSubEntityType().get(0).getSubEntityType().add(this);
		
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

	@Override
	public String evaluateString() {
		if (boolRule != null) {
			if (boolRule.evaluateBool()) {
				value = child1.evaluateString();
			} else {
				value = child2.evaluateString();
			}
		}
		
		return value;
	}
	
	@Override
	public int getType(){
		return 4;
	}

}
