package ClassAdminBackEnd;

import Rule.FloatRule;
import Rule.StringRule;

public class floatRuleEntity extends MarkEntity{

		public floatRuleEntity(EntityType type, SuperEntity parentEntity) {
			super(type,parentEntity);
		}

		@Override
		public String getValue() {
			return((((FloatRule)this.getType()).evaluateDouble(this)).toString());
		}
		
		@Override
		public double calcMark(){
			this.setMark(((FloatRule)this.getType()).evaluateDouble(this));
			return this.getMark();
		}

}
