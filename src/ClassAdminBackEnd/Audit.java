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
		projectName = activeProject.getFileName().substring(0, activeProject.getFileName().indexOf(".")) + "_log.txt"; // create
																													// audit
																													// filename
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	}

	// return current time and date in string form
	private String d() {
		return (dateFormat.format(new Date()).toString());
	}

	// log opening of project
	public void openedProject() {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tPROJECT OPENED:\t" + projectName);
			out.newLine();
			out.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
			out.newLine();
			out.close();
			out = null;
		} catch (IOException e) {
		}
	}

	// log closing of project
	public void closedProject() {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tPROJECT CLOSED:\t" + projectName);
			out.newLine();
			out.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
			out.newLine();
			out.close();
			out = null;
			System.out.println("close");
		} catch (IOException e) {
		}
	}

	// log addition of node to tree structure
	public void AddNode(String parent, String child) {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tMODULE UPDATE:\t" + child + " added as child node to " + parent);
			out.newLine();
			out.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
			out.newLine();
			out.close();
		} catch (IOException e) {
		}
	}

	// log removal of node from tree structure
	public void RemoveNode(String node, boolean withChildren) {
		try {
			if (withChildren) {
				f = new File(projectName);
				fstream = new FileWriter(projectName, true);
				out = new BufferedWriter(fstream);
				out.append(d() + "\tMODULE UPDATE:\t" + node + " removed with children");
				out.newLine();
				out.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
				out.newLine();
				out.close();
			} else {
				f = new File(projectName);
				fstream = new FileWriter(projectName, true);
				out = new BufferedWriter(fstream);
				out.append(d() + "\tMODULE UPDATE:\t" + node + " removed without children");
				out.newLine();
				out.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
				out.newLine();
				out.close();
			}
		} catch (IOException e) {

		}
	}

	// log modifications of existing node to project
	public void updateNode(String node, String isText, String assDate, String weight, String newNode, String newIsText, String newAssDate, String newWeight) {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tMODULE UPDATE:\t" + node + ",\t Weight: " + weight + ",\t Text Field: " + isText + ",\t Assesment Date: " + assDate);
			out.newLine();
			out.append("\t\t\tTO:\t\t" + newNode + ",\t Weight: " + newWeight + ",\t Text Field: " + newIsText + ",\t Assesment Date: " + newAssDate);
			out.newLine();
			out.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
			out.newLine();
			out.close();
		} catch (IOException e) {
		}
	}

	// log parent changing of nodes in tree structure
	public void moveNode(String node, String newParent) {
		try {
			f = new File(projectName);
			fstream = new FileWriter(projectName, true);
			out = new BufferedWriter(fstream);
			out.append(d() + "\tMODULE UPDATE:\t" + node + " changed parent to: " + newParent);
			out.newLine();
			out.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
			out.newLine();
			out.close();
		} catch (IOException e) {
		}
	}

	// log any modifications of student values
	public void updateStudent(String student, String oldVal, String newVal, boolean mark) {
		try {
			if (mark) {
				f = new File(projectName);
				fstream = new FileWriter(projectName, true);
				out = new BufferedWriter(fstream);
				out.append(d() + "\tSTUDENT UPDATE:\t" + student + " MARK CHANGED FROM: " + oldVal + " TO: " + newVal);
				out.newLine();
				out.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
				out.newLine();
				out.close();
			} else {
				f = new File(projectName);
				fstream = new FileWriter(projectName, true);
				out = new BufferedWriter(fstream);
				out.append(d() + "\tSTUDENT UPDATE:\t" + student + " VALUE CHANGED FROM: " + oldVal + " TO: " + newVal);
				out.newLine();
				out.append("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
				out.newLine();
				out.close();
			}
		} catch (IOException e) {
		}
	}

}
