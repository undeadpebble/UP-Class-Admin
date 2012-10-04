package ClassAdminFrontEnd;


import java.io.IOException;
import java.util.Date;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Database {
	private static final Class<? extends ObjectSet> RecentDocuments = null;

	private ObjectContainer db;
	private final String fileName = "database.db";

	public void openDatabase() {
			
		if (db == null) {
			db = Db4o.openFile(fileName);
		}
	
	}

	public void closeDatabase() {
		if (db != null)
			db.close();
	}

	public void addRecentDoc(String filen, String filep, String string) {
		RecentDocument recentdocument = new RecentDocument(filen, filep, string);
		db.store(recentdocument);
	}

	public void deleteRecentDocuments(String filepath) {
		// deleteSecondPilotByName
		ObjectSet result = db.queryByExample(new RecentDocument(null, filepath, null));
		RecentDocument found = (RecentDocument) result.next();
		db.delete(found);
	}
	
	public void listDocuments() {
		ObjectSet result = db.queryByExample(RecentDocument.class);
		listRecentDocsHall(result);

	}

	public void listRecentDocsHall(ObjectSet result) {
		System.out.println("SIZE:"+result.size());
		while (result.hasNext()) {
			RecentDocument rh = (RecentDocument) result.next();
			System.out.println("DOCS");
			System.out.println("document: " + rh.getFilename());
			System.out.println("path: " + rh.getFilepath());
			System.out.println("date: " + rh.getDate());
			System.out.println("==================="); 
		}
	}

	public boolean alreadyContains(String filen, String filep) {
		RecentDocument proto=new RecentDocument(filen,filep,null);
		ObjectSet result=db.queryByExample(proto);
		if (result.size() > 0) {
			return true;
		}			
		else {
			return false;
		}
	}
	
	public void updateRecentDocument(String filen, String filep, String d) {
		ObjectSet result=db.queryByExample(new RecentDocument(filen,filep,null));
		RecentDocument found=(RecentDocument)result.next();
		found.setDate(d);
		db.store(found);
	}
	
	public String[] getDocumentNames() {
		int i = 0;
		String[] array = new String[10];
		
		Query query=db.query();
		query.constrain(RecentDocument.class);
		query.descend("date").orderDescending();
		ObjectSet result=query.execute(); 
		
		while ((result.hasNext()) && (i < 10)){
			RecentDocument rh = (RecentDocument) result.next();
			array[i] = rh.getFilename();
			i++;
		}
		return array;
	}
	
	public int getDocumentCount() {
	
		ObjectSet result = db.queryByExample(RecentDocument.class);
		return result.size();
	}

	public String[] getDocumentPaths() {
		int i = 0;
		String[] array = new String[10];
		Query query=db.query();
		query.constrain(RecentDocument.class);
		query.descend("date").orderDescending();
		ObjectSet result=query.execute(); 
		
		while ((result.hasNext()) && (i < 10)){
			RecentDocument rh = (RecentDocument) result.next();
			array[i] = rh.getFilepath();
			i++;
		}
		return array;
	}
	
	
}
