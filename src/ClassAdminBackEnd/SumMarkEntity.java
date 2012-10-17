package ClassAdminBackEnd;

public class SumMarkEntity extends MarkEntity {
	/**
	 * @param type
	 * @param parentEntity
	 */
	public SumMarkEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param replacedEntity
	 */
	public SumMarkEntity(SuperEntity replacedEntity) {
		super(replacedEntity);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#doMarkMath()
	 */
	public Double doMarkMath() throws AbsentException {
		double mTotal = 0;
		double mmax = 0;
		Boolean hasval = false;
		for (int i = 0; i < this.getSubEntity().size(); ++i) {
			try {
				mTotal += this.getSubEntity().get(i).getMark();

				hasval = true;
			} catch (Exception e) {
			}

		}

		if (!hasval) {
			throw new AbsentException();
		}

			return mTotal;
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#updateMark()
	 */
	public void updateMark() {
		super.updateMark();
			this.getType().updateMaxValue();
		
	}
}
