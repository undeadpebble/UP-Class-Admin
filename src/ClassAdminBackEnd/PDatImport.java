package ClassAdminBackEnd;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ta.TransparentActivationSupport;

public class PDatImport {

	public void importFileDB4o(Project project,String filename){
		//Db4o.newConfiguration().add();
		Db4o.configure().add(new TransparentActivationSupport());
		 ObjectContainer db = Db4o.openFile(filename); 
		 
		 ObjectSet<Project> result  = db.query(Project.class);
		 Project p = (Project)(result.next());
		 System.out.println(p.getHeadEntityType());
		 
		Global.getGlobal().getProjects().set(Global.getGlobal().getProjects().indexOf(project), p);
			db.close();
			
	}
	
	
}
