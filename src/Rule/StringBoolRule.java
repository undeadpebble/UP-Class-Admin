package Rule;

import ClassAdminBackEnd.Project;


public class StringBoolRule extends BooleanRule{
	StringRule child1;
	StringRule child2;
	
	public StringBoolRule(StringRule child1, StringRule child2, char opperator,String name,Project project) {
		super(name, project);
		this.child1 = child1;
		this.child2 = child2;
		this.opperator = opperator;
	}
	
	@Override
	public Boolean evaluateBool(){
		switch (opperator) {
		case '=':
			if(child1.evaluateString().compareTo(child2.evaluateString()) == 0){
				value = true;
			}
			else{
				value = false;
			}
			break;
		case 'c':
			if(child1.evaluateString().contains(child2.evaluateString())){
				value = true;
			}
			else{
				value = false;
			}
			break;
		default:
			value = true;
			break;
		}

		return value;
	}
	
	@Override
	public int getType(){
		return 2;
	}

}
