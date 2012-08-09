package ClassAdminBackEnd;

public class MarkEntity extends SuperEntity{


	public MarkEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity, 0);
		// TODO Auto-generated constructor stub
	}

	public String getValue(){
		//TODO
		return Double.toString(this.getMark());
	}
	
	public void setValue(String newValue){
		this.getFields().set(0, newValue);
	}
	private Double doMarkMath() throws AbsentException{
		return this.getMark();
	}
}
	