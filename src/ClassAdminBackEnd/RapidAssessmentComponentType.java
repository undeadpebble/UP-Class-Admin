package ClassAdminBackEnd;

public class RapidAssessmentComponentType extends EntityType{
	private double x;
	private double y;
	private double w;
	private double h;
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @param n
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public RapidAssessmentComponentType(String n, double x, double y, double w,
			double h, EntityType parent) {
		super(n,parent,false,null,1.0);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return the w
	 */
	public double getW() {
		return w;
	}
	/**
	 * @param w the w to set
	 */
	public void setW(double w) {
		this.w = w;
	}
	/**
	 * @return the h
	 */
	public double getH() {
		return h;
	}
	/**
	 * @param h the h to set
	 */
	public void setH(double h) {
		this.h = h;
	}
	
	@Override
	public void populateTreeWithEntities() {
		for (int x = 0; x < this.getParentEntitytype().getEntityList().size(); ++x) {
			SuperEntity parent = this.getParentEntitytype().getEntityList()
					.get(x);
			new SumMarkEntity(this, parent);

		}
	}
}
