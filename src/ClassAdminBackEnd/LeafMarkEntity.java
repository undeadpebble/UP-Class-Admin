package ClassAdminBackEnd;

public class LeafMarkEntity extends MarkEntity{


	public LeafMarkEntity(EntityType type, SuperEntity parentEntity, double mark) {
		super(type, parentEntity);
		// TODO Auto-generated constructor stub
	}
	
	public LeafMarkEntity(SuperEntity replacedEntity, double mark) {
		super(replacedEntity);
		this.setMark(mark);
		if(this.getSubEntity().size() != 0){
			this.unLeaf();
		}
		// TODO Auto-generated constructor stub
	}

	private Double doMarkMath() throws AbsentException{
		return this.getMark();
	}
	
	public SuperEntity unLeaf(){
		return new MarkEntity(this);
	}
	public String createTreeFromHead()
	{
		String str = "";
		str += "<leaf>" +
				"<attribute name = \"name\" value= \"" + this.getValue() + "\" />";
		str +="</leaf>";
		return str;
	}

}
