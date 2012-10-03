package ClassAdminBackEnd;

import java.awt.Color;
import java.util.LinkedList;

//public class MarkEntityFactory {
	/**
	 * Creates a new MarkEntity and puts it in the tree as a child of parent
	 * 
	 * @param entype
	 * @param parent
	 * @return
	 */
//<<<<<<< HEAD
	/*public SuperEntity makeEntity(EntityType entype, MarkEntity parent) {
=======
	public SuperEntity makeEntity(EntityType entype, SuperEntity parent) {
>>>>>>> refs/remotes/origin/louis
		LinkedList<String> fields = new LinkedList<String>();
		for (int i = 0; i < entype.getFieldDefaults().size(); ++i) {
			fields.add(entype.getFieldDefaults().get(i));
		}
<<<<<<< HEAD
		SuperEntity entity = new SuperEntity(parent, entype,
=======
		SuperEntity entity = new SuperEntity(parent, new EntityDetails(entype,
>>>>>>> refs/remotes/origin/louis
				fields, Color.WHITE, Color.BLACK, false), 0);
		if (parent != null) {
			parent.getSubEntity().add(entity);
			parent.getSubEntityWeight().add(entype.getDefaultWeight());
		}
		entype.getEntityList().add(entity);
		return entity;
	}*/
	
//}
