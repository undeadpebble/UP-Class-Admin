package ClassAdminFrontEnd;

import java.util.Date;

public class RecentDocument {
	
	private String filename;
	private String filepath;
	private String date;
	
	public RecentDocument(String filename_, String filepath_, String date_) {
		filename = filename_;
		filepath = filepath_;
		date = date_;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getFilepath() {
		return filepath;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String d) {
		date = d;
	}
}
