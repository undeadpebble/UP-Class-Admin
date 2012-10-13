package ClassAdminFrontEnd;

import javax.swing.JMenuItem;

public class RecentDocsMenuItem extends JMenuItem{
	
	private String path;
	
	public RecentDocsMenuItem(String s, String p) {
		super(s);
		path = p;
	}
	
	public String getPath() {
		return path;
	}
}
