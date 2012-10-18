package ClassAdminBackEnd;

import java.util.LinkedList;

import Rule.StringRule;

public class StringRuleEntity extends StringEntity {

	public StringRuleEntity(EntityType type, SuperEntity parentEntity,
			String text) {
		super(type, parentEntity, text);
	}

	@Override
	public String getValue() {
		this.setField(((StringRule)this.getType()).evaluateString(this));
		return(this.getField());
	}
	
	@Override
	public String createTreeFromHead(LinkedList<SuperEntity> studentLinkedList) {
		return "";
	}
}
