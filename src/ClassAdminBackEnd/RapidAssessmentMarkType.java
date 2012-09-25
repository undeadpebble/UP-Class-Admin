package ClassAdminBackEnd;

public class RapidAssessmentMarkType extends RapidAssessmentComponentType {

	/**
	 * @param n
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public RapidAssessmentMarkType(String n, double x, double y, double w,
			double h, double maxmark) {
		super(n, x, y, w, h);
		this.setMaxValue(maxmark);
		// TODO Auto-generated constructor stub
	}

}
