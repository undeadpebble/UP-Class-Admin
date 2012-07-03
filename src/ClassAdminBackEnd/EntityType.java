package ClassAdminBackEnd;

import java.util.Date;
import java.util.LinkedList;

public class EntityType {
	String name;
	LinkedList<String> fields;
	LinkedList<Format> formatting;
	LinkedList<BorderCase> borderCasing;
	LinkedList<MarkEntity> entityList;
	Boolean isTextField;
	Date date;
	Boolean[] visibleFields;
	Boolean isVisible; 
	
	public EntityType(String n){
		name = n;
	}
}
