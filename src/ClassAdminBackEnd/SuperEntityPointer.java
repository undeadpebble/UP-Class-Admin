package ClassAdminBackEnd;

public class SuperEntityPointer {
	/**
	 * @param target
	 */
	public SuperEntityPointer(SuperEntity target) {
		this.target = target;
		target.setThisPointer(this);
	}

	private SuperEntity target;

	/**
	 * @return
	 */
	public SuperEntity getTarget() {
		return target;
	}

	/**
	 * @param target
	 */
	public void setTarget(SuperEntity target) {
		if(this.target != null){
			this.target.setThisPointer(null);
		}
		this.target = target;
		target.setThisPointer(this);
		
	}
}
