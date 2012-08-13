package ClassAdminBackEnd;

public class LeafStringEntity extends StringEntity{
	public LeafStringEntity(EntityType type, SuperEntity parentEntity,
			String text) {
		super(type, parentEntity, text);
		// TODO Auto-generated constructor stub
	}

	public LeafStringEntity(SuperEntity replacedEntity, String text) {
		super(replacedEntity, text);
	}

	private Boolean isAbsent(){
		return true;
	}
	
	public SuperEntity unLeaf(){
		return new StringEntity(this, this.getFields().get(0));
	}
}
