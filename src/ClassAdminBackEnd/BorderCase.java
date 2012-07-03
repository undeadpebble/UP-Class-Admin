package ClassAdminBackEnd;

public class BorderCase {
	Double lowVal;
	Double highVal;
	
	/**
	 * @param markE
	 * @return
	 */
	public boolean isBorderCase(MarkEntity markE){
		if((markE.getMark() < highVal) && (markE.getMark() > lowVal)){
			return true;
		}
		
		return false;
	}
}
