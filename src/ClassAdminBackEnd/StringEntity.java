package ClassAdminBackEnd;

public class StringEntity extends SuperEntity{

	public StringEntity(EntityType type, SuperEntity parentEntity, String text) {
		super(type, parentEntity, 0);
		this.getFields().add(text);
		
		// TODO Auto-generated constructor stub
	}
	
	public StringEntity(SuperEntity replacedEntity, String text){
		super(replacedEntity);
		if(this.getFields().size() == 0)
			this.getFields().add(text);
	}
	

	public String getValue(){
		//TODO
			return this.getFields().get(0);
	}
	
	public void setValue(String newValue){
		this.getFields().set(0, newValue);
	}

	
}
