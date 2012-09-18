package ClassAdminBackEnd;

import Rule.StringRule;

public class StringRuleEntity extends StringEntity {

	public StringRuleEntity(StringRule type, SuperEntity parentEntity,
			String text) {
		super(type, parentEntity, text);
	}

	@Override
	public String getValue() {
		return(((StringRule)this.getType()).evaluateString());
	}
}
