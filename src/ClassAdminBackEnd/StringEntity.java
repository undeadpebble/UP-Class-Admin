package ClassAdminBackEnd;

public class StringEntity extends SuperEntity{
	public StringEntity(EntityType type, SuperEntity parentEntity, String text) {
		super(type, parentEntity, 0);
		this.getFields().add(text);
		
		// TODO Auto-generated constructor stub
	}

	public String getValue(){
		//TODO
		return "";
	}
	
	public void setValue(String newValue){
		if(this.getFields().size()==0){
			
		}
		this.getFields().set(0, newValue);

	}
	
}
