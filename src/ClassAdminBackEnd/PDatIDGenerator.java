package ClassAdminBackEnd;

public class PDatIDGenerator {
	private Long val = new Long(0);
	public Long getID(){
		return ++val;
	}
}
