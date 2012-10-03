package ClassAdminBackEnd;

import java.awt.image.BufferedImage;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class IMGEntity extends SuperEntity {
	
	private BufferedImage image;

	public IMGEntity(EntityType type, SuperEntity parentEntity, String text) {
		super(type, parentEntity, 0);
		this.setField(text);
		
		// TODO Auto-generated constructor stub
	}
	
	public IMGEntity(SuperEntity replacedEntity, String text){
		super(replacedEntity);
		this.setField(text);
	}
	

	public String getValue(){
		//TODO
			return this.getField();
	}
	
	public void setValue(String newValue){
		this.setField(newValue);
	}
	
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {
		long id = super.saveToDB(db, parentID, idgen);
		db.beginTransaction(SqlJetTransactionMode.WRITE);

        	ISqlJetTable table = db.getTable(PDatExport.IMG_ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(id,this.getField());

        return id;
	}
	
	public IMGEntity findPortrait(int i){
		return this;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	

	
}