package ClassAdminBackEnd;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;
import ClassAdminBackEnd.UpdateTableEventClassListener;


public class UpdateTableEventSource{
	private ArrayList listeners = new ArrayList();
	private static UpdateTableEventSource instance;
	static UpdateTableEventSource getUpdateTableEventSource(){
		if(instance == null){
			instance = new UpdateTableEventSource();
		}
		
		return(instance);
	}
	
	private UpdateTableEventSource(){
		
	}
	
	public synchronized void addEventListener(UpdateTableEventClassListener listener){
		listeners.add(listener);
	}
	
	public synchronized void removeEventListener(UpdateTableEventClassListener listener){
		listeners.remove(listener);
	}
	
	private synchronized void fireEvent(Project project){
		UpdateTableEvent event = new UpdateTableEvent(this,project);
		
		Iterator i = listeners.iterator();
		
		while(i.hasNext()){
			((UpdateTableEventClassListener)i.next()).handleUpdateTableEvent(event);
		}
	}
}
