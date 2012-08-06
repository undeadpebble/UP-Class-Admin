/**
 * 
 */
package ClassAdminBackEnd;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

/**
 * @author undeadpebble
 *
 */
public class CsvExport {
	void write(String aOutputFileName) {
		LinkedList<LinkedList<SuperEntity>> lListME = Global.getGlobal().getActiveProject().getHead().getDataLinkedList();
		
		String[] headers = Global.getGlobal().getActiveProject().getHead().getHeaders();
		String out = headers[0];
		for(int x = 1;x<headers.length;++x){
			out += ","+headers[x];
		}
		out+= '\n';
		
		for(int x = 0;x<lListME.size();++x){
			for(int y = 0;y<lListME.get(x).size();++y){

				if(!lListME.get(x).get(y).getDetails().getType().getIsTextField()){
					out +=lListME.get(x).get(y).getMark()+",";
				}
				
				for(int z = 0;z<lListME.get(x).get(y).getDetails().getFields().size();++z){
					out+=lListME.get(x).get(y).getDetails().getFields().get(z)+",";
				}
			}
			out=out.substring(0, out.length()-1);
			out+='\n';
		}
		System.out.println(out);
		byte[] aInput = out.getBytes();
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
}
