package ClassAdminBackEnd;

import ClassAdminBackEnd.RapidAssessmentTree.TreeContainerNode;

public class RapidAssessmentContainerType extends RapidAssessmentRectangleType {
	private String Image;

	/**
	 * @return the image
	 */
	public String getImage() {
		return Image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
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
		super(replacedEntity.getName(), x, y, w, h);
		this.setX(x);
		this.setY(y);
		this.setW(w);
		this.setH(h);
		
		this.setDate(replacedEntity.getDate());
		this.setParentEntitytype(replacedEntity.getParentEntitytype());
		this.setDefaultWeight(replacedEntity.getDefaultWeight());
		this.setIsRule(false);
		this.setIsTextField(false);

		replacedEntity.getParentEntitytype().getSubEntityType()
				.remove(replacedEntity);
		replacedEntity.getParentEntitytype().getSubEntityType().add(this);

		this.setBorderCasing(replacedEntity.getBorderCasing());
		this.setFormatting(replacedEntity.getFormatting());
		this.setSubEntityType(replacedEntity.getSubEntityType());
		this.setEntityList(replacedEntity.getEntityList());

		SuperEntity[] entitylist = (SuperEntity[]) (replacedEntity
				.getEntityList().toArray());

		for (int z = 0; z < entitylist.length; ++z) {
			entitylist[z].setType(this);
		}
	}

}
