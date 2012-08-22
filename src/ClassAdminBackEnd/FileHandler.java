package ClassAdminBackEnd;

import java.util.ArrayList;
import java.util.LinkedList;

public class FileHandler {
	final double LARGEST_MARK_VALUE = 1000;
	/**
	 * @param str
	 * @return
	 */
	static FileHandler fh;
	
	Project project;

	public static FileHandler get() {
		if (fh == null)
			fh = new FileHandler();
		return fh;
	}

	private FileHandler() {
		project = new Project();
	}

	public void openFile(String filename, Project project) throws UnsupportedFileTypeException {
		this.project = project;
		if (filename.substring(filename.indexOf('.')).contains("csv")) {
			openCSV(filename);
		} else if (filename.substring(filename.indexOf('.')).contains("xls")) {
			openXls(filename);

		} else
			throw new UnsupportedFileTypeException();

	}

	private void openCSV(String filename) {

		FileImport fileReader;
		fileReader = new CsvImport();
		ArrayList headers;

		if (fileReader.fileExists(filename)) {
			ArrayList recordArray = fileReader.recordData();
			headers = fileReader.getHeaders(recordArray);
			int parentRow = createEntityTypes(headers, recordArray, fileReader);
			createMarkEntities(parentRow, headers, recordArray, fileReader);
		}
	}

	private int createEntityTypes(ArrayList headers, ArrayList recordArray,
			FileImport fileReader) {
		// header entity
		int firstStringCol = -1;
		// EntityTypeFactory eTFactory = new EntityTypeFactory();
		EntityType headType = new EntityType("Project", null, true);
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
					tmp = new EntityType((String) headers.get(i), null, true);
					if(firstStringCol < 0){
						firstStringCol = i;
						parentType = tmp;
					}else{
						types.add(tmp);
					}
				} else {
					tmp = new EntityType((String) headers.get(i), null, false);
					types.add(tmp);
				}
			} catch (NumberFormatException e) {
				tmp = new EntityType((String) headers.get(i), null, true);
				if(firstStringCol < 0){
					firstStringCol = i;
					parentType = tmp;
				}else{
					types.add(tmp);
				}
			}

		}
		
		if(parentType == null){
			parentType = new EntityType("row", null, true);
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
			SuperEntity parentEntity;
			if(parentRowIndex < 0){
				parentEntity = new LeafStringEntity(project.getHeadEntityType().getSubEntityType().get(0), project.getHead(), "Row"+r);
			}
			else{
				String record = fileReader.getRecordFieldValue(recordArray, r, parentRowIndex);
				parentEntity = new StringEntity(project.getHeadEntityType().getSubEntityType().get(0), project.getHead(), record);
			}
			for (int f = 0; f < headers.size(); ++f) {
				if(f != parentRowIndex){
				String record = fileReader.getRecordFieldValue(recordArray, r, f);
				EntityType fieldType = parentEntity.getType().getSubEntityType().get(count++);

					SuperEntity mE = new SuperEntity(fieldType, parentEntity, 0);

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
			System.out.print("break");
		}
	}

	private void openXls(String filename) {
		FileImport fileReader;
		fileReader = new XlsImport();
		ArrayList headers;

		if (fileReader.fileExists(filename)) {
			ArrayList recordArray = fileReader.recordData();
			headers = fileReader.getHeaders(recordArray);
			int parentRow = createEntityTypes(headers, recordArray, fileReader);
			createMarkEntities(parentRow, headers, recordArray, fileReader);
		}
	}

	public void saveFile(String filename) throws UnsupportedFileTypeException {
		if (filename.substring(filename.indexOf('.')).contains("csv")) {
			saveCSV(filename);
		} else if (filename.substring(filename.indexOf('.')).contains("xls")) {
			saveXls(filename);

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

	}

}
