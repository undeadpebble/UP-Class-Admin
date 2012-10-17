package ClassAdminBackEnd;

import java.util.LinkedList;

public class LeafMarkEntity extends MarkEntity{


	/**
	 * @param type
	 * @param parentEntity
	 * @param mark
	 */
	public LeafMarkEntity(EntityType type, SuperEntity parentEntity, double mark) {
		super(type, parentEntity);
	}
	
	/**
	 * @param replacedEntity
	 * @param mark
	 */
	public LeafMarkEntity(SuperEntity replacedEntity, double mark) {
		super(replacedEntity);
		this.setMark(mark);
		if(this.getSubEntity().size() != 0){
			this.unLeaf();
		}
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#doMarkMath()
	 */
	public Double doMarkMath() throws AbsentException{
		return this.getMark();
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#unLeaf()
	 */
	public SuperEntity unLeaf(){
		return new MarkEntity(this);
	}
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#createTreeFromHead(java.util.LinkedList)
	 */
	public String createTreeFromHead(LinkedList<SuperEntity> studentLinkedList)
	{
		String s;
		if(this.getClass().toString().contains("Absent"))
		{
			s = "*ABSENT";
		}
		else
		{
			s = this.getValue();
		}
		studentLinkedList.add((SuperEntity) this);
		String str = "";
		str += "<leaf>" +
				"<attribute name = \"name\" value= \"" + this.getType().getName() + ": " + s + "\" />";
		str +="</leaf>";
		return str;
	}

}
