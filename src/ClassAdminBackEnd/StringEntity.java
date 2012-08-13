package ClassAdminBackEnd;

public class StringEntity extends SuperEntity{

	public StringEntity(EntityType type, SuperEntity parentEntity, String text) {
		super(type, parentEntity, 0);
		this.setField(text);
		
		// TODO Auto-generated constructor stub
	}
	
	public StringEntity(SuperEntity replacedEntity, String text){
		super(replacedEntity);
		this.setField(text);
	}
	

	public String getValue(){
		//TODO
			return this.getField();
	}
	
	public void setValue(String newValue){
		this.setField(newValue);
	}

	
}
