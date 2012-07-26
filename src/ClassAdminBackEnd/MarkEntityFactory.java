package ClassAdminBackEnd;

import java.awt.Color;
import java.util.LinkedList;

public class MarkEntityFactory {
	/**
	 * Creates a new MarkEntity and puts it in the tree as a child of parent
	 * 
	 * @param entype
	 * @param parent
	 * @return
	 */
	public MarkEntity makeEntity(EntityType entype, MarkEntity parent) {
		LinkedList<String> fields = new LinkedList<String>();
		for (int i = 0; i < entype.getFieldDefaults().size(); ++i) {
			fields.add(entype.getFieldDefaults().get(i));
		}
		MarkEntity entity = new MarkEntity(parent, new EntityDetails(entype,
				fields, Color.WHITE, Color.BLACK, false), 0);
		if (parent != null) {
			parent.getSubEntity().add(entity);
			parent.getSubEntityWeight().add(entype.getDefaultWeight());
		}
		entype.getEntityList().add(entity);
		return entity;
	}
	
}
