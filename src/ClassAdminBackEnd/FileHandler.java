package ClassAdminBackEnd;

import java.util.ArrayList;

public class FileHandler {
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
		} else if (filename.substring(filename.indexOf('.')).contains("pdat")) {
			openPdat(filename);

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
			createEntitieTypes(headers, recordArray, fileReader);
			createMarkEntities(headers, recordArray, fileReader);
		}
	}

	private void createEntitieTypes(ArrayList headers, ArrayList recordArray,
			FileImport fileReader) {
		// header entity
		Global glob = Global.getGlobal();
		EntityTypeFactory eTFactory = new EntityTypeFactory();
		glob.getActiveProject().getEntityTypes().clear();
		eTFactory.makeEntityTypeFileImport("File", true);

		SuperEntity mE = new SuperEntity(glob.getActiveProject()
				.getEntityTypes().get(0), 0);
		glob.getActiveProject().setHead(mE);
		// create entity types
		for (int i = 0; i < headers.size(); ++i) {
			String record = fileReader.getRecordFieldValue(recordArray, 0, i);
			try {
				double dub = Double.parseDouble(record);
				if (dub > 1000) {
					eTFactory
							.makeEntityTypeFileImport((String) headers.get(i),
									true).getFields()
							.add((String) headers.get(i));

				} else {
					eTFactory.makeEntityTypeFileImport((String) headers.get(i),
							false);

				}
			} catch (NumberFormatException e) {
				eTFactory
						.makeEntityTypeFileImport((String) headers.get(i), true)
						.getFields().add((String) headers.get(i));

			}

		}
	}

	private void createMarkEntities(ArrayList headers, ArrayList recordArray,
			FileImport fileReader) {
		Global glob = Global.getGlobal();
		EntityTypeFactory eTFactory = new EntityTypeFactory();

		// create MarkEntities
		int numRecords = fileReader.getRecords(recordArray).size();
		for (int r = 0; r < numRecords; ++r) {
			// make parent for row
			SuperEntity parent = new SuperEntity(
							glob.getActiveProject().getEntityTypes().get(1),
							glob.getActiveProject().getHead(), 0);
			String record = fileReader.getRecordFieldValue(recordArray, r, 0);
			if (glob.getActiveProject().getEntityTypes().get(1)
					.getIsTextField() == true) {
				parent.getDetails().getFields().add(record);
			} else {
				try {
					parent.setMark(Double.parseDouble(record));
				} catch (NumberFormatException e) {
					parent.setMark(0);
				}
			}

			for (int f = 1; f < headers.size(); ++f) {
				record = fileReader.getRecordFieldValue(recordArray, r, f);
				EntityType fieldType = glob.getActiveProject().getEntityTypes()
						.get(f + 1);
				SuperEntity mE = new SuperEntity(fieldType, parent, 0);
				if (fieldType.getIsTextField() == true) {
					mE.getDetails().getFields().add(record);
				} else {
					try {
						mE.setMark(Double.parseDouble(record));
					} catch (NumberFormatException e) {
						mE.setMark(0);
					}
				}
			}
		}
	}
	private void openPdat(String filename){
		pdatImport PI = new pdatImport();
		PI.read(filename);
	}
	
	private void openXls(String filename){
		FileImport fileReader;
		fileReader = new XlsImport();
		ArrayList headers;

		if (fileReader.fileExists(filename)) {
			ArrayList recordArray = fileReader.recordData();
			headers = fileReader.getHeaders(recordArray);
			createEntitieTypes(headers, recordArray, fileReader);
			createMarkEntities(headers, recordArray, fileReader);
		}
	}
	
	public void saveFile(String filename) throws UnsupportedFileTypeException{
		if (filename.substring(filename.indexOf('.')).contains("csv")) {
			saveCSV(filename);
		} else if (filename.substring(filename.indexOf('.')).contains("pdat")) {
			savePdat(filename);

		} else if (filename.substring(filename.indexOf('.')).contains("xls")) {
			saveXls(filename);

		} else
			throw new UnsupportedFileTypeException();
	}

	private void saveCSV(String filename) {
		CsvExport exporter = new CsvExport();
		exporter.write(filename);
		
	}

	private void savePdat(String filename) {
		pdatImport exporter = new pdatImport();
		exporter.write(filename);
		
	}

	private void saveXls(String filename) {
		// TODO Auto-generated method stub
		
	}

}
