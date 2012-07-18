package ClassAdminBackEnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.html.parser.Entity;

public class InteractiveTableModel extends AbstractTableModel {
    public static final int TITLE_INDEX = 0;
    public static final int ARTIST_INDEX = 1;
    public static final int ALBUM_INDEX = 2;
    public static final int HIDDEN_INDEX = 3;

    protected String[] columnNames;
    private CsvImport csv;
    private ArrayList headers;
    private ArrayList records;
    protected Vector dataVector;

    public InteractiveTableModel(String[] columnNames, CsvImport csv) {
        this.columnNames = columnNames;
        this.csv = csv;
        ArrayList temp = csv.recordData();
        
        headers = csv.getHeaders(temp);
        records = csv.getRecords(temp);
        
        dataVector = new Vector();
    }
    
    /**
     * @param ent
     * @param head
     * 
     * adds the counters to the nodes that you want to put into the spreadsheet
     */
    public void addCounters(MarkEntity ent, MarkEntity head){
    	ent.increaseRowFollowCount();
    		
    	
    	MarkEntity temp = ent;
    	
    	while(temp != head){
    		temp = temp.getParentEntity();
    		temp.increaseRowFollowCount();
    	}
    	
    	LinkedList<MarkEntity> temp2 = ent.getSubEntity();
    	
    	for(int x = 0; x < temp2.size();x++){
    		addCounters(temp2.get(x), head);
    	}
    	
    	
    }
    
    public String[] getHeaders(MarkEntity ent){
    	String[] headers = new String[ent.getRowFollowCount()];
    	
    	
    	return headers;
    }
    
    public class csvRecord{
    	String[] headers;
    	String[][] records;
    	
    	public csvRecord(ArrayList head, ArrayList record){
    		for(int x = 0; x < head.size();x++){
    			headers[x] = head.get(x).toString();
    		}
    		
    		for(int x = 0; x < record.size(); x++){
    			boolean test = false;
    			int y = 0;
    			
    			while(!test){
    				try{
    					
    				}
    				catch(Exception e){
    					test = true;
    				}
    			}
    			
    		}
    	}
    	
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public boolean isCellEditable(int row, int column) {
        if (column == HIDDEN_INDEX) return false;
        else return true;
    }

  /*  public Class getColumnClass(int column) {
        switch (column) {
            case TITLE_INDEX:
            case ARTIST_INDEX:
            case ALBUM_INDEX:
               return String.class;
            default:
               return Object.class;
        }
    }*/

    public Object getValueAt(int row, int column) {
        /*AudioRecord record = (AudioRecord)dataVector.get(row);
        switch (column) {
            case TITLE_INDEX:
               return record.getTitle();
            case ARTIST_INDEX:
               return record.getArtist();
            case ALBUM_INDEX:
               return record.getAlbum();
            default:
               return new Object();
        }*/
    	return null;
    }

    public void setValueAt(Object value, int row, int column) {
       /* AudioRecord record = (AudioRecord)dataVector.get(row);
        switch (column) {
            case TITLE_INDEX:
               record.setTitle((String)value);
               break;
            case ARTIST_INDEX:
               record.setArtist((String)value);
               break;
            case ALBUM_INDEX:
               record.setAlbum((String)value);
               break;
            default:
               System.out.println("invalid index");
        }
        fireTableCellUpdated(row, column);*/
    }

    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public boolean hasEmptyRow() {
        /*if (dataVector.size() == 0) return false;
        AudioRecord audioRecord = (AudioRecord)dataVector.get(dataVector.size() - 1);
        if (audioRecord.getTitle().trim().equals("") &&
           audioRecord.getArtist().trim().equals("") &&
           audioRecord.getAlbum().trim().equals(""))
        {
           return true;
        }
        else return false;*/
    	return false;
    }

    public void addEmptyRow() {
        //dataVector.add(new AudioRecord());
        fireTableRowsInserted(
           dataVector.size() - 1,
           dataVector.size() - 1);
    }
}