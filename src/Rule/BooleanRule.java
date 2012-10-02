package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class BooleanRule extends Rule {
	char opperator;
	boolean value;

	public BooleanRule(String n,Project project) {
		super(n,project);
		this.setIsRule(true);
		this.setIsTextField(true);
	}

	public Boolean evaluateBool(SuperEntity sE) {

		return true;
	}

	public char getOpperator() {
		return opperator;
	}

	public void setOpperator(char opperator) {
		this.opperator = opperator;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
	
	
}
