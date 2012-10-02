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

	public char getOpperator() {
		return opperator;
	}

	public void setOpperator(char opperator) {
		this.opperator = opperator;
	}

	public FloatRule getChild1() {
		return child1;
	}

	public void setChild1(FloatRule child1) {
		this.child1 = child1;
	}

	public FloatRule getChild2() {
		return child2;
	}

	public void setChild2(FloatRule child2) {
		this.child2 = child2;
	}

	public EntityType getReferences() {
		return references;
	}

	public void setReferences(EntityType references) {
		this.references = references;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public FloatRule(char opperator, FloatRule child1, FloatRule child2,
			String name, Project project) {
		super(name, project);
		this.opperator = opperator;
		this.child1 = child1;
		this.child2 = child2;
		this.setIsRule(true);
		this.setIsTextField(false);
	}

	public FloatRule(char opperator, Double value, String name, Project project) {
		super(name, project);
		this.opperator = opperator;
		this.value = value;
		this.setIsRule(true);
		this.setIsTextField(false);
	}

	public FloatRule(String n, Project project, EntityType references) {
		super(n, project);
		this.references = references;
	}

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
			value = sE.findEntityOfTypeUpDown(references).getMark();
		}
		return value;
	}

	@Override
	public int getType() {
		return 3;
	}
}
