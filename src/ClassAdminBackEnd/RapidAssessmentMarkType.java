package ClassAdminBackEnd;

public class RapidAssessmentMarkType extends RapidAssessmentComponentType {


	
	/**
	 * @param n
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param parent
	 */
	public RapidAssessmentMarkType(String n, double x, double y, double w,
			double h, double maxmark , EntityType parent) {
		super(n, x, y, w, h, parent);
		this.setMaxValue(maxmark);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void populateTreeWithEntities() {
		for (int x = 0; x < this.getParentEntitytype().getEntityList().size(); ++x) {
			SuperEntity parent = this.getParentEntitytype().getEntityList()
					.get(x);
			new LeafMarkEntity(this, parent, 0);

		}
	}

}
