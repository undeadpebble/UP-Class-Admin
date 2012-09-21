package ClassAdminBackEnd;

import Rule.StringRule;

public class StringRuleEntity extends StringEntity {

	public StringRuleEntity(EntityType type, SuperEntity parentEntity,
			String text) {
		super(type, parentEntity, text);
	}

	@Override
	public String getValue() {
		this.setField(((StringRule)this.getType()).evaluateString());
		return(this.getField());
	}
}
