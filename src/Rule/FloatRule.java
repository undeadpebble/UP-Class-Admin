package Rule;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.SuperEntity;

public class FloatRule extends Rule {
	private char opperator;
	private FloatRule child1;
	private FloatRule child2;
	private Double value;

	public FloatRule(char opperator, FloatRule child1, FloatRule child2,
			String name) {
		super(name);
		this.opperator = opperator;
		this.child1 = child1;
		this.child2 = child2;
	}

	public FloatRule(char opperator, Double value, String name) {
		super(name);
		this.opperator = opperator;
		this.value = value;
	}

	 @Override
	public Double evaluateDouble() {
		switch (opperator) {
		case '+':
			value = child1.evaluateDouble() + child2.evaluateDouble();
			break;
		case '-':
			value = child1.evaluateDouble() - child2.evaluateDouble();
			break;
		case '*':
			value = child1.evaluateDouble() * child2.evaluateDouble();
			break;
		case '/':
			value = child1.evaluateDouble() / child2.evaluateDouble();
			break;
		default:
			break;
		}

		return value;
	}
	 
		@Override
		public int getType(){
			return 3;
		}
}
