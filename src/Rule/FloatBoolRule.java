package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class FloatBoolRule extends BooleanRule {
	private FloatRule child1;
	private FloatRule child2;

	/**
	 * @param opperator
	 * @param child1
	 * @param child2
	 * @param name
	 * @param project
	 * constructor to create a new float bool rule
	 */
	public FloatBoolRule(char opperator, FloatRule child1, FloatRule child2,
			String name, Project project) {
		super(name, project);
		this.child1 = child1;
		this.child2 = child2;
		this.opperator = opperator;
		this.setIsRule(true);
		this.setIsTextField(true);
	}
	
	

	/**
	 * @return
	 * returns the first child of the float bool rule
	 */
	public FloatRule getChild1() {
		return child1;
	}



	/**
	 * @param child1
	 * sets the first child of the rule
	 */
	public void setChild1(FloatRule child1) {
		this.child1 = child1;
	}



	/**
	 * @return
	 * returns the second child of the rule
	 */
	public FloatRule getChild2() {
		return child2;
	}



	/**
	 * @param child2
	 * sets the second child of the rule
	 */
	public void setChild2(FloatRule child2) {
		this.child2 = child2;
	}



	/* (non-Javadoc)
	 * @see Rule.BooleanRule#evaluateBool(ClassAdminBackEnd.SuperEntity)
	 */
	@Override
	public Boolean evaluateBool(SuperEntity sE) {
		switch (opperator) {
		case '<':
			if (child1.evaluateDouble(sE) < child2.evaluateDouble(sE)) {
				value = true;
			} else {
				value = false;
			}
			break;
		case '>':
			if (child1.evaluateDouble(sE) > child2.evaluateDouble(sE)) {
				value = true;
			} else {
				value = false;
			}
			break;
		case '=':
			if (child1.evaluateDouble(sE) == child2.evaluateDouble(sE)) {
				value = true;
			} else {
				value = false;
			}
			break;
		case '1':
			if (child1.evaluateDouble(sE) <= child2.evaluateDouble(sE)) {
				value = true;
			} else {
				value = false;
			}
			break;
		case '2':
			if (child1.evaluateDouble(sE) >= child2.evaluateDouble(sE)) {
				value = true;
			} else {
				value = false;
			}
			break;
		default:
			value = true;
			break;
		}

		return value;
	}

	/* (non-Javadoc)
	 * @see Rule.Rule#getType()
	 */
	@Override
	public int getType() {
		return 1;
	}

}
