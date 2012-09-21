package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class FloatRule extends Rule {
	private char opperator;
	private FloatRule child1;
	private FloatRule child2;
	private Double value;
	private EntityType references = null;

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
			value = sE.findEntityOfType_Up(references).getMark();
		}
		return value;
	}

	@Override
	public int getType() {
		return 3;
	}
}
