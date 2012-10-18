package ClassAdminBackEnd;

import java.util.LinkedList;

import Rule.FloatRule;
import Rule.StringRule;

public class markRuleEntity extends MarkEntity{
	

	public markRuleEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity);
		
	}

	@Override
	public void calcMark() {
		this.setMark(((FloatRule)this.getType()).evaluateDouble(this));
		
	}
	@Override
	public String createTreeFromHead(LinkedList<SuperEntity> studentLinkedList) {
		return "";
	}
}
