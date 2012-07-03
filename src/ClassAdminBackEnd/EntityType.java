package ClassAdminBackEnd;

import java.util.Date;
import java.util.LinkedList;

public class EntityType {
	LinkedList<String> fields;
	String name;
	LinkedList<Format> formatting;
	LinkedList<BorderCase> borderCasing;
	LinkedList<MarkEntity> entityList;
	Boolean isTextField;
	Date date;
	Boolean[] visibleFields;
	Boolean isVisible; 
}
