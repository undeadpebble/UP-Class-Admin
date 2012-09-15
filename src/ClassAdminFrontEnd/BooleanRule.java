package ClassAdminFrontEnd;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Rule;

public class BooleanRule extends Rule{
	child child1;
	child child2;
	char op;
	
	public class child{
		BooleanRule boolRule;
		EntityType entType;		
	}
}
