package ClassAdminBackEnd;

import java.util.LinkedList;

public class Global {
	private LinkedList<Project> projects;
	private int activeProjectIndex = 0;

	private LinkedList<GraphType> graphTypes;
	private static Global instance;
	
	/**
	 * Creates the singleton object that contains all the program data.
	 * Creates the graphType instances for drawing different types of graphs.
	 */
	private Global(){
		projects = new LinkedList<Project>();
		projects.add(new Project());
		
		graphTypes = new LinkedList<GraphType>();
		//instantiate all the graphTypes
	
	}
	
	public static Global getGlobal(){
		if (instance == null)
			instance = new Global();
		
		return instance;
	}
	
	
	public LinkedList<GraphType> getGraphTypes() {
		return graphTypes;
	}

	public LinkedList<Project> getProjects() {
		return projects;
	}

	public int getActiveProjectIndex() {
		return activeProjectIndex;
	}

	public void setActiveProjectIndex(int activeProjectIndex) {
		this.activeProjectIndex = activeProjectIndex;
	}
	public Project getActiveProject(){
		return projects.get(activeProjectIndex);
		
	}

}
