package ClassAdminBackEnd;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import Pdat.Pdat;

import Pdat.Pdat;

public class pdatImport {

	public static String arrayToString(String[] arr){
		StringBuilder result = new StringBuilder();
		result.append("[");
		if(arr.length > 0){
			result.append("\""+arr[0]+"\"");
		}
		for(int x = 1;x<arr.length;++x){
			result.append(", "+"\""+arr[x]+"\"");
		}
		result.append("]");
		return result.toString();
	}
	
	public static String linkedListToString(LinkedList<String> arr){
		StringBuilder result = new StringBuilder();
		result.append("[");
		if(arr.size() > 0){
			result.append("\""+arr.get(0)+"\"");
		}
		for(int x = 1;x<arr.size();++x){
			result.append(", "+"\""+arr.get(x)+"\"");
		}
		result.append("]");
		return result.toString();
	}
	
	/**
	 * Write a byte array to the given file. Writing binary data is
	 * significantly simpler than reading it.
	 */
	void write(String aOutputFileName) {
		byte[] aInput = Global.getGlobal().getActiveProject().toString().getBytes();
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

	void read(String aInputFileName) {
		String[] args = {aInputFileName};
		Pdat.main(args);
		/*File file = new File(aInputFileName);
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
				 *//*

			} finally {

				input.close();
			}
		} catch (FileNotFoundException ex) {

		} catch (IOException ex) {

		}
		StringBuilder builder = new StringBuilder();
		for(int x = 0;x<result.length;++x)
			builder.append((char)result[x]);
		return builder.toString();*/
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
// parsing Assist Code

public static Date ParseDate(String date){
	SimpleDateFormat sdf = new SimpleDateFormat();
	
	try{
		return sdf.parse(date.replace('"', ' ').trim());
	}
	catch(ParseException e){
		return null;
	}
}

public static void MarkEntity(){
	LinkedList<Boolean> tempBoolList = new LinkedList<Boolean>();
	tempBoolList.add(true);
	Boolean[] b = (Boolean[])tempBoolList.toArray();
	double g = Double.parseDouble("1.0");
	
	EntityType ent = new EntityType("test");
	ent.setVisibleFields(pdatImport.toBoolArray(tempBoolList));
	
}

public static Boolean[] toBoolArray(LinkedList<Boolean> boolArr){
	Boolean[] array = new Boolean[boolArr.size()];
	for(int x = 0;x<boolArr.size();++x){
		array[x] = boolArr.get(x);
	}
	return array;
}

// end parsing assist code



}

