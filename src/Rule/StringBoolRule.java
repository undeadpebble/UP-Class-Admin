package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;


public class StringBoolRule extends BooleanRule{
	private StringRule child1;
	private StringRule child2;
	
	/**
	 * @param child1
	 * @param child2
	 * @param opperator
	 * @param name
	 * @param project
	 * creates a string bool rule with the parameters
	 */
	public StringBoolRule(StringRule child1, StringRule child2, char opperator,String name,Project project) {
		super(name, project);
		this.child1 = child1;
		this.child2 = child2;
		this.opperator = opperator;
		
		this.setIsRule(true);
		this.setIsTextField(true);
	}
	
	/**
	 * @return
	 * returns the first child of the rule
	 */
	public StringRule getChild1() {
		return child1;
	}

	/**
	 * @param child1
	 * sets the first child of the rule
	 */
	public void setChild1(StringRule child1) {
		this.child1 = child1;
	}

	/**
	 * @return
	 * returns the first second of the rule
	 */
	public StringRule getChild2() {
		return child2;
	}

	/**
	 * @param child2
	 * sets the second child of the rule
	 */
	public void setChild2(StringRule child2) {
		this.child2 = child2;
	}

	/* (non-Javadoc)
	 * @see Rule.BooleanRule#evaluateBool(ClassAdminBackEnd.SuperEntity)
	 */
	@Override
	public Boolean evaluateBool(SuperEntity sE){
		switch (opperator) {
		case '=':
			if(child1.evaluateString(sE).compareTo(child2.evaluateString(sE)) == 0){
				value = true;
			}
			else{
				value = false;
			}
			break;
		case 'c':
			if(child1.evaluateString(sE).contains(child2.evaluateString(sE))){
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
	
	/* (non-Javadoc)
	 * @see Rule.Rule#getType()
	 */
	@Override
	public int getType(){
		return 2;
	}

}
