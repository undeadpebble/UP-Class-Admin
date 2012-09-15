package ClassAdminFrontEnd;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Rule;

public class FloatRule extends Rule{
	char opperator;
	child child1;
	child child2;
	float value;
	
	public class child{
		FloatRule floatRule;
		EntityType entType;		
	}

}
