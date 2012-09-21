package Rule;

import ClassAdminBackEnd.Project;


public class FloatBoolRule extends BooleanRule{
	FloatRule child1;
	FloatRule child2;
	
	public FloatBoolRule(char opperator, FloatRule child1, FloatRule child2,  String name, Project project) {
		super(name, project);
		this.child1 = child1;
		this.child2 = child2;
		this.opperator = opperator;
		this.setIsRule(true);
		this.setIsTextField(true);
	}
	
	@Override
	public Boolean evaluateBool(){
		switch (opperator) {
		case '<':
			if(child1.evaluateDouble() < child2.evaluateDouble()){
				value = true;
			}
			else{
				value = false;
			}
			break;
		case '>':
			if(child1.evaluateDouble() > child2.evaluateDouble()){
				value = true;
			}
			else{
				value = false;
			}
			break;
		case '=':
			if(child1.evaluateDouble() == child2.evaluateDouble()){
				value = true;
			}
			else{
				value = false;
			}
			break;
		case '1':
			if(child1.evaluateDouble() <= child2.evaluateDouble()){
				value = true;
			}
			else{
				value = false;
			}
			break;
		case '2':
			if(child1.evaluateDouble() >= child2.evaluateDouble()){
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
		return 1;
	}
	
}
