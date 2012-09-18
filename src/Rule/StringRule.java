package Rule;

import ClassAdminBackEnd.EntityType;

public class StringRule extends Rule {
	StringRule child1;
	StringRule child2;
	BooleanRule boolRule;
	String value;	
	
	public StringRule(StringRule child1, StringRule child2, BooleanRule boolRule, String name) {
		super(name);
		this.child1 = child1;
		this.child2 = child2;
		this.boolRule = boolRule;
	}
	
	public StringRule(String value, String name){
		super(name);
		this.value = value;
		boolRule = null;
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
