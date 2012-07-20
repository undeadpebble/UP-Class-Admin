package ClassAdminBackEnd;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class pdatImport {

	/**
	 * Write a byte array to the given file. Writing binary data is
	 * significantly simpler than reading it.
	 */
	void write(byte[] aInput, String aOutputFileName) {
		try {
			OutputStream output = null;
			try {
				output = new BufferedOutputStream(new FileOutputStream(
						aOutputFileName));
				output.write(aInput);
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
	}

	String read(String aInputFileName) {

		File file = new File(aInputFileName);
		byte[] result = new byte[(int) file.length()];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while (totalBytesRead < result.length) {
					int bytesRemaining = result.length - totalBytesRead;
					// input.read() returns -1, 0, or more :
					int bytesRead = input.read(result, totalBytesRead,
							bytesRemaining);
					if (bytesRead > 0) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
				/*
				 * the above style is a bit tricky: it places bytes into the
				 * 'result' array; 'result' is an output parameter; the while
				 * loop usually has a single iteration only.
				 */

			} finally {

				input.close();
			}
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
		StringBuilder builder = new StringBuilder();
		for(int x = 0;x<result.length;++x)
			builder.append((char)result[x]);
		return builder.toString();
	}

	public String toBinaryString(String str) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); ++i) {
			builder.append(Integer.toBinaryString(str.charAt(i)));
		}

		return builder.toString();
	}

	public String fromBinaryString(String str) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); i += 7) {
			builder.append((char) Integer.parseInt(
					str.substring(i, Math.min(i + 7, str.length())), 2));
		}

		return builder.toString();
	}



//-----------------------------------------------------------------------------------
// parsing code

private void Project(){
	
}

private void MarkEntity(){
	
}
}

