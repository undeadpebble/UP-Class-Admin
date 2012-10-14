package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class FloatRule extends Rule {
	private char opperator;
	private FloatRule child1;
	private FloatRule child2;
	private Double value = 0.0;
	private EntityType references = null;

	/**
	 * @return
	 * returns the opperator of the rule
	 */
	public char getOpperator() {
		return opperator;
	}

	/**
	 * @param opperator
	 * sets the opperator of the rule
	 */
	public void setOpperator(char opperator) {
		this.opperator = opperator;
	}

	/**
	 * @return
	 * returns the first child of the rule
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

	/**
	 * @return
	 * returns the entity type that the rule references
	 */
	public EntityType getReferences() {
		return references;
	}

	/**
	 * @param references
	 * sets the references of the rule
	 */
	public void setReferences(EntityType references) {
		this.references = references;
	}

	/**
	 * @return
	 * returns the value of the rule
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value
	 * sets the value of the rule to param value
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @param opperator
	 * @param child1
	 * @param child2
	 * @param name
	 * @param project
	 * creater a new float rule with the parametets
	 */
	public FloatRule(char opperator, FloatRule child1, FloatRule child2,
			String name, Project project) {
		super(name, project);
		this.opperator = opperator;
		this.child1 = child1;
		this.child2 = child2;
		this.setIsRule(true);
		this.setIsTextField(false);
	}

	/**
	 * @param opperator
	 * @param value
	 * @param name
	 * @param project
	 * creater a new float rule with the parametets
	 */
	public FloatRule(char opperator, Double value, String name, Project project) {
		super(name, project);
		this.opperator = opperator;
		this.value = value;
		this.setIsRule(true);
		this.setIsTextField(false);
	}

	/**
	 * @param n
	 * @param project
	 * @param references
	 * creater a new float rule with the parametets
	 */
	public FloatRule(String n, Project project, EntityType references) {
		super(n, project);
		this.references = references;
	}

	/* (non-Javadoc)
	 * @see Rule.Rule#evaluateDouble(ClassAdminBackEnd.SuperEntity)
	 */
	@Override
	public Double evaluateDouble(SuperEntity sE) {
		switch (opperator) {
		case '+':
			value = child1.evaluateDouble(sE) + child2.evaluateDouble(sE);
			break;
		case '-':
			value = child1.evaluateDouble(sE) - child2.evaluateDouble(sE);
			break;
		case '*':
			value = child1.evaluateDouble(sE) * child2.evaluateDouble(sE);
			break;
		case '/':
			value = child1.evaluateDouble(sE) / child2.evaluateDouble(sE);
			break;
		default:
			break;
		}

		if(references != null){
			try{
				value = sE.findEntityOfTypeUpDown(references).getMark();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see Rule.Rule#getType()
	 */
	@Override
	public int getType() {
		return 3;
	}
}
