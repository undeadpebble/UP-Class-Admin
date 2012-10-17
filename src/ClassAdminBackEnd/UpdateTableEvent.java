package ClassAdminBackEnd;

import javax.swing.JApplet;

/**
 * @author undeadpebble
 * an event that is fired when the table is updated
 */
public class UpdateTableEvent extends java.util.EventObject{
	private Project project = null;
	public UpdateTableEvent(Object source, Project project) {
		super(source);
		this.project = project;
	}
	/**
	 * @return
	 */
	public Project getProject() {
		return project;
	}
	/**
	 * @param project
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	

}
