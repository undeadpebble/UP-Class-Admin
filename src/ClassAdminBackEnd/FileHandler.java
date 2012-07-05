package ClassAdminBackEnd;

import java.util.ArrayList;

public class FileHandler {
	public void openFile(String filename) throws UnsupportedFileTypeException {
		CsvImport fileReader;
		Global glob = Global.getGlobal();
		if (filename.substring(filename.indexOf('.')).contains("csv")) {
			fileReader = new CsvImport();
		} else
			throw new UnsupportedFileTypeException();

		ArrayList headers;
		String record;
		EntityTypeFactory eTFactory = new EntityTypeFactory();
		MarkEntityFactory mEFactory = new MarkEntityFactory();
		glob.getActiveProject().getEntityTypes().clear();
		eTFactory.makeEntityTypeCSV("File", true);

		MarkEntity mE = mEFactory.makeEntity(glob.getActiveProject()
				.getEntityTypes().get(0), null);
		glob.getActiveProject().setHead(mE);

		if (fileReader.fileExists(filename)) {
			ArrayList csv = fileReader.recordData();
			headers = fileReader.getHeaders(csv);
			record = fileReader.getRecordFieldValue(csv, 0, 0);
			for (int i = 0; i < headers.size(); ++i) {
				record = fileReader.getRecordFieldValue(csv, 0, i);
				try {
					Integer.parseInt(record);
					if (record.length() > 4) {
						eTFactory.makeEntityTypeCSV((String) headers.get(i),
								true);
						System.out.println("text field");
					} else {
						eTFactory.makeEntityTypeCSV((String) headers.get(i),
								false);
						System.out.println("int field");
					}
				} catch (NumberFormatException e) {
					eTFactory.makeEntityTypeCSV((String) headers.get(i), true);
					System.out.println("text field");
				}

			}
			int numRecords = fileReader.getRecords(csv).size();
			for (int r = 0; r < numRecords; ++r) {
				MarkEntity parent = mEFactory.makeEntity(glob
						.getActiveProject().getEntityTypes().get(1), glob
						.getActiveProject().getHead());
				for (int f = 1; f < headers.size(); ++f) {
					record = fileReader.getRecordFieldValue(csv, r, f);
					EntityType fieldType = glob.getActiveProject()
							.getEntityTypes().get(f + 1);
					mE = mEFactory.makeEntity(fieldType, parent);
					if (fieldType.getIsTextField() == true) {
						mE.getDetails().getFields().add(record);
					} else {
						try {
							mE.setMark(Integer.parseInt(record));
						} catch (NumberFormatException e) {
							mE.setMark(0);
						}
					}
				}
			}
		}
	}
}
