package ClassAdminBackEnd;

public class BestNMarkEntity extends MarkEntity{

	Integer N;
	public BestNMarkEntity(EntityType type, SuperEntity parentEntity,
			double mark, Integer N) {
		super(type, parentEntity, mark);
		this.N = N;
		// TODO Auto-generated constructor stub
	}
	
	private Double doMarkMath() throws AbsentException{
		double mTotal = 0;
		double wTotal = 0;
		Boolean hasval = false;
		for (int i = 0; i < this.getSubEntity().size(); ++i) {
			try {
				mTotal += this.getSubEntity().get(i).calcMark()
						* this.getSubEntityWeight().get(i);
				wTotal += this.getSubEntityWeight().get(i);
				hasval = true;
			} catch (Exception e) {
			}

		}
		if(!hasval){
			throw new AbsentException();
		}

		if (wTotal != 0)
			return mTotal / wTotal;
		else
			return mTotal;
	}

}
