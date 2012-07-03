package ClassAdminBackEnd;

public class BorderCase {
	Double lowVal;
	Double highVal;
	
	/**
	 * @param markE
	 * @return
	 */
	public boolean isBorderCase(MarkEntity markE){
		if((markE.mark < highVal) && (markE.mark > lowVal)){
			return true;
		}
		
		return false;
	}
}
