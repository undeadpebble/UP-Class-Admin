package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;

public class BooleanRule extends Rule {
	char opperator;
	boolean value;

	public BooleanRule(String n,Project project) {
		super(n,project);
	}

	public Boolean evaluateBool() {

		return true;
	}
}
