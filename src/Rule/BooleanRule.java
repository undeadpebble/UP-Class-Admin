package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class BooleanRule extends Rule {
	char opperator;
	boolean value;

	/**
	 * @param n
	 * @param project
	 * super for creating a new boolean type rule
	 */
	public BooleanRule(String n,Project project) {
		super(n,project);
		this.setIsRule(true);
		this.setIsTextField(true);
	}

	/* (non-Javadoc)
	 * @see Rule.Rule#evaluateBool(ClassAdminBackEnd.SuperEntity)
	 */
	public Boolean evaluateBool(SuperEntity sE) {

		return true;
	}

	/**
	 * @return
	 * returns the opperator of the boolean rule
	 */
	public char getOpperator() {
		return opperator;
	}

	/**
	 * @param opperator
	 * sets the opperator of the boolean rule
	 */
	public void setOpperator(char opperator) {
		this.opperator = opperator;
	}

	/**
	 * @return
	 * returns the value of the rule
	 */
	public boolean isValue() {
		return value;
	}

	/**
	 * @param value
	 * sets the value of the rule to param value
	 */
	public void setValue(boolean value) {
		this.value = value;
	}
	
	
}
