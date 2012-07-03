package ClassAdminBackEnd;

public class BorderCase {
	Double lowVal;
	Double highVal;
	
	/**
	 * Returns if the MarkEntity is a borderCase or not
	 * @param markEntity
	 * @return boolean
	 */
	public boolean isBorderCase(MarkEntity markE){
		if((markE.getMark() < highVal) && (markE.getMark() > lowVal)){
			return true;
		}
		
		return false;
	}
}
