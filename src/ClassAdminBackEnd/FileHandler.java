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

	public static FileHandler get() {
		if (fh == null)
			fh = new FileHandler();
		return fh;
	}

	private FileHandler() {

	}

	public void openFile(String filename) throws UnsupportedFileTypeException {

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
			createEntityTypes(headers, recordArray, fileReader);
			createMarkEntities(headers, recordArray, fileReader);
		}
	}

	private int createEntityTypes(ArrayList headers, ArrayList recordArray,
			FileImport fileReader) {
		// header entity
		Global glob = Global.getGlobal();
		int firstStringCol = -1;
		// EntityTypeFactory eTFactory = new EntityTypeFactory();
		EntityType parentType = new EntityType("Project", null, true);
		glob.getGlobal().getActiveProject().setHeadEntityType(parentType);

		SuperEntity mE = new HeadEntity(glob.getActiveProject()
				.getHeadEntityType(), 0);

		glob.getActiveProject().setHead(mE);
		// create entity types

		LinkedList<EntityType> types = new LinkedList<EntityType>();
		for (int i = 1; i < headers.size(); ++i) {
			String record = fileReader.getRecordFieldValue(recordArray, 0, i);
			EntityType tmp;
			try {
				double dub = Double.parseDouble(record);
				
				if (dub > LARGEST_MARK_VALUE) {
					tmp = new EntityType((String) headers.get(i), null, true);
					if(firstStringCol < 0){
						firstStringCol = i;
						parentType = tmp;
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
				}
			}

		}
		
		if(parentType == null){
			//TODO
		} else{
			parentType.getSubEntityType().addAll(types);
			parentType.setParentEntitytype(glob.getActiveProject().getHeadEntityType());
			glob.getActiveProject().getHeadEntityType().getSubEntityType().add(parentType);
		}
		
		
		return firstStringCol;
	}

	private void createMarkEntities(int parentRowIndex, ArrayList headers, ArrayList recordArray,
			FileImport fileReader) {
		Global glob = Global.getGlobal();

		// create MarkEntities
		int numRecords = fileReader.getRecords(recordArray).size();
		for (int r = 0; r < numRecords; ++r) {
			// make parent for row

			SuperEntity parent = new SuperEntity(

			glob.getActiveProject().getHeadEntityType().getSubEntityType()
					.get(0), glob.getActiveProject().getHead(), 0);
			String record = fileReader.getRecordFieldValue(recordArray, r, 0);
			if (parent.getType().getIsTextField()) {
				parent = new StringEntity(parent, record);
			} else {
				try {
					parent = new LeafMarkEntity(parent);
					parent.setMark(Double.parseDouble(record));
				} catch (NumberFormatException e) {
					parent.setMark(0);
				}
			}

			for (int f = 1; f < headers.size(); ++f) {
				record = fileReader.getRecordFieldValue(recordArray, r, f);
				EntityType fieldType = glob.getActiveProject().getEntityTypes()
						.get(f);

				SuperEntity mE = new SuperEntity(fieldType, parent, 0);

				if (fieldType.getIsTextField() == true) {
					mE = new LeafStringEntity(mE, record);
				} else {
					try {
						mE = new LeafMarkEntity(mE);
						mE.setMark(Double.parseDouble(record));
					} catch (NumberFormatException e) {
						mE.setMark(0);
					}
				}
			}
		}
	}

	private void openXls(String filename) {
		FileImport fileReader;
		fileReader = new XlsImport();
		ArrayList headers;

		if (fileReader.fileExists(filename)) {
			ArrayList recordArray = fileReader.recordData();
			headers = fileReader.getHeaders(recordArray);
			createEntityTypes(headers, recordArray, fileReader);
			createMarkEntities(headers, recordArray, fileReader);
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
