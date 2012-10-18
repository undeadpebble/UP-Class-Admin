package ClassAdminBackEnd;

public class PDatIDGenerator {
	private Long val = new Long(0);
	/**
	 * @return ID
	 */
	public Long getID(){
		return ++val;
	}
}
