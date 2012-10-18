package ClassAdminBackEnd;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class RapidAssessmentContainerType extends RapidAssessmentRectangleType {
	private BufferedImage Image;

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return Image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(BufferedImage image) {
		Image = image;
	}

	/**
	 * @param n
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public RapidAssessmentContainerType(EntityType replacedEntity, double x,
			double y, double w, double h) {
		super(replacedEntity.getName(), x, y, w, h, null);
		this.setX(x);
		this.setY(y);
		this.setW(w);
		this.setH(h);

		this.setDate(replacedEntity.getDate());
		this.setParentEntitytype(replacedEntity.getParentEntitytype());
		this.setDefaultWeight(replacedEntity.getDefaultWeight());
		this.setIsRule(false);
		this.setIsTextField(false);

		if (replacedEntity.getParentEntitytype() != null) {
			replacedEntity.getParentEntitytype().getSubEntityType()
					.remove(replacedEntity);
			replacedEntity.getParentEntitytype().getSubEntityType().add(this);
		}

		this.setBorderCasing(replacedEntity.getBorderCasing());
		this.setFormatting(replacedEntity.getFormatting());
		this.setSubEntityType(replacedEntity.getSubEntityType());
		for (int q = getSubEntityType().size()-1; q >= 0; --q) {
			try {
				RapidAssessmentComponentType temp = (RapidAssessmentComponentType) (this
						.getSubEntityType().get(q));
				temp.removeDeletingChildren();
				
			} catch (ClassCastException e) {
				this.getSubEntityType().get(q).setParentEntitytype(this);
			}
		}
		this.setEntityList(replacedEntity.getEntityList());

		for (int z = this.getEntityList().size()-1; z >=0 ; --z) {

			this.getEntityList().get(z).setType(this);
			//new MarkEntity(this.getEntityList().get(z));
		}
		
		
	}
	
	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.EntityType#findRapidAssessment(java.util.LinkedList)
	 */
	@Override
	public void findRapidAssessment(
			LinkedList<RapidAssessmentContainerType> list) {
		// TODO Auto-generated method stub
		super.findRapidAssessment(list);
		list.add(this);
	}

	/* (non-Javadoc)
	 * @see ClassAdminBackEnd.EntityType#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName();
	}

}
