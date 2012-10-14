package ClassAdminBackEnd;

import java.io.File;
import java.util.LinkedList;

import ClassAdminFrontEnd.FrmTable;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;

public class PDatExport {
	public static final String ENTITY_TYPE_TABLE = "EntityType";
	public static final String ENTITY_TABLE = "Entity";
	public static final String BEST_N_ENTITY_TABLE = "BestNEntity";
	public static final String STRING_ENTITY_TABLE = "StringEntity";
	public static final String ABSENT_ENTITY_TABLE = "AbsentEntity";
	public static final String IMG_ENTITY_TABLE = "ImageEntity";
	public static final String MARK_ENTITY_TABLE = "MarkEntity";
	public static final String BETWEEN_FORMAT_TABLE = "BetweenFormat";
	public static final String LESS_THAN_FORMAT_TABLE = "LessThanFormat";
	public static final String GREATER_THAN_FORMAT_TABLE = "GreaterThanFormat";
	public static final String FORMAT_TABLE = "Format";
	public static final String BORDERCASE_TABLE = "BorderCase";

	public void exportFileDB4o(Project project, String filename) {
		
		System.out.println("index: "+Global.getGlobal().getActiveProjectIndex()+" , "+project.getHeadEntityType());
		for(int x = 0;x<Global.getGlobal().getProjects().size();++x){
			System.out.println("index: "+x+" , "+ Global.getGlobal().getProjects().get(x).getHeadEntityType());
		}
		
		File f = new File(filename);
		f.delete();
		ObjectContainer db = Db4o.openFile(filename);

		LinkedList<FrmTable> tmp = project.getTables();
		project.getTables().clear();
		db.store(project);
		project.setTables(tmp);

		db.commit();
		db.close();

	}

	

}
