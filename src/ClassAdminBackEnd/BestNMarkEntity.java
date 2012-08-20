package ClassAdminBackEnd;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class BestNMarkEntity extends MarkEntity{

	int N;
	public BestNMarkEntity(EntityType type, SuperEntity parentEntity, int N) {
		super(type, parentEntity);
		this.N = N;
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unused")
	private Double doMarkMath() throws AbsentException{
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
				w =  this.getSubEntity().get(i).getWeight();
				m = this.getSubEntity().get(i).calcMark()
						* w;
				
				if(ofN < N){
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
			return mTotal / wTotal;
		else
			return mTotal;
	}
	
	public int saveToDB(SqlJetDb db, int parentID, PDatIDGenerator idgen) throws SqlJetException {
		int id = super.saveToDB(db, parentID, idgen);
		db.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
        	//TODO
        	ISqlJetTable table = db.getTable(PDatExport.ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(id+", "+this.N);
        } finally {
            db.commit();
            
        }
        return id;
	}

}
