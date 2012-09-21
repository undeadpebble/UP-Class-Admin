package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;

public class BooleanRule extends Rule {
	char opperator;
	boolean value;

	public BooleanRule(String n,Project project) {
		super(n,project);
		this.setIsRule(true);
		this.setIsTextField(true);
	}

	public Boolean evaluateBool() {

		return true;
	}
}
