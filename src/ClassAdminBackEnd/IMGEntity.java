package ClassAdminBackEnd;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 * @author undeadpebble
 *
 */
public class IMGEntity extends SuperEntity {
	
	private SaveableImage image = new SaveableImage();

	/**
	 * @param type
	 * @param parentEntity
	 * @param text
	 */
	public IMGEntity(EntityType type, SuperEntity parentEntity, String text) {
		super(type, parentEntity, 0);
		this.setField(text);
		
	}
	
	/**
	 * @param replacedEntity
	 * @param text
	 */
	public IMGEntity(SuperEntity replacedEntity, String text){
		super(replacedEntity);
		this.setField(text);
	}
	

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#getValue()
	 */
	public String getValue(){
		//TODO
			return this.getField();
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#setValue(java.lang.String)
	 */
	public void setValue(String newValue){
		this.setField(newValue);
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#saveToDB(org.tmatesoft.sqljet.core.table.SqlJetDb, long, ClassAdminBackEnd.PDatIDGenerator)
	 * was used to save to an sql database, now deprecated
	 */
	public long saveToDB(SqlJetDb db, long parentID, PDatIDGenerator idgen) throws SqlJetException {
		long id = super.saveToDB(db, parentID, idgen);
		db.beginTransaction(SqlJetTransactionMode.WRITE);

        	ISqlJetTable table = db.getTable(PDatExport.IMG_ENTITY_TABLE);
        	//insert statements
        	
        	table.insert(id,this.getField());

        return id;
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.SuperEntity#findPortrait(int)
	 */
	public IMGEntity findPortrait(int i){
		return this;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		try {
			return image.getImage();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		try {
			this.image.setImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	
}