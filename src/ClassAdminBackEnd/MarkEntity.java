package ClassAdminBackEnd;


public class MarkEntity extends SuperEntity{


	public MarkEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity, 0);
		// TODO Auto-generated constructor stub
	}


	public String getValue(){
		

		return Double.toString(this.getMark());

	}
	
	private Double doMarkMath() throws AbsentException{
		return this.getMark();
	}

	


	
}

