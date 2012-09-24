package ClassAdminBackEnd;

import Rule.FloatRule;
import Rule.StringRule;

public class markRuleEntity extends MarkEntity{
	

	public markRuleEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity);
		
	}

	@Override
	public double calcMark() {
		this.setMark(((FloatRule)this.getType()).evaluateDouble(this));
		
		return this.getMark();
	}
}
