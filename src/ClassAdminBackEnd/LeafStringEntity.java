package ClassAdminBackEnd;

public class LeafStringEntity extends StringEntity{
	/**
	 * @param type
	 * @param parentEntity
	 * @param text
	 */
	public LeafStringEntity(EntityType type, SuperEntity parentEntity,
			String text) {
		super(type, parentEntity, text);
	}

	/**
	 * @param replacedEntity
	 * @param text
	 */
	public LeafStringEntity(SuperEntity replacedEntity, String text) {
		super(replacedEntity, text);
		if(this.getSubEntity().size() != 0){
			this.unLeaf();
		}
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#isAbsent()
	 */
	public Boolean isAbsent(){
		return true;
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#unLeaf()
	 */
	public SuperEntity unLeaf(){
		return new StringEntity(this, this.getField());
	}
	/**
	 * @return
	 */
	public String createTreeFromHead()
	{
		String str = "";
		str += "<leaf>" +
				"<attribute name = \"name\" value= \"" + this.getType().getName() + ": " + this.getValue() + "\" />";
		str +="</leaf>";
		return str;
	}

}
