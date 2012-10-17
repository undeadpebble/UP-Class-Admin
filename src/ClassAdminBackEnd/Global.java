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
	
	/**
	 * @return instance
	 */
	public static Global getGlobal(){
		if (instance == null)
			instance = new Global();
		
		return instance;
	}
	
	
	/**
	 * @return
	 */
	public LinkedList<GraphType> getGraphTypes() {
		return graphTypes;
	}

	/**
	 * @return
	 */
	public LinkedList<Project> getProjects() {
		return projects;
	}

	/**
	 * @return
	 */
	public int getActiveProjectIndex() {
		return activeProjectIndex;
	}

	/**
	 * @param activeProjectIndex
	 */
	public void setActiveProjectIndex(int activeProjectIndex) {
		this.activeProjectIndex = activeProjectIndex;
	}
	
	/**
	 * @return
	 */
	public Project getActiveProject(){
		return projects.get(activeProjectIndex);
		
	}
	
	/**
	 * @param newProject
	 */
	public void addProject(Project newProject){
		this.projects.add(newProject);
		this.setActiveProjectIndex(this.projects.size()-1);
	}

}
