package ClassAdminBackEnd;

import java.util.LinkedList;

public class Rule {
	String name;
	String type;
	int references;
	int val1;
	int val2;
	String str;
	LinkedList<Rule> subRules = new LinkedList<Rule>();
	
	public Rule(String name, String type, int references, int val1, int val2, String str) {
		super();
		this.name = name;
		this.type = type;
		this.references = references;
		this.val1 = val1;
		this.val2 = val2;
		this.str = str;
	}
	
	public String getName(){
		return name;
	}
	
	public LinkedList<Rule> getSubRule(){
		return subRules;
	}
}
