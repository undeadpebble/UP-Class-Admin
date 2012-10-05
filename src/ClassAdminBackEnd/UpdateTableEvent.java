package ClassAdminBackEnd;

import javax.swing.JApplet;

public class UpdateTableEvent extends java.util.EventObject{
	private Project project = null;
	public UpdateTableEvent(Object source, Project project) {
		super(source);
		this.project = project;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	

}
