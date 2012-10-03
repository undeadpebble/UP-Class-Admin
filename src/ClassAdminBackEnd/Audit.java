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
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
		} catch (IOException e) {
			f = new File(projectName);
			fstream = new FileWriter(projectName);
			e.printStackTrace();
		}
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	}

	private String d() {
		return (dateFormat.format(new Date()).toString());
	}

	public void openedProject() {
		try {
			out = new BufferedWriter(fstream);
			out.append(d() + "\tPROJECT OPENED: " + projectName);
			out.newLine();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closedProject() {
		try {
			out = new BufferedWriter(fstream);
			out.append(d() + "\tPROJECT CLOSED: " + projectName);
			out.newLine();
		} catch (IOException e) {
		}
	}

	public void AddNode(String parent, String child) {
		try {
			out = new BufferedWriter(fstream);
			out.append(d() + "\tSTRUCTURE ADDITION: " + child + " added as child node to " + parent);
			out.newLine();
		} catch (IOException e) {
		}
	}

	public void RemoveNode(String node, boolean withChildren) {
		try {
			if (withChildren) {
				out = new BufferedWriter(fstream);
				out.append(d() + "\tSTRUCTURE REMOVAL: " + node + " removed with children");
				out.newLine();
			} else {
				out = new BufferedWriter(fstream);
				out.append(d() + "\tSTRUCTURE REMOVAL: " + node + " removed without children");
				out.newLine();
			}
		} catch (IOException e) {

		}
	}

	public void updateNode(String node, String isText, String assDate, String weight) {
		try {
			out.append(d() + "\tSTRUCTURE UPDATE: " + node + " Weight: " + weight + " Text Field: " + isText + " Assesment Date: " + assDate);
			out.append("\n");
		} catch (IOException e) {
		}
	}

	public void moveNode(String node, String oldParent, String newParent) {
		try {
			out.write(d() + "\tSTRUCTURE UPDATE: " + node + " changed parents from: " + oldParent + " to: " + newParent);
			out.write("\n");
		} catch (IOException e) {
		}
	}

	public void updateStudent(String student, String node, String oldVal, String newVal) {
		try {
			out.write(d() + "\tSTUDENT UPDATE: " + student + " MARK: " + node + " FROM: " + oldVal + " TO: " + newVal);
			out.write("\n");
		} catch (IOException e) {
		}
	}

}
