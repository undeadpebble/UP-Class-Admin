package ClassAdminBackEnd;

public class FileHandler {
	public void openFile(String filename) throws UnsupportedFileTypeException{
		CsvImport fileReader;
		if(filename.substring(filename.indexOf('.')).contains("csv")){
			fileReader = new CsvImport();
		}
		else
			throw new UnsupportedFileTypeException();
		
		
		if(fileReader.fileExists(filename)){
			//TODO 
		}
	}
}
