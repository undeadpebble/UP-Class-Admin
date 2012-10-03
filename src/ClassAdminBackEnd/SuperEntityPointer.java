package ClassAdminBackEnd;

public class SuperEntityPointer {
	public SuperEntityPointer(SuperEntity target) {
		this.target = target;
		target.setThisPointer(this);
	}

	private SuperEntity target;

	public SuperEntity getTarget() {
		return target;
	}

	public void setTarget(SuperEntity target) {
		if(this.target != null){
			this.target.setThisPointer(null);
		}
		this.target = target;
		target.setThisPointer(this);
		
	}
}
