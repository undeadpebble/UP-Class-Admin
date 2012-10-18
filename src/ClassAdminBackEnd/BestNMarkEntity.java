package ClassAdminBackEnd;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class BestNMarkEntity extends MarkEntity{

	/**
	 * @param type
	 * @param parentEntity
	 * creates new BestNEntity
	 */
	public BestNMarkEntity(EntityType type, SuperEntity parentEntity) {
		super(type, parentEntity);

	}
	
	/**
	 * @param replacedEntity
	 * create a new BestNEntity, replacing the replacedEntity
	 */
	public BestNMarkEntity(SuperEntity replacedEntity) {
		super(replacedEntity);

	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#doMarkMath()
	 * Calculate the mark using the Best N strategy
	 */
	@Override
	public Double doMarkMath() throws AbsentException{
		double mTotal = 0;
		double wTotal = 0;
		double nthLargest = Double.MAX_VALUE;
		double wnthLargest = 0;
		int ofN = 0;
		double m ;
		double w;
		Boolean hasval = false;
		for (int i = 0; i < this.getSubEntity().size(); ++i) {
			try {
				w =  this.getSubEntity().get(i).getWeight()
						* this.getSubEntity().get(i).getType().getMaxValue();
				m = this.getSubEntity().get(i).getMark()
						* this.getSubEntity().get(i).getWeight();
				
				if(ofN < getType().getN()){

					ofN++;
					mTotal += m;
					wTotal += w;
					if(m<nthLargest){
						nthLargest = m;
						wnthLargest = w;
					}
				} else if(nthLargest < m){
					mTotal -= nthLargest;
					wTotal -= wnthLargest;
					
					nthLargest = m;
					wnthLargest = w;
					
					mTotal += nthLargest;
					wTotal += wnthLargest;
				}
				
				hasval = true;
			} catch (Exception e) {
			}

		}
		if(!hasval){
			throw new AbsentException();
		}

		if (wTotal != 0)
			return mTotal / wTotal * this.getType().getMaxValue();
		else
			return 0.0;
	}
	
	

}
