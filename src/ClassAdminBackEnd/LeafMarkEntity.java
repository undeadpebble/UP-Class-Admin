package ClassAdminBackEnd;

public class LeafMarkEntity extends SuperEntity{


	public LeafMarkEntity(EntityType type, SuperEntity parentEntity, double mark) {
		super(type, parentEntity, mark);
		// TODO Auto-generated constructor stub
	}
	
	public LeafMarkEntity(SuperEntity replacedEntity) {
		super(replacedEntity.getType(), replacedEntity.getParentEntity(), replacedEntity.getMark());
		// TODO Auto-generated constructor stub
	}

	private Double doMarkMath() throws AbsentException{
		return this.getMark();
	}
}
