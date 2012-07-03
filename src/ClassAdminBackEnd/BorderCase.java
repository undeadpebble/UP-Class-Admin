package ClassAdminBackEnd;

public class BorderCase {
	private Double lowVal;
	private Double highVal;
	
	public Double getLowVal() {
		return lowVal;
	}

	public void setLowVal(Double lowVal) {
		this.lowVal = lowVal;
	}

	public Double getHighVal() {
		return highVal;
	}

	public void setHighVal(Double highVal) {
		this.highVal = highVal;
	}
	
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
