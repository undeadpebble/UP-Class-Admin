package ClassAdminBackEnd;

import java.util.Date;

public class AbsentLeafMarkEntity extends LeafMarkEntity{


	public AbsentLeafMarkEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity, 0);
		// TODO Auto-generated constructor stub
	}
	
	public AbsentLeafMarkEntity(LeafMarkEntity replacedEntity) {
		super(replacedEntity.getType(), replacedEntity.getParentEntity(), 0);
		// TODO Auto-generated constructor stub
	}

	private Boolean isAbsent(){
		return true;
	}
}
