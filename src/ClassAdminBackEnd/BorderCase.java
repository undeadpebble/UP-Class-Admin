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
		if((markE.mark < highVal) && (markE.mark > lowVal)){
			return true;
		}
		
		return false;
	}
}
