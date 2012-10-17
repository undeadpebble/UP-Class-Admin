package ClassAdminBackEnd;

public class RapidAssessmentRectangleType extends RapidAssessmentComponentType {

	/**
	 * @param n
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param parent
	 */
	public RapidAssessmentRectangleType(String n, double x, double y, double w,
			double h, EntityType parent) {
		super(n, x, y, w, h,parent);
		this.setMaxValue(0);
	}

}
