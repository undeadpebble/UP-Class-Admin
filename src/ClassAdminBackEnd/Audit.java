package ClassAdminBackEnd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Audit {
	File f;
	FileWriter fstream;
	BufferedWriter out;
	Project activeProject;
	String projectName;
	DateFormat dateFormat;
	Date date;

	public Audit(Project project) throws IOException {
		activeProject = project;
		projectName = activeProject.getFileName().substring(0, activeProject.getFileName().indexOf(".")) + ".txt";
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	}

	private String d() {
		return (dateFormat.format(new Date()).toString());
	}

	public void openedProject() {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tPROJECT OPENED: " + projectName);
			out.newLine();
			out.close();
			out = null;
		} catch (IOException e) {
		}
	}

	public void closedProject() {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tPROJECT CLOSED: " + projectName);
			out.newLine();
			out.close();
			out = null;
			System.out.println("close");
		} catch (IOException e) {
		}
	}

	public void AddNode(String parent, String child) {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tSTRUCTURE ADDITION: " + child + " added as child node to " + parent);
			out.newLine();
			out.close();
		} catch (IOException e) {
		}
	}

	public void RemoveNode(String node, boolean withChildren) {
		try {
			if (withChildren) {
				f = new File(projectName);
				fstream = new FileWriter(projectName, true);
				out = new BufferedWriter(fstream);
				out.append(d() + "\tSTRUCTURE REMOVAL: " + node + " removed with children");
				out.newLine();
				out.close();
			} else {
				f = new File(projectName);
				fstream = new FileWriter(projectName, true);
				out = new BufferedWriter(fstream);
				out.append(d() + "\tSTRUCTURE REMOVAL: " + node + " removed without children");
				out.newLine();
				out.close();
			}
		} catch (IOException e) {

		}
	}

	public void updateNode(String node, String isText, String assDate, String weight) {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tSTRUCTURE UPDATE: " + node + " Weight: " + weight + " Text Field: " + isText + " Assesment Date: " + assDate);
			out.newLine();
			out.close();
		} catch (IOException e) {
		}
	}

	public void moveNode(String node, String newParent) {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tSTRUCTURE UPDATE: " + node + " changed parent to: " + newParent);
			out.newLine();
			out.close();
		} catch (IOException e) {
		}
	}

	public void updateStudent(String student, String oldVal, String newVal, boolean mark) {
		try {
			if (mark)
			{
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tSTUDENT UPDATE: " + student + " MARK CHANGED FROM: " + oldVal + " TO: " + newVal);
			out.newLine();
			out.close();
			}
			else
			{
				f = new File(projectName);
				fstream = new FileWriter(projectName, true);
				out = new BufferedWriter(fstream);
				out.append(d() + "\tSTUDENT UPDATE: " + student + " VALUE CHANGED FROM: " + oldVal + " TO: " + newVal);
				out.newLine();
				out.close();
			}
		} catch (IOException e) {
		}
	}

}
