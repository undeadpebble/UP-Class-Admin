package Rule;

import ClassAdminBackEnd.EntityType;

public class BooleanRule extends Rule {
	char opperator;
	boolean value;

	public BooleanRule(String n) {
		super(n);
	}

	public Boolean evaluateBool() {

		return true;
	}
}
