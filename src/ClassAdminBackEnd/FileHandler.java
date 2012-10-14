package ClassAdminBackEnd;

import java.util.ArrayList;
import java.util.LinkedList;

import org.tmatesoft.sqljet.core.SqlJetException;

import Frames.Frame;

public class FileHandler {
	final double LARGEST_MARK_VALUE = 1000;
	/**
	 * @param str
	 * @return
	 */
	static FileHandler fh;
	Frame frame;
	
	Project project;

	public static FileHandler get() {
		if (fh == null)
			fh = new FileHandler();
		return fh;
	}

	private FileHandler() {
	}

	public void openFile(String filename, Project project) throws UnsupportedFileTypeException {
		this.project = project;
		if (filename.substring(filename.indexOf('.')).toLowerCase().contains("csv")) {
			openCSV(filename);
		} else if (filename.substring(filename.indexOf('.')).toLowerCase().contains("xls")) {
			openXls(filename);

		} else if (filename.substring(filename.indexOf('.')).toLowerCase().contains("pdat")) {
			openPDat(filename);
		}
		else
			throw new UnsupportedFileTypeException();

	}

	private void openCSV(String filename) {

		FileImport fileReader;
		fileReader = new CsvImport();
		ArrayList headers;

		if (fileReader.fileExists(filename)) {
			ArrayList recordArray = fileReader.recordData();
			headers = fileReader.getHeaders(recordArray);
			int parentRow = createEntityTypes(headers, recordArray, fileReader, filename);
			createMarkEntities(parentRow, headers, recordArray, fileReader);
		}
	}

	private int createEntityTypes(ArrayList headers, ArrayList recordArray,
			FileImport fileReader, String filename) {
		// header entity
		int firstStringCol = -1;
		// EntityTypeFactory eTFactory = new EntityTypeFactory();
		EntityType headType = new EntityType(filename.substring(filename.lastIndexOf("\\")+1), null, true,null,1.0);
		project.setHeadEntityType(headType);

		SuperEntity mE = new HeadEntity(project
				.getHeadEntityType(), 0);

		project.setHead(mE);
		// create entity types
		
		EntityType parentType = null;

		LinkedList<EntityType> types = new LinkedList<EntityType>();
		for (int i = 0; i < headers.size(); ++i) {
			String record = fileReader.getRecordFieldValue(recordArray, 0, i);
			EntityType tmp;
			try {
				double dub = Double.parseDouble(record);
				
				if (dub > LARGEST_MARK_VALUE) {
					tmp = new EntityType((String) headers.get(i), null, true,null,1.0);
					if(firstStringCol < 0){
						firstStringCol = i;
						parentType = tmp;
					}else{
						types.add(tmp);
					}
				} else {
					tmp = new EntityType((String) headers.get(i), null, false,null,1.0);
					types.add(tmp);
				}
			} catch (NumberFormatException e) {
				tmp = new EntityType((String) headers.get(i), null, true,null,1.0);
				if(firstStringCol < 0){
					firstStringCol = i;
					parentType = tmp;
				}else{
					types.add(tmp);
				}
			}

		}
		
		if(parentType == null){
			parentType = new EntityType("row", null, true,null,1.0);
		} 
			parentType.getSubEntityType().addAll(types);
			parentType.setParentEntitytype(project.getHeadEntityType());
			project.getHeadEntityType().getSubEntityType().add(parentType);
			
			for(int x = 0;x<parentType.getSubEntityType().size();++x){
				parentType.getSubEntityType().get(x).setParentEntitytype(parentType);
			}
		
		
		
		return firstStringCol;
	}

	private void createMarkEntities(int parentRowIndex, ArrayList headers, ArrayList recordArray,
			FileImport fileReader) {


		// create MarkEntities
		int numRecords = fileReader.getRecords(recordArray).size();

		for (int r = 0; r < numRecords; ++r) {
			int count = 0;
			SuperEntityPointer parentEntity;
			if(parentRowIndex < 0){
				parentEntity = new SuperEntityPointer(new LeafStringEntity(project.getHeadEntityType().getSubEntityType().get(0), project.getHead(), "Row"+r));
			}
			else{
				String record = fileReader.getRecordFieldValue(recordArray, r, parentRowIndex);
				parentEntity = new SuperEntityPointer(new LeafStringEntity(project.getHeadEntityType().getSubEntityType().get(0), project.getHead(), record));
			}
			for (int f = 0; f < headers.size(); ++f) {
				if(f != parentRowIndex){
				String record = fileReader.getRecordFieldValue(recordArray, r, f);
				EntityType fieldType = parentEntity.getTarget().getType().getSubEntityType().get(count++);

					SuperEntity mE = new SuperEntity(fieldType, parentEntity.getTarget(), 0);

				if (fieldType.getIsTextField() == true) {
					mE = new LeafStringEntity(mE, record);
				} else {
					try {
						mE = new LeafMarkEntity(mE,0);
						mE.setMark(Double.parseDouble(record));
					} catch (NumberFormatException e) {
						mE.setMark(0);
					}
				}
			}
			}
		}
	}

	private void openXls(String filename) {
		FileImport fileReader;
		fileReader = new XlsImport(frame);
		ArrayList headers;

		if (fileReader.fileExists(filename)) {
			ArrayList recordArray = fileReader.recordData();
			headers = fileReader.getHeaders(recordArray);
			int parentRow = createEntityTypes(headers, recordArray, fileReader, filename);
			createMarkEntities(parentRow, headers, recordArray, fileReader);
		}
	}

	public void saveFile(String filename, Project project) throws UnsupportedFileTypeException {
		this.project = project;
		if (filename.substring(filename.indexOf('.')).contains("csv")) {
			saveCSV(filename);
		} else if (filename.substring(filename.indexOf('.')).contains("xls")) {
			saveXls(filename);

		} else if (filename.substring(filename.indexOf('.')).contains("pdat")) {
			 savePDat(filename);
		} else
			throw new UnsupportedFileTypeException();
	}

	private void saveCSV(String filename) {
		CsvExport exporter = new CsvExport();
		exporter.write(filename);

	}

	private void saveXls(String filename) {
		// TODO Auto-generated method stub

	}

	private void openPDat(String filename) {
		PDatImport pImport = new PDatImport();
		
			pImport.importFileDB4o(project, filename);
		
	}
	
	private void savePDat(String filename){
		PDatExport pExport = new PDatExport();
			pExport.exportFileDB4o(project, filename);
		
	}
	

	public void setXLSImport(XlsImport i) {
//		i.recordData();
		
	}
	
	public void setFrame(Frame frame_) {
		frame = frame_;
	}

}
