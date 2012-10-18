package ClassAdminBackEnd;

/**
 * @author undeadpebble
 *Used to save the head entity of the project
 */
public class HeadEntity extends SuperEntity{

	

	/**
	 * @param type
	 * @param mark
	 */
	public HeadEntity(EntityType type, double mark) {
		super(type, 0);
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#getValue()
	 */
	public String getValue(){
		return "";
	}
	
}
