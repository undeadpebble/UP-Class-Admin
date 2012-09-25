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
	 * @param image the image to set
	 */
	public void setImage(String image) {
		Image = image;
	}

	public RapidAssessmentContainerType(EntityType replacedEntity) {

		this.setDate(replacedEntity.getDate());
		this.setParentEntitytype(replacedEntity.getParentEntitytype());
		this.setDefaultWeight(replacedEntity.getDefaultWeight());
		this.setIsRule(false);
		this.setIsTextField(false);
		this.setName(replacedEntity.getName());

		replacedEntity.getParentEntitytype().getSubEntityType()
				.remove(replacedEntity);
		replacedEntity.getParentEntitytype().getSubEntityType().add(this);

		this.setBorderCasing(replacedEntity.getBorderCasing());
		this.setFormatting(replacedEntity.getFormatting());
		this.setSubEntityType(replacedEntity.getSubEntityType());
		this.setEntityList(replacedEntity.getEntityList());

		SuperEntity[] entitylist = (SuperEntity[]) (replacedEntity
				.getEntityList().toArray());

		for (int x = 0; x < entitylist.length; ++x) {
			entitylist[x].setType(this);
		}

	}
}
